//package redis.redissonUtil;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import org.redisson.api.RScript;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import nettyServer.util.cache.PersistEntity;
//
///**
// * Redis 相关的通用操作
// *
// * @author xiezuojie
// */
//public final class RedisUtil {
//
//    private static Logger log = LoggerFactory.getLogger("redis");
//
//    public static int REDIS_KEY_TIME_TO_LIVE = 3600 * 24 * 15; // 缓存有效时间（秒）
//
//    /*
//     * 为什么不设计自动获取类型来生成redis-key？
//     * 1.在一个地方统一声明Key可以很方便的查找引用
//     * 2.在setOneToOne时可以通过类型来获取名称，getOneToOne也可以在传参数时强制指定类型，
//     *   但不是所有场景都适合这样做，如果以后扩展了此方法，参数的多样性将破坏强制性，
//     *   如果开发者不明白代码逻辑，那么很容易使用错误的key关联
//     */
//
//    /**
//     * 获取一对一类型的对象
//     *
//     * @param playerId 玩家ID
//     * @param mapKey   hash的key
//     * @param <T>      对象的类型
//     * @return 返回给定字段的值。如果给定的字段或 key 不存在时，返回 null 。
//     */
//    public static <T> Optional<T> getOneToOne(int playerId, String mapKey) {
//        String key = String.format(RedisKeys.Private.ONE_TO_ONE, playerId);
//        long start = System.currentTimeMillis();
//        Optional<T> opt = RedissonUtil.eval(
//                RedisScripts.HGET_AND_EXPIRE,
//                RedissonKryoEvalCodec.INSTANCE,
//                RScript.Mode.READ_ONLY,
//                RScript.ReturnType.VALUE,
//                Collections.singletonList(key),
//                RedissonKryoEvalCodec.wrapString(mapKey)
//        );
//        long end = System.currentTimeMillis();
//        if (log.isDebugEnabled()) {
//            log.debug("GetOneToOne - PID={} Key={} HashKey={} Hit={} TimeCost={}",
//                    playerId, key, mapKey, opt.isPresent(), end - start);
//        }
//        return opt;
//    }
//
//    /**
//     * 删除一对一类型的对象
//     *
//     * @param playerId 玩家ID
//     * @param mapKey   hash的key
//     * @return 删除结果，被成功移除的域的数量，不包括被忽略的域。
//     */
//    public static Optional<Long> delOneToOne(int playerId, String mapKey) {
//        String key = String.format(RedisKeys.Private.ONE_TO_ONE, playerId);
//        long start = System.currentTimeMillis();
//        Optional<Long> opt = RedissonUtil.eval(
//                RedisScripts.HGET_DEL_EXPIRE,
//                RedissonKryoEvalCodec.INSTANCE,
//                RScript.Mode.READ_WRITE,
//                RScript.ReturnType.INTEGER,
//                Collections.singletonList(key),
//                RedissonKryoEvalCodec.wrapString(mapKey)
//        );
//        long end = System.currentTimeMillis();
//        if (log.isDebugEnabled()) {
//            log.debug("DelOneToOne - PID={} Key={} HashKey={} Return={} TimeCost={}",
//                    playerId, key, mapKey, opt.orElse(0L), end - start);
//        }
//        return opt;
//    }
//
//    /**
//     * 设置一对一类型的对象
//     *
//     * @param playerId 玩家ID
//     * @param mapKey   hash的key
//     * @param value    对象
//     * @param <T>      对象的类型
//     * @return 操作结果，字段是哈希表中的一个新建字段并且值设置成功时返回 true ，哈希表中字段已经存在且旧值已被新值覆盖时返回 false。
//     */
//    public static <T> Optional<Boolean> setOneToOne(int playerId, String mapKey, T value) {
//        String key = String.format(RedisKeys.Private.ONE_TO_ONE, playerId);
//        long start = System.currentTimeMillis();
//        Optional<Boolean> opt = RedissonUtil.eval(
//                RedisScripts.HSET_AND_EXPIRE,
//                RedissonKryoEvalCodec.INSTANCE,
//                RScript.Mode.READ_WRITE,
//                RScript.ReturnType.BOOLEAN,
//                Collections.singletonList(key),
//                RedissonKryoEvalCodec.wrapString(mapKey),
//                value
//        );
//        long end = System.currentTimeMillis();
//        if (opt.isPresent()) { // 执行成功，Redis访问正常
//            if (log.isDebugEnabled()) {
//                log.debug("SetOneToOne - PID={} Key={} HashKey={} ValueType={} Return={} TimeCost={}",
//                        playerId, key, mapKey, value.getClass().getName(), opt.orElse(false), end - start);
//            }
//        } else {
//            if (log.isDebugEnabled()) {
//                String ret = RedissonUtil.isEnable() ? "Exception" : "RedisClosed";
//                log.debug("SetOneToOne - PID={} Key={} HashKey={} ValueType={} Return={} TimeCost={}",
//                        playerId, key, mapKey, value.getClass().getName(), ret, end - start);
//            }
//            // Redis已关闭或异常
//            if (RedissonUtil.isEnable()) {
//                // 更新异常时删除key，尽可能保证数据一致
//                Optional<Long> optDel = delOneToOne(playerId, mapKey);
//                if (optDel.isPresent()) {
//                    log.info("SetOneToOne - PID={} Key={} HashKey={} ValueType={} 由于更新失败，Key被删除({})",
//                            playerId, key, mapKey, value.getClass().getName(), optDel.get());
//                } else {
//                    log.info("SetOneToOne - PID={} Key={} HashKey={} ValueType={} 在更新失败时尝试删除Key，删除失败！",
//                            playerId, key, mapKey, value.getClass().getName());
//                }
//            }
//        }
//
//        return opt;
//    }
//
//    public static boolean setOneToMany(int playerId, String key, List<PersistEntity> persistEntityList) {
//        if (persistEntityList.isEmpty()) {
//            return true;
//        }
//
//        Map<String, PersistEntity> map = new HashMap<>();
//        for (PersistEntity entity : persistEntityList) {
//            LongIDPersistEntity longIDPersistEntity = (LongIDPersistEntity) entity;
//            map.put(String.valueOf(longIDPersistEntity.getId()), longIDPersistEntity);
//        }
//        return setOneToMany(playerId, key, map);
//    }
//
//    public static boolean setOneToMany(int playerId, String key, Map<String, PersistEntity> persistEntityList) {
//        if (persistEntityList.isEmpty()) {
//            return true;
//        }
//
//        long start = System.currentTimeMillis();
//        Optional<Boolean> opt = RedissonUtil.hMSetExpire(key, persistEntityList, REDIS_KEY_TIME_TO_LIVE);
//        long end = System.currentTimeMillis();
//        if (opt.isPresent()) {
//            if (log.isDebugEnabled()) {
//                log.debug("SetOneToMany - PID={} Key={} HashKeys={} TimeCost={}",
//                        playerId, key, persistEntityList.keySet().toString(), end - start);
//            }
//        } else {
//            // Redis已关闭或异常
//            if (RedissonUtil.isEnable()) {
//                // 更新异常时删除key，尽可能保证数据一致
//                Optional<Boolean> optDel = RedissonUtil.del(key);
//                if (optDel.isPresent()) {
//                    log.info("SetOneToMany - PID={} Key={} HashKeys={} TimeCost={} 由于更新失败，Key被删除({})",
//                            playerId, key, persistEntityList.keySet().toString(), end - start, optDel.get());
//                } else {
//                    log.info("SetOneToMany - PID={} Key={} HashKeys={} TimeCost={} 在更新失败时尝试删除Key，删除失败！",
//                            playerId, key, persistEntityList.keySet().toString(), end - start);
//                }
//            }
//        }
//        return opt.orElse(false);
//    }
//
//    public static boolean delOneToMany(int playerId, String key, List<Long> idList) {
//        if (idList.isEmpty()) {
//            return true;
//        }
//
//        long start = System.currentTimeMillis();
//        boolean rs = RedissonUtil.hDelExpire(key, longListToStringArray(idList), REDIS_KEY_TIME_TO_LIVE);
//        long end = System.currentTimeMillis();
//        if (log.isDebugEnabled()) {
//            log.debug("DelOneToMany - PID={} Key={} HashKeys={} TimeCost={}",
//                    playerId, key, idList.toString(), end - start);
//        }
//        return rs;
//    }
//
//    public static <T> Optional<Map<String, T>> getOneToMany(int playerId, String key) {
//        long start = System.currentTimeMillis();
//        Optional<Map<String, T>> opt = RedissonUtil.hGetAllExpire(key, REDIS_KEY_TIME_TO_LIVE);
//        long end = System.currentTimeMillis();
//        if (log.isDebugEnabled()) {
//            log.debug("GetOneToMany - PID={} Key={} Hit={} Len={} TimeCost={}",
//                    playerId, key, opt.isPresent(), opt.map(Map::size).orElse(0), end - start);
//        }
//        return opt;
//    }
//
//    private static String[] longListToStringArray(List<Long> longList) {
//        Object[] longArray = longList.toArray();
//        String[] stringArray = new String[longArray.length];
//        for (int i = 0; i < longArray.length; i++) {
//            stringArray[i] = longArray[i].toString();
//        }
//        return stringArray;
//    }
//
//}
