package cn.wanlinus.natsstreaming;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Instant;

/**
 * @author wanli
 * @date 2018-09-27 10:25
 */
@ConfigurationProperties(prefix = "spring.nats.streaming.subscribe")
public class NatsStreamingSubProperties {
    private String durableName;
    private Integer maxInFlight = 1024;
    private Long ackWait = 30L;
    private Long startSequence;
    private Instant startTime;
    private boolean manualAcks = false;
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    private String startTimeAsDate;
    private Integer subscriptionTimeout = 2;
    private boolean deliverAllAvailable = true;
    private boolean startWithLastReceived = false;

    public String getDurableName() {
        return durableName;
    }

    public void setDurableName(String durableName) {
        this.durableName = durableName;
    }

    public Integer getMaxInFlight() {
        return maxInFlight;
    }

    public void setMaxInFlight(Integer maxInFlight) {
        this.maxInFlight = maxInFlight;
    }

    public Long getAckWait() {
        return ackWait;
    }

    public void setAckWait(Long ackWait) {
        this.ackWait = ackWait;
    }

    public Long getStartSequence() {
        return startSequence;
    }

    public void setStartSequence(Long startSequence) {
        this.startSequence = startSequence;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public boolean isManualAcks() {
        return manualAcks;
    }

    public void setManualAcks(boolean manualAcks) {
        this.manualAcks = manualAcks;
    }

    public String getStartTimeAsDate() {
        return startTimeAsDate;
    }

    public void setStartTimeAsDate(String startTimeAsDate) {
        this.startTimeAsDate = startTimeAsDate;
    }

    public Integer getSubscriptionTimeout() {
        return subscriptionTimeout;
    }

    public void setSubscriptionTimeout(Integer subscriptionTimeout) {
        this.subscriptionTimeout = subscriptionTimeout;
    }

    public boolean isDeliverAllAvailable() {
        return deliverAllAvailable;
    }

    public void setDeliverAllAvailable(boolean deliverAllAvailable) {
        this.deliverAllAvailable = deliverAllAvailable;
    }

    public boolean isStartWithLastReceived() {
        return startWithLastReceived;
    }

    public void setStartWithLastReceived(boolean startWithLastReceived) {
        this.startWithLastReceived = startWithLastReceived;
    }
}
