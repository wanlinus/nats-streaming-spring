package cn.wanlinus.natsstreaming;

import io.nats.client.Connection;
import io.nats.client.ConnectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于监听Nats Connection 连接状态
 *
 * @author wanli
 * @date 2018-11-16 20:45
 */
public class NatsListener implements ConnectionListener {

    private static final Logger logger = LoggerFactory.getLogger(NatsListener.class);

    @Override
    public void connectionEvent(Connection conn, Events type) {
        logger.info("connection status:[{}]", conn.getStatus());
        if (conn.getStatus().equals(Connection.Status.CLOSED)) {
            logger.info("NATS connection is closed, server is shutting down");
            System.exit(1);
        }
    }
}
