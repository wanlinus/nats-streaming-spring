/*
 * Copyright 2018 wanli <wanlinus@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.wanlinus.natsstreaming;


import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.net.ssl.SSLContext;
import java.time.Duration;

import static io.nats.client.Options.*;

/**
 * Nats 配置类，在Spring boot启动的时候自动装配此类
 * 其配置主要参考 {@link io.nats.client.Options}
 *
 * @author wanli
 * @date 2018-08-30
 */
@ConfigurationProperties(prefix = "spring.nats")
public class NatsProperties {
    private String[] urls = {"nats://127.0.0.1:4222"};
    private boolean noRandomize = false;
    /**
     * Useful for debugging -> "test: " + NatsTestServer.currentPort();
     */
    private String connectionName = null;
    private boolean verbose = false;
    private boolean pedantic = false;
    private SSLContext sslContext = null;
    private int maxControlLine = DEFAULT_MAX_CONTROL_LINE;
    private int maxReconnect = DEFAULT_MAX_RECONNECT;
    private Duration reconnectWait = DEFAULT_RECONNECT_WAIT;
    private Duration connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
    private Duration pingInterval = DEFAULT_PING_INTERVAL;
    private Duration requestCleanupInterval = DEFAULT_REQUEST_CLEANUP_INTERVAL;
    private int maxPingsOut = DEFAULT_MAX_PINGS_OUT;
    private long reconnectBufferSize = DEFAULT_RECONNECT_BUF_SIZE;
    private String username = null;
    private String password = null;
    private String token = null;
    private boolean useOldRequestStyle = false;
    private int bufferSize = DEFAULT_BUFFER_SIZE;
    private boolean trackAdvancedStats = false;
    private boolean noEcho = false;

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public boolean isNoRandomize() {
        return noRandomize;
    }

    public void setNoRandomize(boolean noRandomize) {
        this.noRandomize = noRandomize;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isPedantic() {
        return pedantic;
    }

    public void setPedantic(boolean pedantic) {
        this.pedantic = pedantic;
    }

    public SSLContext getSslContext() {
        return sslContext;
    }

    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    public int getMaxControlLine() {
        return maxControlLine;
    }

    public void setMaxControlLine(int maxControlLine) {
        this.maxControlLine = maxControlLine;
    }

    public int getMaxReconnect() {
        return maxReconnect;
    }

    public void setMaxReconnect(int maxReconnect) {
        this.maxReconnect = maxReconnect;
    }

    public Duration getReconnectWait() {
        return reconnectWait;
    }

    public void setReconnectWait(Duration reconnectWait) {
        this.reconnectWait = reconnectWait;
    }

    public Duration getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Duration connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Duration getPingInterval() {
        return pingInterval;
    }

    public void setPingInterval(Duration pingInterval) {
        this.pingInterval = pingInterval;
    }

    public Duration getRequestCleanupInterval() {
        return requestCleanupInterval;
    }

    public void setRequestCleanupInterval(Duration requestCleanupInterval) {
        this.requestCleanupInterval = requestCleanupInterval;
    }

    public int getMaxPingsOut() {
        return maxPingsOut;
    }

    public void setMaxPingsOut(int maxPingsOut) {
        this.maxPingsOut = maxPingsOut;
    }

    public long getReconnectBufferSize() {
        return reconnectBufferSize;
    }

    public void setReconnectBufferSize(long reconnectBufferSize) {
        this.reconnectBufferSize = reconnectBufferSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isUseOldRequestStyle() {
        return useOldRequestStyle;
    }

    public void setUseOldRequestStyle(boolean useOldRequestStyle) {
        this.useOldRequestStyle = useOldRequestStyle;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public boolean isTrackAdvancedStats() {
        return trackAdvancedStats;
    }

    public void setTrackAdvancedStats(boolean trackAdvancedStats) {
        this.trackAdvancedStats = trackAdvancedStats;
    }

    public boolean isNoEcho() {
        return noEcho;
    }

    public void setNoEcho(boolean noEcho) {
        this.noEcho = noEcho;
    }
}
