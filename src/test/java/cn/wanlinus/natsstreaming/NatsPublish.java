package cn.wanlinus.natsstreaming;

import io.nats.client.Nats;
import io.nats.streaming.StreamingConnection;
import io.nats.streaming.StreamingConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author wanli
 * @date 2018-09-17
 */
public class NatsPublish {
    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {

        StreamingConnectionFactory cf = new StreamingConnectionFactory("test-cluster", "bar");
        cf.setNatsConnection(Nats.connect("nats://127.0.0.1:4222"));
        StreamingConnection sc = cf.createConnection();


        for (int i = 0; i < 10000; i++) {
            System.out.println(i);
            sc.publish("aa", ("HelloWorld: " + i).getBytes(StandardCharsets.UTF_8), (guid, err) -> {
                if (err != null) {
                    System.out.printf("error publish msg id %s: %s\n", guid, err.getMessage());
                } else {
                    System.out.printf("Received ack for msg id %s\n", guid);
                }
            });
            Thread.sleep(500);
        }


    }
}
