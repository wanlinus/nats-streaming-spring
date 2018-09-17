package cn.wanlinus.natsstreaming;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wanli
 * @date 2018-09-17
 */
@ConfigurationProperties(prefix = "spring.nats.streaming")
public class NatsStreamingProperties {
    private String clusterId = "test-cluster";
    private String natsUrl = "nats://127.0.0.1:4200";
    private String durableName = "test";

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getNatsUrl() {
        return natsUrl;
    }

    public void setNatsUrl(String natsUrl) {
        this.natsUrl = natsUrl;
    }

    public String getDurableName() {
        return durableName;
    }

    public void setDurableName(String durableName) {
        this.durableName = durableName;
    }
}
