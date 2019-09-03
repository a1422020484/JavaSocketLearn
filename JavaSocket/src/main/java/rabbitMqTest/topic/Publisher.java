package rabbitMqTest.topic;

import java.io.IOException;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import rabbitMqTest.Config;

public class Publisher {

	public static void main(String[] args) throws IOException {
		Connection conn = Config.getRabbitConnection();
		for (int i = 0; i < 19; i++) {
			doPublisher(conn);
		}
		conn.close();
	}

	/**
	 * 推送消息
	 */
	public static void doPublisher(Connection conn) {
		// 创建一个连接
		
		if (conn != null) {
			try {
				// 创建通道
				Channel channel = conn.createChannel();
				// 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式，如果为true只能被一个connection使用，其他连接建立时会抛出异常；
				// 参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
				channel.queueDeclare(Config.QueueName2, true, false, false, null);
				// channel.queueBind(Config.QueueName, Config.EXCHANGE_NAME,
				// Config.ROUTING_KEY);
				String content = String.format("当前时间：%s", new Date().getTime());
				// 发送内容【参数说明：参数一：交换机名称；参数二：队列名称，参数三：消息的其他属性-routing
				// headers，此属性为MessageProperties.PERSISTENT_TEXT_PLAIN用于设置纯文本消息存储到硬盘；参数四：消息主体】
				channel.basicPublish(Config.EXCHANGE_NAME2, Config.ROUTING_KEY2, null, content.getBytes("UTF-8"));
				System.out.println("已发送消息：" + content);
				// 关闭连接
				channel.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
