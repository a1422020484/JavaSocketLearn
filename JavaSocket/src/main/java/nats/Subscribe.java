package nats;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Nats;
import io.nats.client.Subscription;

public class Subscribe {
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, TimeoutException {
		Connection nc = Nats.connect("nats://user:123123@10.0.0.132:4222");
		Subscription sub = nc.subscribe("subject");
		while(true) {
			Message msg = sub.nextMessage(Duration.ofMillis(500));
			if(msg == null) {
				continue;
			}
			String response = new String(msg.getData(), StandardCharsets.UTF_8);
			System.out.println(response);
		}
    }
}
