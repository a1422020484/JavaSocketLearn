package nats;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import io.nats.client.Connection;
import io.nats.client.Nats;

public class Publish2 {
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection nc = Nats.connect("nats://user:123123@10.0.0.132:4222");
		// 消息
		Scanner scanner = new Scanner(System.in);
        System.out.println("请输入字符串：");
        while (true) {
                String line = scanner.nextLine();
                // 发布消息
                nc.publish("subject2", line.getBytes(StandardCharsets.UTF_8));
        }
	}
}
