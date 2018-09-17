package cn.wanlinus.natsstreaming;

import io.nats.streaming.NatsStreaming;
import io.nats.streaming.Options;
import io.nats.streaming.StreamingConnection;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import java.io.IOException;
import java.util.UUID;

/**
 * @author wanli
 * @date 2018-09-17
 */
@Configuration
@EnableConfigurationProperties({NatsProperties.class, NatsStreamingProperties.class})
public class NatsStreamingConfiguration {

    @Bean
    public StreamingConnection streamingConnection(NatsStreamingProperties properties) throws IOException, InterruptedException {
        Options streamingOptions = new Options.Builder().natsUrl(properties.getNatsUrl()).build();
        return NatsStreaming.connect(properties.getClusterId(), String.valueOf(UUID.randomUUID()), streamingOptions);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public NatsStreamingConfigBeanPostProcessor configBeanPostProcessor(StreamingConnection connection) {
        return new NatsStreamingConfigBeanPostProcessor(connection);
    }
}
