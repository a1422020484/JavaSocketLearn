//package redis.redissonUtil;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
///**
// * Redis Lua Scripts
// *
// * @author xiezuojie
// */
//public class RedisScripts {
//
//    /**
//     * 在调用HGET后，自动延长有效期
//     * KEY[1]: Redis key
//     * ARGV[1]: map key
//     * RETURN: Object of HGET
//     */
//    public static final RedisScript HGET_AND_EXPIRE = new RedisScript(
//            "local v = redis.call('HGET', KEYS[1], ARGV[1]); redis.call('EXPIRE', KEYS[1], %d); return v;", RedisUtil.REDIS_KEY_TIME_TO_LIVE);
//
//    /**
//     * 在调用HDEL后，自动延长有效期
//     * KEY[1]: Redis key
//     * ARGV[1]: map key
//     * RETURN: Object of HDEL
//     */
//    public static final RedisScript HGET_DEL_EXPIRE = new RedisScript(
//            "local v = redis.call('HDEL', KEYS[1], ARGV[1]); redis.call('EXPIRE', KEYS[1], %d); return v;", RedisUtil.REDIS_KEY_TIME_TO_LIVE);
//
//    /**
//     * 在调用HGET后，自动延长有效期
//     * KEY[1]: Redis key
//     * ARGV[1]: map key
//     * ARGV[2]: map value
//     * RETURN: Value of HSET
//     */
//    public static final RedisScript HSET_AND_EXPIRE = new RedisScript(
//            "local v = redis.call('HSET', KEYS[1], ARGV[1], ARGV[2]); redis.call('EXPIRE', KEYS[1], %d); return v;", RedisUtil.REDIS_KEY_TIME_TO_LIVE);
//
//    public static void build() {
//        Method formatMethod = null;
//        try {
//            formatMethod = RedisScript.class.getDeclaredMethod("format");
//        } catch (NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        }
//
//        Field[] fields = RedisScripts.class.getDeclaredFields();
//        for (Field field : fields) {
//            if (field.getType().isAssignableFrom(RedisScript.class)) {
//                try {
//                    Object o = field.get(RedisScripts.class);
//                    formatMethod.invoke(o);
//                } catch (IllegalAccessException | InvocationTargetException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
//
//    static {
//        build();
//    }
//}
