package cn.wanlinus.nats;

/**
 * @author wanli
 * @date 2018-09-17
 */
public class NatsStreamingException extends RuntimeException {
    private String message;

    public NatsStreamingException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
