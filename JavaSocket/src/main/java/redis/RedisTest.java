package redis;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import redis.clients.jedis.Jedis;

public class RedisTest {

	private static String lockName = "testYxp";
	
	private static Lock lock = new ReentrantLock();

	private static int sum = 0;
	public static void main(String[] args) {
		RedisUtils.init();
//		redis-cli -p 9488 -a DN1XOzA+AAwmO+3TSsB7M/Xr9ItE=
		
		
//		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
//		ScheduledExecutorService scheduledExecutorService1 = Executors.newScheduledThreadPool(5);
//		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//
//			@Override
//			public void run() {
//				addPersonNum(1, 20181217);
//				System.out.println(getPersonNum(20181217) + " --- " + Thread.currentThread().getName());
//			}
//		}, 1, 1, TimeUnit.SECONDS);
//		try {
//			Thread.sleep(600);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		scheduledExecutorService1.scheduleAtFixedRate(new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(600);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				addPersonNum(1, 20181217);
//				System.out.println(getPersonNum(20181217) + " --- " + Thread.currentThread().getName());
//			}
//		}, 1, 1, TimeUnit.SECONDS);
		
		
//		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
//		for (int i = 0; i < 100; i++) {
//			fixedThreadPool.execute(new Runnable() {
//				public void run() {
////					lock.lock();
//					addPersonNum(1, 20181217);
//					System.out.println(getPersonNum(20181217));
////					lock.unlock();
//				}
//			});
//		}
//		fixedThreadPool.shutdown();
//		ExecutorService fixedThreadPool2 = Executors.newFixedThreadPool(3);
//		for (int i = 0; i < 100; i++) {
//			fixedThreadPool2.execute(new Runnable() {
//				public void run() {
//					addPersonNum2(1, 20181217);
//					System.out.println(getPersonNum(20181217));
//				}
//			});
//		}
//		fixedThreadPool2.shutdown();
		StringBuilder redisPersonKey = new StringBuilder();
        redisPersonKey.append(RedisKeys.K_ALL_SERVER_GIFT).append("_").append(202099);
		for (int i = 0; i < 10; i++) {
			RedisUtils.hset(redisPersonKey.toString(), "No" + i, String.valueOf(10 + i));
		}
		for (int i = 0; i < 10; i++) {
			RedisUtils.hincrBy(redisPersonKey.toString(), "No" + i, i);
		}
		RedisUtils.hincrBy(redisPersonKey.toString(), "No" + 1000, 100);
		
		Map<String, String> allNum = RedisUtils.hgetAll(redisPersonKey.toString());
		allNum.values().forEach(e->{
			sum += Integer.valueOf(e);
		});
		System.out.println(sum);
	}

	public static void addPersonNum(int value, int activityId) {
		// k_all_server_gift_20181217
		String redisPersonKey = RedisKeys.K_ALL_SERVER_GIFT + "_" + activityId;
		try {
//			if (RedisUtils.acquireLock(lockName, redis)) {
//				RedisUtils.hincrBy(redisPersonKey, "all", value);
			lock.lock();
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
				RedisUtils.getJedis().hincrBy(redisPersonKey, "all", value);
				lock.unlock();
//			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}
	public static void addPersonNum2(int value, int activityId) {
		// k_all_server_gift_20181217
		String redisPersonKey = RedisKeys.K_ALL_SERVER_GIFT + "_" + activityId;
		try {
//			if (RedisUtils.acquireLock(lockName, redis)) {
//				RedisUtils.hincrBy(redisPersonKey, "all", value);
			lock.lock();
			RedisUtils.getJedis().hincrBy(redisPersonKey, "all", value);
			lock.unlock();
//			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
	}
	
	public static int getPersonNum(int activityId) {
		lock.lock();
		String redisPersonKey = RedisKeys.K_ALL_SERVER_GIFT + "_" + activityId;
//		int a = Integer.parseInt(RedisUtils.hget(redisPersonKey, "all"));
		int a = Integer.parseInt(RedisUtils.getJedis().hget(redisPersonKey, "all"));
		lock.unlock();
		return a;
	}
	
	
}
