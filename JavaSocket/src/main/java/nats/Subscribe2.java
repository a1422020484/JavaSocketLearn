package nats;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.Nats;
import io.nats.client.Subscription;

public class Subscribe2 {
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, TimeoutException {
		Connection nc = Nats.connect("nats://user:123123@10.0.0.132:4222");

		Map<String, Consumer<Message>> handlers = new HashMap<>(32);
		handlers.put("subject", Subscribe2::doSomething1);
		handlers.put("subject2", Subscribe2::doSomething2);
		handlers.put("subject3", Subscribe2::doSomething3);

		Dispatcher disp = nc.createDispatcher((msg) -> {
			Consumer<Message> handler = handlers.get(msg.getSubject());
            if (handler != null) {
                handler.accept(msg);
            }
		});

		handlers.keySet().forEach(disp::subscribe);
	}

	public static void doSomething1(Message msg) {
		System.out.println("doSomething1 ======== ");
		String response = new String(msg.getData(), StandardCharsets.UTF_8);
		System.out.println(response);
	}

	public static void doSomething2(Message msg) {
		System.out.println("doSomething2 ======== ");
		String response = new String(msg.getData(), StandardCharsets.UTF_8);
		System.out.println(response);
	}

	public static void doSomething3(Message msg) {
		System.out.println("doSomething3 ======== ");
		String response = new String(msg.getData(), StandardCharsets.UTF_8);
		System.out.println(response);
	}
}
