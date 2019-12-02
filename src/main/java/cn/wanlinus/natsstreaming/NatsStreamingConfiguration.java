package cn.wanlinus.natsstreaming;

import io.nats.client.impl.NatsImpl;
import io.nats.streaming.StreamingConnection;
import io.nats.streaming.StreamingConnectionFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

/**
 * @author wanli
 * @date 2018-09-17
 */
@Configuration
@EnableConfigurationProperties({NatsStreamingProperties.class})
public class NatsStreamingConfiguration {
    @Bean
    public StreamingConnection streamingConnection(NatsStreamingProperties properties) throws IOException, InterruptedException {
        StreamingConnectionFactory scf = new StreamingConnectionFactory(properties.getClusterId(), String.valueOf(UUID.randomUUID()));
        io.nats.client.Options.Builder builder = new io.nats.client.Options.Builder()
                .servers(properties.getNatsUrls())
                .token(properties.getToken())
                .connectionListener(new NatsListener())
                .maxReconnects(properties.getMaxReconnect())
                .reconnectWait(Duration.ofSeconds(properties.getReconnectWait()))
                .connectionTimeout(Duration.ofSeconds(properties.getConnectionTimeout()));
        scf.setNatsConnection(NatsImpl.createConnection(builder.build(), true));
        return scf.createConnection();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public NatsStreamingConfigBeanPostProcessor configBeanPostProcessor(StreamingConnection connection) {
        return new NatsStreamingConfigBeanPostProcessor(connection);
    }
}
