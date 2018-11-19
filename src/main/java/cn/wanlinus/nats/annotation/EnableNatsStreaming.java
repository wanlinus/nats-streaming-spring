package cn.wanlinus.nats.annotation;

import cn.wanlinus.nats.NatsStreamingConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enable Nats Streaming Support {@link Subscribe }
 *
 * @author wanli
 * @date 2018-09-17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({NatsStreamingConfiguration.class})
public @interface EnableNatsStreaming {
}
