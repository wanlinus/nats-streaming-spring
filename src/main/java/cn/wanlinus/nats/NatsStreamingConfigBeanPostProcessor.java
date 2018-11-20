package cn.wanlinus.nats;

import cn.wanlinus.nats.annotation.Subscribe;
import io.nats.streaming.Message;
import io.nats.streaming.StreamingConnection;
import io.nats.streaming.SubscriptionOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(NatsStreamingConfigBeanPostProcessor.class);

    private StreamingConnection sc;


    public NatsStreamingConfigBeanPostProcessor(StreamingConnection sc) {
        this.sc = sc;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        final Class<?> clazz = bean.getClass();
        Arrays.stream(clazz.getMethods()).forEach(method -> {
            Optional<Subscribe> subOpt = Optional.ofNullable(AnnotationUtils.findAnnotation(method, Subscribe.class));
            subOpt.ifPresent(sub -> {
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
                    sc.subscribe(sub.value(), "".equals(sub.queue()) ? null : sub.queue(), msg -> {
                        try {
                            method.invoke(bean, msg);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            LOGGER.error(String.format("method <%s> invoke failed", method.getName()));
                        }
                    }, new SubscriptionOptions.Builder()
                            .durableName("".equals(sub.durableName()) ? null : sub.durableName())
                            .deliverAllAvailable().build());
                } catch (IOException | InterruptedException | TimeoutException e) {
                    LOGGER.error("Message subscribe failed");
                    Thread.currentThread().interrupt();
                }
            });
        });
        return bean;
    }
}
