package cn.wanlinus.natsstreaming.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author wanli
 * @date 2018-09-17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Subscribe {


    /**
     * @return {@link #subscribe()}
     */
    @AliasFor("subscribe")
    String value() default "";

    /**
     * The Nats subject to subscribe to.
     *
     * @return the Nats Subject
     */
    @AliasFor("value")
    String subscribe() default "";

    /**
     * Queue name
     *
     * @return the nats queue name
     */
    String queue() default "";

    /**
     * @return
     */
    String durableName() default "";
}
