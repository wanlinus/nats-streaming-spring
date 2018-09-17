package cn.wanlinus.natsstreaming;

import io.nats.streaming.NatsStreaming;
import io.nats.streaming.Options;
import io.nats.streaming.StreamingConnection;
import io.nats.streaming.SubscriptionOptions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

/**
 * @author wanli
 * @date 2018-09-17
 */
public class NatsSubscribe {
    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {

        Options streamingOptions = new Options.Builder().natsUrl("nats://118.31.50.72:4222").build();
//        StreamingConnectionFactory cf = new StreamingConnectionFactory("test-cluster", "sub");
        StreamingConnection sc = NatsStreaming.connect("test-cluster", "sub", streamingOptions);
//        Subscription sub = sc.subscribe("bb", msg ->
//                        System.out.println(new String(msg.getData(), StandardCharsets.UTF_8)),
//                new SubscriptionOptions.Builder().durableName("asd").deliverAllAvailable().build());
        sc.subscribe("bb", msg -> {
            System.out.println(new String(msg.getData(), StandardCharsets.UTF_8));
            try {
                msg.ack();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, new SubscriptionOptions.Builder().manualAcks().ackWait(Duration.ofSeconds(30)).durableName("asd").deliverAllAvailable().build());
    }
}
