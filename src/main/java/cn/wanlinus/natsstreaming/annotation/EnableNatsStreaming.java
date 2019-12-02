package cn.wanlinus.natsstreaming.annotation;

import cn.wanlinus.natsstreaming.NatsStreamingConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enable Nats Streaming Support {@link NatsStreamingConfiguration}
 *
 * @author wanli
 * @date 2018-09-17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({NatsStreamingConfiguration.class})
public @interface EnableNatsStreaming {
}
