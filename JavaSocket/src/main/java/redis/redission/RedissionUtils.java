package redis.redission;

import org.redisson.Redisson;
import org.redisson.api.ClusterNodesGroup;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissionUtils {
	private RedissionUtils() {
	};

	private static RedissionUtils redissionUtils;

	public static RedissionUtils getInstence() {
		if (redissionUtils == null) {
			synchronized (RedissionUtils.class) {
				if (redissionUtils == null) {
					redissionUtils = new RedissionUtils();
				}
			}
		}
		return redissionUtils;
	}

	public RedissonClient getRedissionClient() {
		Config config = new Config();
		config.useClusterServers()
		    // 主节点变化扫描间隔时间  
		    .setScanInterval(2000)   
		    //设置云服务商的redis服务IP和端口，目前支持亚马逊云的AWS ElastiCache和微软云的Azure Redis 缓存  
		    .addNodeAddress("redis://192.168.96.140:9001")  
		    .addNodeAddress("redis://192.168.96.140:9002")  
		    .addNodeAddress("redis://192.168.96.140:9003").setPassword("yangxp"); 
		//创建客户端(发现这一非常耗时，基本在2秒-4秒左右)  
		RedissonClient redissionClient = Redisson.create(config); 
//		ClusterNodesGroup cli = redissionClient.getClusterNodesGroup();
//		cli.pingAll();
		//首先获取redis中的key-value对象，key不存在没关系  
//		RBucket<String> keyObject = redissionClient.getBucket("key");  
		//如果key存在，就设置key的值为新值value  
		//如果key不存在，就设置key的值为value  
//		keyObject.set("value");  
//		for (int i = 0; i < 10; i++) {
//			RBucket<String> keyObject = redissionClient.getBucket("ffkey1" + i);  
//			keyObject.set("value" + i);  
//		}
//		//最后关闭RedissonClient  
//		redissionClient.shutdown();  
		return redissionClient;
	}

	/**
	 * 测试代码
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RedissonClient redissonClient = RedissionUtils.getInstence().getRedissionClient();

//		for (int i = 0; i < 100; i++) {
//			System.out.println(redissonClient.getQueue("queue").poll());
//		}
//
//		System.out.println(redissonClient.getQueue("queue").isExists());

	}
}
