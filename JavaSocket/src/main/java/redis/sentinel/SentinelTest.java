package redis.sentinel;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class SentinelTest {
	private SentinelTest() {
	};

	private static SentinelTest redissionUtils;

	public RedissonClient getRedissionClient() {
		Config config = new Config();
		
		config.useSentinelServers()
			.addSentinelAddress("redis://192.168.96.140:16000")
			.addSentinelAddress("redis://192.168.96.140:16001")
			.addSentinelAddress("redis://192.168.96.140:16002")
			.setPassword("yangxp").setMasterName("macrog-master");
		//创建客户端(发现这一非常耗时，基本在2秒-4秒左右)  
		
		RedissonClient redissionClient = Redisson.create(config); 
		for (int i = 0; i < 100; i++) {
			RBucket<String> keyObject = redissionClient.getBucket("SenKey" + i);  
			keyObject.set("SenKey" + i);  
		}
		redissionClient.shutdown();  
		return redissionClient;
	}

	/**
	 * 测试代码
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RedissonClient redissonClient = SentinelTest.getInstence().getRedissionClient();
	}
	
	public static SentinelTest getInstence() {
		if (redissionUtils == null) {
			synchronized (SentinelTest.class) {
				if (redissionUtils == null) {
					redissionUtils = new SentinelTest();
				}
			}
		}
		return redissionUtils;
	}

	
}	
