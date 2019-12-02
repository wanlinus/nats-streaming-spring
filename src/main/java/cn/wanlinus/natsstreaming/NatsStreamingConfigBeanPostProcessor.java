package cn.wanlinus.natsstreaming;

import cn.wanlinus.natsstreaming.annotation.Subscribe;
import io.nats.client.Subscription;
import io.nats.streaming.Message;
import io.nats.streaming.StreamingConnection;
import io.nats.streaming.SubscriptionOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

/**
 * Nats Streaming 处理类 主要是注册{@link Subscribe}
 *
 * @author wanli
 * @date 2018-09-17
 */
public class NatsStreamingConfigBeanPostProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(NatsStreamingConfigBeanPostProcessor.class);

    private StreamingConnection sc;

    public NatsStreamingConfigBeanPostProcessor(StreamingConnection sc) {
        this.sc = sc;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        final Class<?> clazz = bean.getClass();
        Arrays.stream(clazz.getMethods()).forEach(method -> {
            Optional<Subscribe> subOpt = Optional.ofNullable(AnnotationUtils.findAnnotation(method, Subscribe.class));
            subOpt.ifPresent(subscribe -> {
                final Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1 || !parameterTypes[0].equals(Message.class)) {
                    throw new NatsStreamingException(String.format(
                            "Method '%s' on bean with name '%s' must have a single parameter of type %s when using the @%s annotation.",
                            method.toGenericString(),
                            beanName,
                            Message.class.getName(),
                            Subscribe.class.getName()
                    ));
                }
                if (subscribe.manualAck()) {
                    manualAsk(subscribe, method, bean);
                } else {
                    autoAck(subscribe, method, bean);
                }
            });
        });
        return bean;
    }

    private void manualAsk(Subscribe sub, Method method, Object bean) {
        new ManualThread(sc, sub, method, bean).start();
    }

    private void autoAck(Subscribe sub, Method method, Object bean) {
        try {
            sc.subscribe(sub.value(), "".equals(sub.queue()) ? null : sub.queue(), msg -> {
                try {
                    method.invoke(bean, msg);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    logger.error("method [{}] invoke failed", method.getName(), e);
                }
            }, new SubscriptionOptions.Builder()
                    .durableName("".equals(sub.durableName()) ? null : sub.durableName())
                    .build());
        } catch (IOException | InterruptedException | TimeoutException e) {
            logger.error("Message subscribe failed", e);
            Thread.currentThread().interrupt();
        }
    }

    class ManualThread extends Thread {
        private StreamingConnection sc;
        private Subscribe sub;
        private Method method;
        private Object bean;

        ManualThread(StreamingConnection sc, Subscribe sub, Method method, Object bean) {
            this.sc = sc;
            this.sub = sub;
            this.method = method;
            this.bean = bean;
        }

        @Override
        public void run() {
            try {
                Subscription subscribe = sc.getNatsConnection().subscribe(sub.value(), "".equals(sub.queue()) ? null : sub.queue());
                io.nats.client.Message msg;
                while ((msg = subscribe.nextMessage(Duration.ZERO)) != null) {
                    try {
                        method.invoke(bean, msg);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        logger.error("method [{}] invoke failed", method.getName(), e);
                    }
                    sc.getNatsConnection().publish(msg.getReplyTo(), "OK".getBytes());
                }
            } catch (InterruptedException e) {
                logger.error("method invoke failed", e);
            }
        }
    }

}
