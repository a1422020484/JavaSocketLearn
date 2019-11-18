package redis.sentinel;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class SingleTest {
	public static void main(String[] args) {
		Config config = new Config();
		config.useSingleServer().setAddress("redis://192.168.96.140:9011").setPassword("yangxp");
		
		RedissonClient redissionClient = Redisson.create(config);
		
		for (int i = 0; i < 10; i++) {
			RBucket<String> keyObject = redissionClient.getBucket("key" + i);
			keyObject.set("value" + i);
		}
		for (int i = 0; i < 10; i++) {
			RBucket<String> keyObject = redissionClient.getBucket("key" + i);
			System.out.println(keyObject.get());
		}
		redissionClient.shutdown();
	}
}
