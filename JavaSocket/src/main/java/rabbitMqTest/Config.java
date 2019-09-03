package rabbitMqTest;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Config {

	public static final String UserName = "yang";
	public static final String Password = "yang123";
	public static final String VHost = "/yang123";
	public static final String Host = "192.168.116.130";
	public static final int Port = 5672;
	public static final String QueueName1 = "testYang";
	public static final String QueueName2 = "topic_queue";
	
	public static final String EXCHANGE_NAME1 = "exchange_demo";
	public static final String EXCHANGE_NAME2 = "exchange_demo_topic";
	public static final String ROUTING_KEY1 = "routingkey_demo";
	public static final String ROUTING_KEY2 = "routingkey_demo_topic.1";

	public static Connection getRabbitConnection() {
		ConnectionFactory factory = new ConnectionFactory();
//		ExecutorService executorService = Executors.newSingleThreadExecutor();
//		executorService.execute(new Runnable() {
//			
//			@Override
//			public void run() {
//				System.out.println("shutdown");
//				
//			}
//		}); 
//		factory.setShutdownExecutor(executorService);
		factory.setUsername(Config.UserName);
		factory.setPassword(Config.Password);
		factory.setVirtualHost(Config.VHost);
		factory.setHost(Config.Host);
		factory.setPort(Config.Port);
		Connection conn = null;
		try {
			conn = factory.newConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
}
