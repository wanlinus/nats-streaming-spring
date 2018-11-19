package cn.wanlinus.nats;

import io.nats.client.Connection;
import io.nats.client.ConnectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author wanli
 * @date 2018-11-16 17:55
 */
public class NatsListener implements ConnectionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionListener.class);

    private ApplicationContext context;

    public NatsListener(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void connectionEvent(Connection conn, Events type) {
        LOGGER.info("connection status: " + conn.getStatus());
        if (conn.getStatus().equals(Connection.Status.CLOSED)) {
            LOGGER.info("------------------- NATS connection is close, server is shutting down ---------------------");
            SpringApplication.exit(context);
        }
    }
}
