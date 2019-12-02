package cn.wanlinus.natsstreaming;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wanli
 * @date 2018-09-17
 */
@ConfigurationProperties(prefix = "spring.nats.streaming")
public class NatsStreamingProperties {
    private String clusterId = "test-cluster";
    private String[] natsUrls = {"nats://127.0.0.1:4200"};
    private String token;
    /**
     * Default maximum number of reconnect attempts
     */
    private int maxReconnect = 60;
    /**
     * Default wait time before attempting reconnection to the same server
     */
    private int reconnectWait = 2;
    /**
     * Default connection timeout
     */
    private int connectionTimeout = 2;

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String[] getNatsUrls() {
        return natsUrls;
    }

    public void setNatsUrls(String[] natsUrls) {
        this.natsUrls = natsUrls;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getMaxReconnect() {
        return maxReconnect;
    }

    public void setMaxReconnect(int maxReconnect) {
        this.maxReconnect = maxReconnect;
    }

    public int getReconnectWait() {
        return reconnectWait;
    }

    public void setReconnectWait(int reconnectWait) {
        this.reconnectWait = reconnectWait;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
