package cn.wanlinus.natsstreaming;

import cn.wanlinus.natsstreaming.annotation.Subscribe;
import io.nats.streaming.Message;
import io.nats.streaming.StreamingConnection;
import io.nats.streaming.SubscriptionOptions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    private StreamingConnection sc;
    private SubscriptionOptions options;

    public NatsStreamingConfigBeanPostProcessor(StreamingConnection sc, SubscriptionOptions options) {
        this.sc = sc;
        this.options = options;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        final Class<?> clazz = bean.getClass();
        Arrays.stream(clazz.getMethods()).forEach(method -> {
            Optional<Subscribe> sub = Optional.ofNullable(AnnotationUtils.findAnnotation(method, Subscribe.class));
            sub.ifPresent(subscribe -> {
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
                try {
                    sc.subscribe(subscribe.subscribe(), "".equals(subscribe.queue()) ? null : subscribe.queue(), msg -> {
                        try {
                            method.invoke(bean, msg);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }, options);
                } catch (IOException | InterruptedException | TimeoutException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            });
        });
        return bean;
    }
}
