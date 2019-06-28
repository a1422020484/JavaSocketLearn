package redis;

import java.util.Map;
import java.util.concurrent.locks.Lock;

import redis.clients.jedis.Jedis;

public class RedisUtils {
	/**
	 * 锁的有效时间(单位：毫秒)
	 */
	public static final int expired = 100;

	private static Jedis jedis = null;
	
	private static Lock lock = null;
	/**
	 * 锁超时时间(单位:秒)
	 */
	public static final int lockTimeoutS = 1;

	public static void init() {
		jedis = new Jedis("10.0.0.66", 6379, 1000);
//		jedis.auth("rootroot");
		System.out.println("连接成功");
		// 查看服务是否运行
		System.out.println("服务正在运行: " + jedis.ping());
	}

	/**
	 * 获取Jedis实例
	 *
	 * @return Jedis实例, 获取失败时返回null.
	 */
	public synchronized static Jedis getJedis() {
		if (jedis == null) {
			init();
			return jedis;
		} else {
			return jedis;
		}
	}

	/**
	 * Increment the number stored at field in the hash at key by value
	 *
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static long hincrBy(String key, String field, long value) {
		Jedis redis = RedisUtils.getJedis();
		if (redis == null) {
			return 0;
		}
		long result = 0;
		try {
			result = redis.hincrBy(key, field, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(redis);
		}
		return result;
	}

	/**
	 * 获取指定的hash field
	 *
	 * @param key
	 * @param field
	 * @return 关联的值, 没有值为null.
	 * @see Jedis#hget(String, String)
	 */
	public static String hget(String key, String field) {
		Jedis redis = RedisUtils.getJedis();
		if (redis == null) {
			return null;
		}
		String value = null;
		try {
			value = redis.hget(key, field);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(redis);
		}
		return value;
	}

	/**
     * 设置hash field为指定值，如果key不存在，则先创建
     *
     * @param key   决定使用哪个HashMap
     * @param field 相当于HashMap的key
     * @param value 相当于HashMap的值
     * @see Jedis#hset(String, String, String)
     */
    public static void hset(String key, String field, String value) {
        Jedis redis = RedisUtils.getJedis();
        if (redis == null) {
            return;
        }
        try {
            redis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(redis);
        }
    }
	
    /**
     * 获取在哈希表中指定 key 的所有字段和值
     *
     * @param key
     * @return
     * @see Jedis#hgetAll(String)
     */
    public static Map<String, String> hgetAll(String key) {
        Jedis redis = RedisUtils.getJedis();
        if (redis == null) {
            return null;
        }
        Map<String, String> values = null;
        try {
            values = redis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(redis);
        }
        return values;
    }
    
	/**
	 * 在使用完后关闭
	 *
	 * @param redis
	 */
	public static void close(Jedis redis) {
		if (redis == null) {
			return;
		}
		try {
			redis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 尝试索取分布锁()
	 *
	 * @param lockName
	 * @return 是否拿到锁
	 */
	public static boolean acquireLock(String lockName, Jedis redis) {
		if (redis == null) {
			return false;
		}
		boolean isSuccess = false;
		long value = System.currentTimeMillis() + expired;
		long time = redis.setnx(lockName, String.valueOf(value));
		// 成功获得锁
		if (time == 1) {
			// 过期时间1s 保证准确性
			redis.expire(lockName, lockTimeoutS);
			isSuccess = true;
		} else {
			// 防止多个线程同时竞争
			String string = redis.get(lockName);
			if (string == null) {
				return isSuccess;
			}
			long lockTime = Long.parseLong(redis.get(lockName));
			// 超时删除锁
			if (lockTime < System.currentTimeMillis()) {
				redis.del(lockName);
				isSuccess = true;
			}
			try {
				// 线程休眠 防止饥饿
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return isSuccess;
	}
}
