package redis.redission;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonKey {
	public static void main(String[] args) {
		Config config = new Config();
		config.useClusterServers()
		    .setScanInterval(2000)   
		    .addNodeAddress("redis://192.168.96.140:9001")  
		    .addNodeAddress("redis://192.168.96.140:9002")  
		    .addNodeAddress("redis://192.168.96.140:9003").setPassword("yangxp"); 
		RedissonClient redissionClient = Redisson.create(config);
		
		RKeys rkeys = redissionClient.getKeys();
		System.out.println(rkeys.count());
		
		RLock rlock = redissionClient.getLock("lock1s");
		rlock.lock();
		
		redissionClient.shutdown();
	}
}
