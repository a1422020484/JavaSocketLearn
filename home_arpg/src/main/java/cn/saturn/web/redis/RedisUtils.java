package cn.saturn.web.redis;

import cn.saturn.web.utils.Config;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis工具类
 */
public final class RedisUtils {

    // Redis服务器IP
    private static String ADDR = Config.val("Redis.host");

    // Redis的端口号
    private static int PORT = Config.intVal("Redis.port");

    // 访问密码
    private static String AUTH = Config.val("Redis.password");

    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = Config.intVal("Redis.active");

    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = Config.intVal("Redis.idle");

    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = Config.intVal("Redis.maxwait");

    private static int TIMEOUT = Config.intVal("Redis.timeout");

    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    // 是否开启Redis
    public final static boolean RedisEnable = Config.booleanVal("RedisEnable");
    // 在Redis开启时,按模块开启
    public final static boolean RedisServer = Config.booleanVal("RedisServer");
    public final static boolean RedisVersion = Config.booleanVal("RedisVersion");
    public final static boolean RedisAccount = Config.booleanVal("RedisAccount");
    public final static boolean RedisAccountBind = Config.booleanVal("RedisAccountBind");
    public final static boolean RedisPackage = Config.booleanVal("RedisPackage");
    public final static boolean RedisParam = Config.booleanVal("RedisParam");
    public final static boolean RedisNotice = Config.booleanVal("RedisNotice");
    public final static boolean RedisVindicatorIp = Config.booleanVal("RedisVindicatorIp");
    public final static boolean RedisCdKey = Config.booleanVal("RedisCdKey");
    public final static boolean RedisShieldSys = Config.booleanVal("RedisShieldSys");
    public final static boolean RedisUCGift = Config.booleanVal("RedisUCGift");
    // 保存在Redis中的Token有效时间(秒)
    public final static int RedisTokenExpire = Config.intVal("RedisTokenExpire");

    /**
     * 初始化Redis连接池
     */
    static {
        if (RedisEnable) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            // 根据有没有密码处理
            if (AUTH != null && AUTH.length() > 0) {
                jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH); // 带密码
            } else {
                jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);
            }
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return Jedis实例, 获取失败时返回null.
     */
    public synchronized static Jedis getJedis() {
        if (!RedisEnable) {
            return null;
        }
        try {
            return jedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        Jedis redis = RedisUtils.getJedis();
        if (redis == null) {
            return;
        }
        try {
            redis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(redis);
        }
    }

    /**
     * @param key
     * @param seconds key的过期时间(秒)
     * @param value
     */
    public static void setex(String key, int seconds, String value) {
        Jedis redis = RedisUtils.getJedis();
        if (redis == null) {
            return;
        }
        try {
            redis.setex(key, seconds, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(redis);
        }
    }

    /**
     * @param key
     * @return 与key关联的值, 没有值为null.
     */
    public static String get(String key) {
        Jedis redis = RedisUtils.getJedis();
        if (redis == null) {
            return null;
        }
        try {
            return redis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(redis);
        }
        return null;
    }

    /**
     * @param key
     * @return 成功删除数量
     * @see Jedis#del(String)
     */
    public static long del(String key) {
        Jedis redis = RedisUtils.getJedis();
        if (redis == null) {
            return 0;
        }
        long result = 0;
        try {
            result = redis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(redis);
        }
        return result;
    }

    // hash

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
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     *
     * @param key
     * @param hash
     * @see Jedis#hmset(String, Map)
     */
    public static void hmset(String key, Map<String, String> hash) {
        Jedis redis = RedisUtils.getJedis();
        if (redis == null) {
            return;
        }
        try {
            redis.hmset(key, hash);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(redis);
        }
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
     * 获取所有给定字段的值
     *
     * @param key
     * @param fields
     * @return List<String>
     * @see Jedis#hmget(String, String...)
     */
    public static List<String> hmget(String key, String... fields) {
        Jedis redis = RedisUtils.getJedis();
        if (redis == null) {
            return null;
        }
        List<String> values = null;
        try {
            values = redis.hmget(key, fields);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(redis);
        }
        return values;
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key
     * @return Set<String>
     * @see Jedis#hkeys(String)
     */
    public static Set<String> hkeys(String key) {
        Jedis redis = RedisUtils.getJedis();
        if (redis == null) {
            return null;
        }
        Set<String> values = null;
        try {
            values = redis.hkeys(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(redis);
        }
        return values;
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key
     * @return List<String>
     * @see Jedis#hvals(String)
     */
    public static List<String> hvals(String key) {
        Jedis redis = RedisUtils.getJedis();
        if (redis == null) {
            return null;
        }
        List<String> values = null;
        try {
            values = redis.hvals(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(redis);
        }
        return values;
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
     * 测试指定field是否存在
     *
     * @param key
     * @param field
     * @return 是否存在
     * @see Jedis#hexists(String, String)
     */
    public static boolean hexists(String key, String field) {
        Jedis redis = RedisUtils.getJedis();
        if (redis == null) {
            return false;
        }
        boolean result = false;
        try {
            result = redis.hexists(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(redis);
        }
        return result;
    }

    /**
     * @param key
     * @return 指定hash的field数量
     * @see Jedis#hlen(String)
     */
    public static long hlen(String key) {
        Jedis redis = RedisUtils.getJedis();
        if (redis == null) {
            return 0;
        }
        long result = 0;
        try {
            result = redis.hlen(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(redis);
        }
        return result;
    }

    /**
     * @param key
     * @param fields
     * @return 成功删除数量
     * @see Jedis#hdel(String, String...)
     */
    public static long hdel(String key, String... fields) {
        Jedis redis = RedisUtils.getJedis();
        if (redis == null) {
            return 0;
        }
        long result = 0;
        try {
            result = redis.hdel(key, fields);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(redis);
        }
        return result;
    }

}