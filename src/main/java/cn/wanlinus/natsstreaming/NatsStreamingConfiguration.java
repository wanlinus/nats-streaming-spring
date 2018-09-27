package cn.wanlinus.natsstreaming;

import io.nats.streaming.NatsStreaming;
import io.nats.streaming.Options;
import io.nats.streaming.StreamingConnection;
import io.nats.streaming.SubscriptionOptions;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author wanli
 * @date 2018-09-17
 */
@Configuration
@EnableConfigurationProperties({NatsProperties.class, NatsStreamingProperties.class, NatsStreamingSubProperties.class})
public class NatsStreamingConfiguration {

    @Bean
    public StreamingConnection streamingConnection(NatsStreamingProperties properties) throws IOException, InterruptedException {
        Options streamingOptions = new Options.Builder().natsUrl(properties.getNatsUrl()).build();
        return NatsStreaming.connect(properties.getClusterId(), String.valueOf(UUID.randomUUID()), streamingOptions);
    }

    @Bean
    public SubscriptionOptions options(NatsStreamingSubProperties properties) {
        SubscriptionOptions.Builder builder = new SubscriptionOptions.Builder();
        builder.maxInFlight(properties.getMaxInFlight())
                .ackWait(properties.getAckWait(), TimeUnit.SECONDS)
                .deliverAllAvailable();
        if (properties.isManualAcks()) {
            builder.manualAcks();
        }
        return builder.build();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public NatsStreamingConfigBeanPostProcessor configBeanPostProcessor(StreamingConnection connection) {
        return new NatsStreamingConfigBeanPostProcessor(connection);
    }

}
