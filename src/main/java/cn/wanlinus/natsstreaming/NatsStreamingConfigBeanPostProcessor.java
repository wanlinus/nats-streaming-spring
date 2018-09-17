package cn.wanlinus.natsstreaming;

import cn.wanlinus.natsstreaming.annotation.StreamingSub;
import io.nats.streaming.Message;
import io.nats.streaming.StreamingConnection;
import io.nats.streaming.SubscriptionOptions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeoutException;

/**
 * Nats Streaming 处理类 主要是注册{@link StreamingSub}
 *
 * @author wanli
 * @date 2018-09-17
 */
public class NatsStreamingConfigBeanPostProcessor implements BeanPostProcessor {

    private StreamingConnection sc;

    public NatsStreamingConfigBeanPostProcessor(StreamingConnection sc) {
        this.sc = sc;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        final Class<?> clazz = bean.getClass();
        for (final Method method : clazz.getMethods()) {
            final StreamingSub sub = AnnotationUtils.findAnnotation(method, StreamingSub.class);
            if (sub != null) {
                final Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1 || !parameterTypes[0].equals(Message.class)) {
                    throw new NatsStreamingException(String.format(
                            "Method '%s' on bean with name '%s' must have a single parameter of type %s when using the @%s annotation.",
                            method.toGenericString(),
                            beanName,
                            Message.class.getName(),
                            StreamingSub.class.getName()
                    ));
                }
                try {
                    sc.subscribe(sub.subscribe(), "".equals(sub.queue()) ? null : sub.queue(), msg -> {
                        try {
                            method.invoke(bean, msg);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }, new SubscriptionOptions.Builder()
                            .durableName("".equals(sub.durableName()) ? null : sub.durableName())
                            .deliverAllAvailable().build());
                } catch (IOException | InterruptedException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}
