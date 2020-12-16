//package redis.redissonUtil;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.net.InetSocketAddress;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.OptionalDouble;
//import java.util.OptionalInt;
//import java.util.OptionalLong;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//import org.apache.commons.lang.StringUtils;
//import org.redisson.Redisson;
//import org.redisson.api.BatchOptions;
//import org.redisson.api.ClusterNodesGroup;
//import org.redisson.api.RAtomicDouble;
//import org.redisson.api.RAtomicLong;
//import org.redisson.api.RBitSet;
//import org.redisson.api.RBucket;
//import org.redisson.api.RDeque;
//import org.redisson.api.RHyperLogLog;
//import org.redisson.api.RKeys;
//import org.redisson.api.RList;
//import org.redisson.api.RMap;
//import org.redisson.api.RPatternTopic;
//import org.redisson.api.RQueue;
//import org.redisson.api.RScoredSortedSet;
//import org.redisson.api.RScript;
//import org.redisson.api.RSet;
//import org.redisson.api.RTopic;
//import org.redisson.api.RedissonClient;
//import org.redisson.api.listener.MessageListener;
//import org.redisson.api.listener.PatternMessageListener;
//import org.redisson.client.codec.Codec;
//import org.redisson.client.protocol.ScoredEntry;
//import org.redisson.config.ClusterServersConfig;
//import org.redisson.config.Config;
//import org.redisson.connection.ConnectionListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import nettyServer.util.GameConfig;
//
///**
// * Redisson 功能类
// * <p>
// * 默认使用<code>{@link RedissonKryoCodec}</code>序列化为字节数组保存到Redis
// * <p>
// * Map的key默认使用{@link org.redisson.client.codec.StringCodec}序列化为字符串,
// * value默认使用{@link RedissonKryoCodec}序列化为字节数组
// * <p>
// * 有些方法返回的是{@link Optional},在返回{@link Optional}时,
// * 文档中描述的'异常时返回null'指的是{@link Optional#empty()},
// * 不包含值,但本身不会是null.
// *
// * @author xiezuojie
// */
//public class RedissonUtil {
//
//    private static Logger log = LoggerFactory.getLogger("redis");
//
//    static RedissonClient cli;
//
//    static ClusterServersConfig clusterServersConfig;
//    static BatchOptions batchOptions;
//
//    private static final AtomicBoolean enable = new AtomicBoolean(true);
//
//    private static List<RedisStateListener> stateListeners = new ArrayList<>(2);
//
//    static {
//        String configFile = "/Redisson.yml";
//        URL url = RedissonUtil.class.getResource(configFile);
//        File file = null;
//        try {
//            file = new File(url.toURI());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        if (file == null || !file.exists()) {
//            System.err.println(String.format("没有找到配置文件: %s", configFile));
//            System.exit(0);
//        }
//
//        Config config = null;
//        try {
//            config = Config.fromYAML(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (config == null) {
//            System.err.println(String.format("解析配置文件错误: %s", configFile));
//            System.exit(0);
//        }
//
//        try {
//            Field field = Config.class.getDeclaredField("clusterServersConfig");
//            field.setAccessible(true);
//            clusterServersConfig = (ClusterServersConfig) field.get(config);
//            if (clusterServersConfig == null) {
//                throw new Error("不能读取Config.clusterServersConfig，请检查Redisson版本");
//            }
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        batchOptions = BatchOptions
//                .defaults()
//                .retryAttempts(clusterServersConfig.getRetryAttempts())
//                .retryInterval(clusterServersConfig.getRetryInterval(), TimeUnit.MILLISECONDS)
//                .responseTimeout(clusterServersConfig.getTimeout(), TimeUnit.MILLISECONDS)
//                .executionMode(BatchOptions.ExecutionMode.REDIS_WRITE_ATOMIC);
//
//        config.setCodec(new RedissonKryoCodec());
//        RedissonClient cli = null;
//        try {
//            cli = Redisson.create(config);
//        } catch (Exception e) {
//            System.err.println(String.format("Redis 连接失败：%s %s",
//                    e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : ""));
//            System.exit(0);
//        }
//        ClusterNodesGroup group = cli.getClusterNodesGroup();
//        group.addConnectionListener(new ConnectionListener() {
//            @Override
//            public void onConnect(InetSocketAddress addr) {
//                log.info("onConnect:" + addr.getAddress().toString() + ":" + addr.getPort());
//                // 经测试，在这里调用Group.pingAll()会阻塞
//            }
//
//            @Override
//            public void onDisconnect(InetSocketAddress addr) {
//                log.info("onDisconnect:" + addr.getAddress().toString() + ":" + addr.getPort());
//                // 这里正常关闭服务器时也会调用
//            }
//        });
//        group.pingAll();
//        log.info("集群连接成功!");
//        RedissonUtil.cli = cli;
//    }
//
//    /**
//     * 设置 Redis 功能开关
//     *
//     * @param enable true 开启，false 关闭。
//     */
//    public static void setEnable(boolean enable) {
//        RedissonUtil.enable.set(enable);
//    }
//
//    /**
//     * @return Redis 功能是否开启，true 开启，false 关闭。
//     */
//    public static boolean isEnable() {
//        return enable.get();
//    }
//
//    /**
//     * 获取Redisson客户端
//     * <p>
//     * <b>注意：不能执行RedissonUtil.getRedisson().shutdown(); 如果在服务器运行时执行了关闭，
//     * 将导致有些Redis操作卡死，服务器不能正常保存数据，数据丢失！</b>
//     *
//     * @return RedissonClient
//     */
//    public static RedissonClient getRedisson() {
//        return cli;
//    }
//
//    /**
//     * 给集群所有节点发送 PING 协议，在所有节点回应 PONG 时正常
//     *
//     * @return 集群正常回应时返回 true ，否则返回 false。
//     * @throws Exception 在 Redis 不可用时抛出异常。
//     */
//    public static boolean testCluster() {
//        // 在Redis异常时可能导致卡死
//        if (cli.isShuttingDown() || cli.isShutdown()) {
//            return false;
//        }
//
//        RBucket<?> bucket = RedissonUtil.cli.getBucket("=====JUST-TEST-CLUSTER=====");
//        bucket.isExists(); // 不需要Key真的存在，只是测试能否正常访问
//        return true;
//    }
//
////    // 此方法是创建新的Redis连接来发送Ping命令
////    public static boolean pingAll() {
////        // 在使用多个客户端的情况下可以共享同一个EventLoopGroup
////        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
////
////        try {
////            RedissonClient cli = RedissonUtil.getRedisson();
////            Config config = cli.getConfig();
////            Field clusterServersConfigField = Config.class.getDeclaredField("clusterServersConfig");
////            clusterServersConfigField.setAccessible(true);
////            ClusterServersConfig clusterServersConfig = (ClusterServersConfig) clusterServersConfigField.get(config);
////
////            Map<RedisConnection, RFuture<String>> result = new ConcurrentHashMap<>();
////            ClusterNodesGroup group = cli.getClusterNodesGroup();
////            group.getNodes(NodeType.MASTER).forEach(node -> {
////                RedisClientConfig redisClientConfig = new RedisClientConfig();
////                String host = node.getAddr().getAddress().getHostAddress();
////                int port = node.getAddr().getPort();
////                redisClientConfig.setAddress(host, port);
////                redisClientConfig.setPassword(clusterServersConfig.getPassword());
////                redisClientConfig.setGroup(eventLoopGroup);
////                redisClientConfig.setDatabase(0);
////
////                RedisClient client = RedisClient.create(redisClientConfig);
////                RedisConnection conn = client.connect();
////                conn.closeAsync();
////                RFuture<String> r = conn.async(StringCodec.INSTANCE, RedisCommands.PING);
////                result.put(conn, r);
////            });
////
////            boolean res = true;
////            for (Map.Entry<RedisConnection, RFuture<String>> entry : result.entrySet()) {
////                RFuture<String> f = entry.getValue();
////                f.awaitUninterruptibly();
////                if (!"PONG".equals(f.getNow())) {
////                    res = false;
////                }
////                entry.getKey().closeAsync();
////                entry.getKey().getRedisClient().shutdown();
////            }
////            return res;
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////        return false;
////    }
//
//    /**
//     * 检查指定 key 是否存在
//     *
//     * @param key redis key
//     * @return 若 key 存在，返回 true ，否则返回 false，异常时返回 null 。
//     */
//    public static Optional<Boolean> exists(String key) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RBucket<?> bucket = cli.getBucket(key);
//            return Optional.of(bucket.isExists());
//        } catch (Exception e) {
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * 设置指定 key 的值
//     *
//     * @param key   redis key
//     * @param value value to set
//     * @param <T>   type of value
//     * @return 设置成功时返回 true ，异常时返回 false 。
//     */
//    public static <T> boolean set(String key, T value) {
//        if (!isEnable()) {
//            return false;
//        }
//        try {
//            RBucket<T> bucket = cli.getBucket(key);
//            bucket.set(value);
//            return true;
//        } catch (Exception e) {
//            log.error(String.format("SET %s %s >> %s", key, value, exceptionStack(e)));
//            onException(e, key);
//            return false;
//        }
//    }
//
//    /**
//     * 设置指定 key 的值，同时指定存活时间
//     *
//     * @param key        redis key
//     * @param value      value to set
//     * @param timeToLive time to live interval
//     * @param timeUnit   unit of time to live interval
//     * @param <T>        type of value
//     * @return 设置成功时返回 true ，异常时返回 false 。
//     */
//    public static <T> boolean set(String key, T value, long timeToLive, TimeUnit timeUnit) {
//        if (!isEnable()) {
//            return false;
//        }
//        try {
//            RBucket<T> bucket = cli.getBucket(key);
//            bucket.set(value, timeToLive, timeUnit);
//            return true;
//        } catch (Exception e) {
//            log.error(String.format("SET %s %s %s(%s) >> %s", key, value, timeToLive, timeUnit.name(), exceptionStack(e)));
//            onException(e, key);
//            return false;
//        }
//    }
//
//    /**
//     * 命令在指定的 key 不存在时，为 key 设置指定的值。
//     *
//     * @param key   redis key
//     * @param value value to set
//     * @param <T>   type of value
//     * @return 设置成功时返回 true ，在元素已经存在时返回 false ，异常时返回 null 。
//     */
//    public static <T> Optional<Boolean> setNX(String key, T value) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RBucket<T> bucket = cli.getBucket(key);
//            return Optional.of(bucket.trySet(value));
//        } catch (Exception e) {
//            log.error(String.format("SETNX %s %s >> %s", key, value, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * 获取指定 key 的值
//     *
//     * @param key redis key
//     * @param <T> type of value
//     * @return 与 key 关联的值，在值不存在或异常时返回 null 。
//     */
//    public static <T> Optional<T> get(String key) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RBucket<T> bucket = cli.getBucket(key);
//            return Optional.ofNullable(bucket.get());
//        } catch (Exception e) {
//            log.error(String.format("GET %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis DEL 命令用于删除已存在的键，不存在的 key 会被忽略。
//     *
//     * @param key redis key
//     * @return key 存在并成功删除时返回 true ，否则返回 false ，异常时返回 null 。
//     */
//    public static Optional<Boolean> del(String key) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RBucket bucket = cli.getBucket(key);
//            return Optional.of(bucket.delete());
//        } catch (Exception e) {
//            log.error(String.format("DEL %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis DEL 命令用于删除已存在的键，不存在的 key 会被忽略。
//     *
//     * @param key redis key
//     * @param <T> type of value
//     * @return 与 key 关联的值，值不存在或异常时返回 null 。
//     */
//    public static <T> Optional<T> getAndDelete(String key) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RBucket<T> bucket = cli.getBucket(key);
//            return Optional.ofNullable(bucket.getAndDelete());
//        } catch (Exception e) {
//            log.error(String.format("GET AND DEL %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
//     *
//     * @param key   redis key
//     * @param value value to set
//     * @param <T>   type of value
//     * @return 与 key 关联的旧值，旧值不存在或异常时返回 null 。
//     */
//    public static <T> Optional<T> getAndSet(String key, T value) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RBucket<T> bucket = cli.getBucket(key);
//            return Optional.ofNullable(bucket.getAndSet(value));
//        } catch (Exception e) {
//            log.error(String.format("GETSET %s %s >> %s", key, value, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)。
//     *
//     * @param key      redis key
//     * @param bitIndex index of bit
//     * @return 位被设置为 1 时返回 true ，否则返回 false ，异常时返回 null 。
//     */
//    public static Optional<Boolean> getBit(String key, long bitIndex) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RBitSet bitSet = cli.getBitSet(key);
//            return Optional.of(bitSet.get(bitIndex));
//        } catch (Exception e) {
//            log.error(String.format("GETBIT %s %d >> %s", key, bitIndex, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
//     *
//     * @param key      redis key
//     * @param bitIndex index of bit
//     * @param value    true = 1, false = 0
//     */
//    public static boolean setBit(String key, long bitIndex, boolean value) {
//        if (!isEnable()) {
//            return false;
//        }
//        try {
//            RBitSet bitSet = cli.getBitSet(key);
//            bitSet.set(bitIndex, value);
//            return true;
//        } catch (Exception e) {
//            log.error(String.format("SETBIT %s %d %b >> %s", key, bitIndex, value, exceptionStack(e)));
//            onException(e, key);
//            return false;
//        }
//    }
//
//    /**
//     * 随机获取 key
//     *
//     * @return 随机的 key ，在没有获取到任何 key 或异常时返回 null 。
//     */
//    public static String randomKey() {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RKeys keys = cli.getKeys();
//            return keys.randomKey();
//        } catch (Exception e) {
//            log.error(String.format("RANDOMKEY >> %s", exceptionStack(e)));
//            onException(e, null);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Incr 命令将 key 中储存的数字值增一。
//     * <p>
//     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
//     * <p>
//     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
//     * <p>
//     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
//     *
//     * @param key redis key
//     * @return 执行 INCR 后的值，异常时返回 null 。
//     */
//    public static OptionalLong incr(String key) {
//        if (!isEnable()) {
//            return OptionalLong.empty();
//        }
//        try {
//            RAtomicLong atomicLong = cli.getAtomicLong(key);
//            return OptionalLong.of(atomicLong.incrementAndGet());
//        } catch (Exception e) {
//            log.error(String.format("INCR %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return OptionalLong.empty();
//        }
//    }
//
//    /**
//     * Redis Incrby 命令将 key 中储存的数字加上指定的增量值。
//     * <p>
//     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
//     * <p>
//     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
//     * <p>
//     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
//     *
//     * @param key   redis key
//     * @param delta the value to add
//     * @return 执行 INCRBY 后的值，异常时返回 null 。
//     */
//    public static Long incrBy(String key, long delta) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RAtomicLong atomicLong = cli.getAtomicLong(key);
//            return atomicLong.addAndGet(delta);
//        } catch (Exception e) {
//            log.error(String.format("INCRBY %s %d >> %s", key, delta, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Incrbyfloat 命令为 key 中所储存的值加上指定的浮点数增量值。
//     * <p>
//     * 如果 key 不存在，那么 INCRBYFLOAT 会先将 key 的值设为 0 ，再执行加法操作。
//     *
//     * @param key   redis key
//     * @param delta the value to add
//     * @return 执行 INCRBYFLOAT 后的值，异常时返回 null 。
//     */
//    public static Double incrByFloat(String key, double delta) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RAtomicDouble atomicDouble = cli.getAtomicDouble(key);
//            return atomicDouble.addAndGet(delta);
//        } catch (Exception e) {
//            log.error(String.format("INCRBYFLOAT %s %f >> %s", key, delta, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Decr 命令将 key 中储存的数字值减一。
//     * <p>
//     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
//     * <p>
//     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
//     * <p>
//     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
//     *
//     * @param key redis key
//     * @return 执行 DECR 后的值，异常时返回 null 。
//     */
//    public static Long decr(String key) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RAtomicLong atomicLong = cli.getAtomicLong(key);
//            return atomicLong.decrementAndGet();
//        } catch (Exception e) {
//            log.error(String.format("DECR %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Decrby 命令将 key 所储存的值减去指定的减量值。
//     * <p>
//     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECRBY 操作。
//     * <p>
//     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
//     * <p>
//     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
//     *
//     * @param key   redis key
//     * @param delta the value to dec
//     * @return 执行 DECRBY 后的值，异常时返回 null 。
//     */
//    public static Long decrBy(String key, long delta) {
//        return incrBy(key, -delta);
//    }
//
//    // Redis 哈希(Hash)命令
//
//    /**
//     * Redis Hdel 命令用于删除哈希表 key 中的一个或多个指定字段，不存在的字段将被忽略。
//     *
//     * @param key    redis key
//     * @param fields map keys
//     * @return 被成功删除字段的数量，不包括被忽略的字段，异常时返回 null 。
//     */
//    public static OptionalLong hDel(String key, String... fields) {
//        if (!isEnable()) {
//            return OptionalLong.empty();
//        }
//        try {
//            RMap<String, Object> map = cli.getMap(key);
//            return OptionalLong.of(map.fastRemove(fields));
//        } catch (Exception e) {
//            log.error(String.format("HDEL %s %s >> %s", key, Arrays.toString(fields), exceptionStack(e)));
//            onException(e, key);
//            return OptionalLong.empty();
//        }
//    }
//
//    /**
//     * Redis Hdel 命令用于删除哈希表 key 中的一个或多个指定字段，不存在的字段将被忽略。
//     * <p>
//     * 同时设置Key的生存时间，秒。
//     *
//     * @param key        redis key
//     * @param fields     map keys
//     * @param timeToLive key的生存时间，秒。
//     * @return 操作成功时返回 true ，异常时返回 null 。
//     */
//    public static boolean hDelExpire(String key, String[] fields, long timeToLive) {
//        if (!isEnable()) {
//            return false;
//        }
//        try {
//            RMap<String, Object> map = cli.getMap(key);
//            map.fastRemoveAsync(fields);
//            map.expireAsync(timeToLive, TimeUnit.SECONDS);
//            return true;
//        } catch (Exception e) {
//            log.error(String.format("HDEL AND EXPIRE %s %s timeToLive(%d) >> %s", key, Arrays.toString(fields), timeToLive, exceptionStack(e)));
//            onException(e, key);
//            return false;
//        }
//    }
//
//    /**
//     * Redis Hexists 命令用于查看哈希表的指定字段是否存在。
//     *
//     * @param key   redis key
//     * @param field map key
//     * @return 哈希表含有给定字段时返回 true ，哈希表不含有给定字段或 key 不存在时返回 false ，异常时返回 null 。
//     */
//    public static Optional<Boolean> hExists(String key, String field) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RMap<String, Object> map = cli.getMap(key);
//            return Optional.of(map.containsKey(field));
//        } catch (Exception e) {
//            log.error(String.format("HEXISTS %s %s >> %s", key, field, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Hset 命令用于为哈希表中的字段赋值 。
//     * <p>
//     * 如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
//     * <p>
//     * 如果字段已经存在于哈希表中，旧值将被覆盖。
//     *
//     * @param key   redis key
//     * @param field map key
//     * @param value map value
//     * @param <T>   type of value
//     * @return 字段是哈希表中的一个新建字段并且值设置成功时返回 true ，哈希表中字段已经存在且旧值已被新值覆盖时返回 false ，异常时返回 null 。
//     */
//    public static <T> Optional<Boolean> hSet(String key, String field, T value) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RMap<String, T> map = cli.getMap(key);
//            return Optional.of(map.fastPut(field, value));
//        } catch (Exception e) {
//            log.error(String.format("HSET %s %s %s >> %s", key, field, value, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Hsetnx 命令用于为哈希表中不存在的的字段赋值 。
//     * <p>
//     * 如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
//     * <p>
//     * 如果字段已经存在于哈希表中，操作无效。
//     * <p>
//     * 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
//     *
//     * @param key   redis key
//     * @param field map key
//     * @param value map value
//     * @param <T>   type of value
//     * @return 设置成功时返回 true ，给定字段已经存在且没有操作被执行时返回 false ，集群不可用时返回 null 。
//     */
//    public static <T> Optional<Boolean> hSetNX(String key, String field, T value) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RMap<String, T> map = cli.getMap(key);
//            return Optional.of(map.fastPutIfAbsent(field, value));
//        } catch (Exception e) {
//            log.error(String.format("HSETNX %s %s %s >> %s", key, field, value, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Hget 命令用于返回哈希表中指定字段的值。
//     *
//     * @param key   redis key
//     * @param field field
//     * @param <T>   type of value
//     * @return 返回给定字段的值。如果给定的字段或 key 不存在时，返回 null 。
//     */
//    public static <T> Optional<T> hGet(String key, String field) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RMap<String, T> map = cli.getMap(key);
//            return Optional.ofNullable(map.get(field));
//        } catch (Exception e) {
//            log.error(String.format("HGET %s %s >> %s", key, field, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Hgetall 命令用于返回哈希表中，所有的字段和值。
//     *
//     * @param key redis key
//     * @param <T> type of value
//     * @return 包含所有字段和值的哈希表，在哈希表不存在或哈希表内容为空时返回的是一个 size 为 0 的 Map ，异常时返回 null 。
//     */
//    public static <T> Optional<Map<String, T>> hGetAll(String key) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RMap<String, T> map = cli.getMap(key);
//            return Optional.of(map.readAllMap());
//        } catch (Exception e) {
//            log.error(String.format("HGETALL %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Hgetall 命令用于返回哈希表中，所有的字段和值。
//     * <p>
//     * 同时设置Key的生存时间，秒。
//     *
//     * @param key        redis key
//     * @param timeToLive key的生存时间，秒。
//     * @param <T>        type of value
//     * @return 包含所有字段和值的哈希表，在哈希表不存在或哈希表内容为空时返回的是一个 size 为 0 的 Map ，异常时返回 null 。
//     */
//    public static <T> Optional<Map<String, T>> hGetAllExpire(String key, long timeToLive) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RMap<String, T> map = cli.getMap(key);
//            Optional<Map<String, T>> rs = Optional.of(map.readAllMap());
//            map.expire(timeToLive, TimeUnit.SECONDS);
//            return rs;
//        } catch (Exception e) {
//            log.error(String.format("HGETALL AND EXPIRE %s timeToLive(%d) >> %s", key, timeToLive, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Hvals 命令返回哈希表所有字段的值。
//     *
//     * @param key redis key
//     * @param <T> type of value
//     * @return 包含所有值的哈希表，当 key 不存在时，返回空表，异常时返回 null 。
//     */
//    public static <T> Optional<List<T>> hVals(String key) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RMap<String, T> map = cli.getMap(key);
//            return Optional.of(new ArrayList<>(map.readAllValues()));
//        } catch (Exception e) {
//            log.error(String.format("HVALS %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Hmget 命令用于返回哈希表中，一个或多个给定字段的值。
//     *
//     * @param key    redis key
//     * @param fields map keys
//     * @param <T>    type of value
//     * @return 包含指定键值的哈希表，当 key 不存在时，返回空表，异常时返回 null 。
//     */
//    public static <T> Optional<Map<String, T>> hMGet(String key, Set<String> fields) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RMap<String, T> map = cli.getMap(key);
//            return Optional.of(map.getAll(fields));
//        } catch (Exception e) {
//            log.error(String.format("HMGET %s %s >> %s", key, fields.toString(), exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Hmset 命令用于同时将多个 field-value (字段-值)对设置到哈希表中。
//     * <p>
//     * 此命令会覆盖哈希表中已存在的字段。
//     * <p>
//     * 如果哈希表不存在，会创建一个空哈希表，并执行 HMSET 操作。
//     *
//     * @param key         redis key
//     * @param fieldValues map
//     * @param <T>         type of value
//     * @return 操作成功时返回 true ，异常时返回 null 。
//     */
//    public static <T> boolean hMSet(String key, Map<String, T> fieldValues) {
//        if (!isEnable()) {
//            return false;
//        }
//        try {
//            RMap<String, T> map = cli.getMap(key);
//            map.putAll(fieldValues);
//            return true;
//        } catch (Exception e) {
//            log.error(String.format("HMSET %s size(%d) >> %s", key, fieldValues.size(), exceptionStack(e)));
//            onException(e, key);
//            return false;
//        }
//    }
//
//    /**
//     * Redis Hmset 命令用于同时将多个 field-value (字段-值)对设置到哈希表中。
//     * <p>
//     * 此命令会覆盖哈希表中已存在的字段。
//     * <p>
//     * 如果哈希表不存在，会创建一个空哈希表，并执行 HMSET 操作。
//     * <p>
//     * 同时设置Key的生存时间，秒。
//     *
//     * @param key         redis key
//     * @param fieldValues map
//     * @param timeToLive  key的生存时间，秒。
//     * @param <T>         type of value
//     * @return 操作成功时返回 true ，异常时返回 null 。
//     */
//    public static <T> Optional<Boolean> hMSetExpire(String key, Map<String, T> fieldValues, long timeToLive) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RMap<String, T> map = cli.getMap(key);
//            map.putAll(fieldValues);
//            map.expireAsync(timeToLive, TimeUnit.SECONDS);
//            return Optional.of(true);
//        } catch (Exception e) {
//            log.error(String.format("HMSET AND EXPIRE %s size(%d) timeToLive(%d) >> %s", key, fieldValues.size(), timeToLive, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Hincrby 命令用于为哈希表中的字段值加上指定增量值。
//     * <p>
//     * 增量也可以为负数，相当于对指定字段进行减法操作。
//     * <p>
//     * 如果哈希表的 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
//     * <p>
//     * 如果指定的字段不存在，那么在执行命令前，字段的值被初始化为 0 。
//     * <p>
//     * 对一个储存字符串值的字段执行 HINCRBY 命令将造成一个错误。
//     * <p>
//     * 本操作的值被限制在 64 位(bit)有符号数字表示之内。
//     *
//     * @param key   redis key
//     * @param field map key
//     * @param delta the value to add
//     * @return 执行 HINCRBY 后的值，异常时返回空。
//     */
//    public static OptionalLong hIncrBy(String key, String field, long delta) {
//        if (!isEnable()) {
//            return OptionalLong.empty();
//        }
//        try {
//            RMap<String, Long> map = cli.getMap(key);
//            return OptionalLong.of(map.addAndGet(field, delta));
//        } catch (Exception e) {
//            log.error(String.format("HINCRBY %s %s %d >> %s", key, field, delta, exceptionStack(e)));
//            onException(e, key);
//            return OptionalLong.empty();
//        }
//    }
//
//    /**
//     * Redis Hkeys 命令用于获取哈希表中的所有字段名。
//     *
//     * @param key redis key
//     * @return 包含哈希表中所有字段的列表，当 key 不存在时返回空列表，异常时返回 null 。
//     */
//    public static Set<String> hKeys(String key) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RMap<String, Object> map = cli.getMap(key);
//            return map.readAllKeySet();
//        } catch (Exception e) {
//            log.error(String.format("HKEYS %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Hlen 命令用于获取哈希表中字段的数量。
//     *
//     * @param key redis key
//     * @return 哈希表中字段的数量。当 key 不存在时返回 0 ，异常时返回空。
//     */
//    public static OptionalInt hLen(String key) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RMap<String, Object> map = cli.getMap(key);
//            return OptionalInt.of(map.size());
//        } catch (Exception e) {
//            log.error(String.format("HLEN %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return OptionalInt.empty();
//        }
//    }
//
//    // Redis 列表(List) 命令
//
////    /**
////     * Redis Blpop 命令移出并获取列表的第一个元素，如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
////     *
////     * @param key
////     * @param timeout 等待时间
////     * @param unit    timeout 的时间单位
////     * @param <T>
////     * @return 如果列表为空，返回一个 null 。
////     * @throws Exception 可能是InterruptedException或其它异常
////     */
////    public static <T> T blpop(String key, long timeout, TimeUnit unit) throws Exception {
////        RBlockingDeque<T> queue = cli.getBlockingDeque(key);
////        return queue.poll(timeout, unit);
////    }
////
////    /**
////     * Redis Brpop 命令移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
////     *
////     * @param key
////     * @param timeout 等待时间
////     * @param unit    timeout 的时间单位
////     * @param <T>
////     * @return 如果列表为空，返回一个 null 。
////     * @throws Exception 可能是InterruptedException或其它异常
////     */
////    public static <T> T brpop(String key, long timeout, TimeUnit unit) throws Exception {
////        RBlockingDeque<T> queue = cli.getBlockingDeque(key);
////        return queue.pollLast(timeout, unit);
////    }
//
//    /**
//     * Redis Lindex 命令用于通过索引获取列表中的元素。
//     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
//     *
//     * @param key   redis key
//     * @param index index
//     * @param <T>   type of value
//     * @return 列表中下标为指定索引值的元素，指定索引值不在列表的区间范围内时返回 null ，异常时返回 null 。
//     */
//    public static <T> T lIndex(String key, int index) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RList<T> list = cli.getList(key);
//            return list.get(index);
//        } catch (Exception e) {
//            log.error(String.format("LINDEX %s %d >> %s", key, index, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Linsert 命令用于在列表的元素前或者后插入元素。
//     * 当指定元素不存在于列表中时，不执行任何操作。当列表不存在时，被视为空列表，不执行任何操作。
//     * 如果 key 不是列表类型，返回一个错误。
//     *
//     * @param key      redis key
//     * @param existing 查找的元素
//     * @param newValue 插入的元素
//     * @param before   true 在元素前插入，false 在元素后插入
//     * @param <T>      type of value
//     * @return 如果命令执行成功，返回插入操作完成之后，列表的长度。
//     * 如果没有找到指定元素 ，返回 -1 。如果 key 不存在或为空列表，返回 0 。异常时返回 null 。
//     */
//    public static <T> OptionalInt lInsert(String key, T existing, T newValue, boolean before) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RList<T> list = cli.getList(key);
//            if (before) {
//                return OptionalInt.of(list.addBefore(existing, newValue));
//            } else {
//                return OptionalInt.of(list.addBefore(existing, newValue));
//            }
//        } catch (Exception e) {
//            log.error(String.format("LINSERT %s %s %s %b >> %s", key, existing, newValue, before, exceptionStack(e)));
//            onException(e, key);
//            return OptionalInt.empty();
//        }
//    }
//
//    /**
//     * Redis Llen 命令用于返回列表的长度。
//     * 如果列表 key 不存在，则 key 被解释为一个空列表，返回 0 。如果 key 不是列表类型，返回一个错误。
//     *
//     * @param key redis key
//     * @return 列表的长度，异常时返回 null 。
//     */
//    public static OptionalInt lLen(String key) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RList<?> list = cli.getList(key);
//            return OptionalInt.of(list.size());
//        } catch (Exception e) {
//            log.error(String.format("LLEN %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return OptionalInt.empty();
//        }
//    }
//
//    /**
//     * Redis Lpop 命令用于移除并返回列表的第一个元素。
//     *
//     * @param key redis key
//     * @param <T> type of value
//     * @return 列表的第一个元素。当列表 key 不存在时返回 null 。异常时返回 null 。
//     */
//    public static <T> T lPop(String key) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RQueue<T> queue = cli.getQueue(key);
//            return queue.poll();
//        } catch (Exception e) {
//            log.error(String.format("LPOP %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Lpush 命令将一个或多个值插入到列表头部。
//     * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。当 key 存在但不是列表类型时，返回一个错误。
//     *
//     * @param key   redis key
//     * @param value to push
//     * @param <T>   type of value
//     * @return 操作成功时返回 true ，异常时返回 false 。
//     */
//    public static <T> boolean lPush(String key, T value) {
//        if (!isEnable()) {
//            return false;
//        }
//        try {
//            RDeque<T> deque = cli.getDeque(key);
//            deque.push(value);
//            return true;
//        } catch (Exception e) {
//            log.error(String.format("LPUSH %s %s >> %s", key, value, exceptionStack(e)));
//            onException(e, key);
//            return false;
//        }
//    }
//
//    // lrange 暂不支持
//
//    /**
//     * Redis Lset 通过索引来设置元素的值。
//     * <p>
//     * 当索引参数超出范围，或对一个空列表进行 LSET 时，返回一个错误。
//     *
//     * @param key   redis key
//     * @param index index of object
//     * @param value to set
//     * @param <T>   type of value
//     * @return 操作成功时返回 true ，异常时返回 false 。
//     */
//    public static <T> boolean lSet(String key, int index, T value) {
//        if (!isEnable()) {
//            return false;
//        }
//        try {
//            RList<T> list = cli.getList(key);
//            list.fastSet(index, value);
//            return true;
//        } catch (Exception e) {
//            log.error(String.format("LSET %s %d %s >> %s", key, index, value, exceptionStack(e)));
//            onException(e, key);
//            return false;
//        }
//    }
//
//    /**
//     * Redis Ltrim 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
//     * <p>
//     * 下标 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
//     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
//     *
//     * @param key  redis key
//     * @param from from index
//     * @param to   to index
//     * @return 操作成功时返回 true ，异常时返回 false 。
//     */
//    public static boolean lTrim(String key, int from, int to) {
//        if (!isEnable()) {
//            return false;
//        }
//        try {
//            RList<?> list = cli.getList(key);
//            list.trim(from, to);
//            return true;
//        } catch (Exception e) {
//            log.error(String.format("LTRIM %s %d %d >> %s", key, from, to, exceptionStack(e)));
//            onException(e, key);
//            return false;
//        }
//    }
//
//    /**
//     * Redis Rpop 命令用于移除并返回列表的最后一个元素。
//     *
//     * @param key redis key
//     * @param <T> type of value
//     * @return 列表的最后一个元素。当列表不存在时，返回 null 。异常时返回 null 。
//     */
//    public static <T> T rPop(String key) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RDeque<T> deque = cli.getDeque(key);
//            return deque.pollLast();
//        } catch (Exception e) {
//            log.error(String.format("RPOP %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Rpush 命令用于将一个或多个值插入到列表的尾部(最右边)。
//     * <p>
//     * 如果列表不存在，一个空列表会被创建并执行 RPUSH 操作。当列表存在但不是列表类型时，返回一个错误。
//     *
//     * @param key   redis key
//     * @param value value to append
//     * @param <T>   type of value
//     * @return 操作成功时返回 true ，异常时返回 null 。
//     */
//    public static <T> Optional<Boolean> rPush(String key, T value) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RList<T> list = cli.getList(key);
//            return Optional.of(list.add(value));
//        } catch (Exception e) {
//            log.error(String.format("RPUSH %s %s >> %s", key, value, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    // Redis 集合(Set) 命令
//
//    /**
//     * Redis Sadd 命令将一个或多个成员元素加入到集合中，已经存在于集合的成员元素将被忽略。
//     * <p>
//     * 假如集合 key 不存在，则创建一个只包含添加的元素作成员的集合。
//     * <p>
//     * 当集合 key 不是集合类型时，返回一个错误。
//     *
//     * @param key    redis key
//     * @param member member to add
//     * @param <T>    type of member
//     * @return 操作成功时返回 true ，异常时返回 null 。
//     */
//    public static <T> Optional<Boolean> sAdd(String key, T member) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RSet<T> set = cli.getSet(key);
//            return Optional.of(set.add(member));
//        } catch (Exception e) {
//            log.error(String.format("SADD %s %s >> %s", key, member, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Scard 命令返回集合中元素的数量。
//     *
//     * @param key redis key
//     * @return 集合的数量，集合 key 不存在时返回 0 ，异常时返回 null 。
//     */
//    public static OptionalInt sCard(String key) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RSet<?> set = cli.getSet(key);
//            return OptionalInt.of(set.size());
//        } catch (Exception e) {
//            log.error(String.format("SCARD %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return OptionalInt.empty();
//        }
//    }
//
//    /**
//     * Redis Sdiffstore 命令将给定集合之间的差集存储在指定的集合中。如果指定的集合 key 已存在，则会被覆盖。
//     *
//     * @param key  redis key
//     * @param sets name of sets
//     * @return 结果集中的元素数量，异常时返回 null 。
//     */
//    public static OptionalInt sDiffStore(String key, String... sets) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RSet<?> set = cli.getSet(key);
//            return OptionalInt.of(set.diff(sets));
//        } catch (Exception e) {
//            log.error(String.format("SDIFFSTORE %s %s >> %s", key, Arrays.toString(sets), exceptionStack(e)));
//            onException(e, key);
//            return OptionalInt.empty();
//        }
//    }
//
//    /**
//     * Redis Sinterstore 命令将给定集合之间的交集存储在指定的集合中。如果指定的集合已经存在，则将其覆盖。
//     *
//     * @param key  redis key
//     * @param sets name of sets
//     * @return 交集成员的列表，异常时返回 null 。
//     */
//    public static OptionalInt sInterStore(String key, String... sets) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RSet<?> set = cli.getSet(key);
//            return OptionalInt.of(set.intersection(sets));
//        } catch (Exception e) {
//            log.error(String.format("SINTERSTORE %s %s >> %s", key, Arrays.toString(sets), exceptionStack(e)));
//            onException(e, key);
//            return OptionalInt.empty();
//        }
//    }
//
//    /**
//     * Redis Sismember 命令判断成员元素是否是集合的成员。
//     *
//     * @param key redis key
//     * @param obj object
//     * @return 成员元素是集合的成员时返回 true 。成员元素不是集合的成员或 key 不存在时返回 false ，异常时返回 null 。
//     */
//    public static Optional<Boolean> sIsMember(String key, Object obj) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RSet<?> set = cli.getSet(key);
//            return Optional.of(set.contains(obj));
//        } catch (Exception e) {
//            log.error(String.format("SISMEMBER %s %s >> %s", key, obj, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Smembers 命令返回集合中的所有的成员。不存在的集合 key 被视为空集合。
//     *
//     * @param key redis key
//     * @param <T> type of value
//     * @return 包含所有成员的集合，异常时返回 null 。
//     */
//    public static <T> Set<T> sMembers(String key) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RSet<T> set = cli.getSet(key);
//            return set.readAll();
//        } catch (Exception e) {
//            log.error(String.format("SMEMBERS %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Smove 命令将指定成员 member 元素从 source 集合移动到 destination 集合。
//     * <p>
//     * SMOVE 是原子性操作。
//     * <p>
//     * 如果 source 集合不存在或不包含指定的 member 元素，则 SMOVE 命令不执行任何操作，仅返回 0 。
//     * 否则， member 元素从 source 集合中被移除，并添加到 destination 集合中去。
//     * <p>
//     * 当 destination 集合已经包含 member 元素时， SMOVE 命令只是简单地将 source 集合中的 member 元素删除。
//     * <p>
//     * 当 source 或 destination 不是集合类型时，返回一个错误。
//     *
//     * @param key         redis key
//     * @param destination the destination set
//     * @param member      member to move
//     * @param <T>         type of member
//     * @return 如果成员元素被成功移除，返回 true 。
//     * 如果成员元素不是 source 集合的成员，并且没有任何操作对 destination 集合执行，那么返回 false 。异常时返回 null 。
//     */
//    public static <T> Optional<Boolean> sMove(String key, String destination, T member) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RSet<T> set = cli.getSet(key);
//            return Optional.of(set.move(destination, member));
//        } catch (Exception e) {
//            log.error(String.format("SMOVE %s %s %s >> %s", key, destination, member, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Spop 命令用于移除并返回集合中的一个随机元素。
//     *
//     * @param key redis key
//     * @param <T> type of value
//     * @return 被移除的随机元素，集合不存在或是空集时返回 null ，异常时返回 null 。
//     */
//    public static <T> T sPop(String key) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RSet<T> set = cli.getSet(key);
//            return set.removeRandom();
//        } catch (Exception e) {
//            log.error(String.format("SPOP %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Spop 命令用于移除并返回集合中的多个随机元素。
//     *
//     * @param key    redis key
//     * @param amount of random values
//     * @param <T>    type of value
//     * @return 被移除的随机元素集合，集合不存在或是空集时返回空，异常时返回 null 。
//     */
//    public static <T> Set<T> sPopN(String key, int amount) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RSet<T> set = cli.getSet(key);
//            return set.removeRandom(amount);
//        } catch (Exception e) {
//            log.error(String.format("SPOP %s %d >> %s", key, amount, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Srem 命令用于移除集合中的一个或多个成员元素，不存在的成员元素会被忽略。
//     * <p>
//     * 当 key 不是集合类型，返回一个错误。
//     *
//     * @param key redis key
//     * @param obj object to remove
//     * @return 成员存在并被成功移除时返回 true ，成员不存在时返回 false ，异常时返回 null 。
//     */
//    public static Optional<Boolean> sRem(String key, Object obj) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RSet<?> set = cli.getSet(key);
//            return Optional.of(set.remove(obj));
//        } catch (Exception e) {
//            log.error(String.format("SREM %s %s >> %s", key, obj, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    // Redis 有序集合(sorted set)命令
//
//    /**
//     * Redis Zadd 命令用于将一个或多个成员元素及其分数值加入到有序集当中。
//     * <p>
//     * 如果某个成员已经是有序集的成员，那么更新这个成员的分数值，并通过重新插入这个成员元素，来保证该成员在正确的位置上。
//     * <p>
//     * 分数值可以是整数值或双精度浮点数。
//     * <p>
//     * 如果有序集合 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
//     * <p>
//     * 当 key 存在但不是有序集类型时，返回一个错误。
//     *
//     * @param key    redis key
//     * @param score  score
//     * @param member member
//     * @param <T>    type of member
//     * @return 成员成功添加时返回 true ，否则返回 false ，异常时返回 null 。
//     */
//    public static <T> Optional<Boolean> zAdd(String key, double score, T member) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RScoredSortedSet<T> sortedSet = cli.getScoredSortedSet(key);
//            return Optional.of(sortedSet.add(score, member));
//        } catch (Exception e) {
//            log.error(String.format("ZADD %s %f %s >> %s", key, score, member, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Zcard 命令用于计算集合中元素的数量。
//     *
//     * @param key redis key
//     * @return 当 key 存在且是有序集类型时，返回有序集的基数。当 key 不存在时返回 0 。异常时返回 null 。
//     */
//    public static OptionalInt zCard(String key) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RScoredSortedSet<?> sortedSet = cli.getScoredSortedSet(key);
//            return OptionalInt.of(sortedSet.size());
//        } catch (Exception e) {
//            log.error(String.format("ZCARD %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return OptionalInt.empty();
//        }
//    }
//
//    /**
//     * Redis Zcount 命令用于计算有序集合中指定分数区间的成员数量。
//     *
//     * @param key        redis key
//     * @param startScore start score
//     * @param endScore   end score
//     * @return 分数值在 min 和 max 之间的成员的数量，异常时返回 null 。
//     */
//    public static OptionalInt zCount(String key, double startScore, double endScore) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RScoredSortedSet<?> sortedSet = cli.getScoredSortedSet(key);
//            return OptionalInt.of(sortedSet.count(startScore, true, endScore, true));
//        } catch (Exception e) {
//            log.error(String.format("ZCOUNT %s %f %f >> %s", key, startScore, endScore, exceptionStack(e)));
//            onException(e, key);
//            return OptionalInt.empty();
//        }
//    }
//
//    /**
//     * Redis Zincrby 命令对有序集合中指定成员的分数加上增量 increment
//     * <p>
//     * 可以通过传递一个负数值 increment ，让分数减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 值减去 5 。
//     * <p>
//     * 当 key 不存在，或分数不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key increment member 。
//     * <p>
//     * 当 key 不是有序集类型时，返回一个错误。
//     * <p>
//     * 分数值可以是整数值或双精度浮点数。
//     *
//     * @param key    redis key
//     * @param score  score
//     * @param member member whose score needs to be increased
//     * @param <T>    type of member
//     * @return 成员的新分数值，异常时返回 null 。
//     */
//    public static <T> OptionalDouble zIncrBy(String key, double score, T member) {
//        if (!isEnable()) {
//            return OptionalDouble.empty();
//        }
//        try {
//            RScoredSortedSet<T> sortedSet = cli.getScoredSortedSet(key);
//            return OptionalDouble.of(sortedSet.addScore(member, score));
//        } catch (Exception e) {
//            log.error(String.format("ZINCRBY %s %f %s >> %s", key, score, member, exceptionStack(e)));
//            onException(e, key);
//            return OptionalDouble.empty();
//        }
//    }
//
//    /**
//     * Redis Zrange 返回有序集中，指定区间内的成员。
//     * <p>
//     * 其中成员的位置按分数值递增(从小到大)来排序。
//     * <p>
//     * 具有相同分数值的成员按字典序(lexicographical order )来排列。
//     * <p>
//     * 如果你需要成员按
//     * <p>
//     * 值递减(从大到小)来排列，请使用 ZREVRANGE 命令。
//     * <p>
//     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
//     * <p>
//     * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
//     *
//     * @param key        redis key
//     * @param startIndex start index
//     * @param endIndex   end index
//     * @param <T>        type of value
//     * @return 指定区间内有序集成员的列表，异常时返回 null 。
//     */
//    public static <T> Collection<T> zRange(String key, int startIndex, int endIndex) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RScoredSortedSet<T> sortedSet = cli.getScoredSortedSet(key);
//            return sortedSet.valueRange(startIndex, endIndex);
//        } catch (Exception e) {
//            log.error(String.format("ZRANGE %s %d %d >> %s", key, startIndex, endIndex, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * 比 {@link #zRange(String, int, int)} 多了分数
//     *
//     * @param key        redis key
//     * @param startIndex start index
//     * @param endIndex   end index
//     * @param <T>        type of value
//     * @return 指定区间内带有分数值(可选)的有序集成员的列表，异常时返回 null 。
//     */
//    public static <T> Collection<ScoredEntry<T>> zRangeWithScores(String key, int startIndex, int endIndex) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RScoredSortedSet<T> sortedSet = cli.getScoredSortedSet(key);
//            return sortedSet.entryRange(startIndex, endIndex);
//        } catch (Exception e) {
//            log.error(String.format("ZRANGE WITHSCORES %s %d %d >> %s", key, startIndex, endIndex, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Zrangebyscore 返回有序集合中指定分数区间的成员列表。有序集成员按分数值递增(从小到大)次序排列。
//     * <p>
//     * 具有相同分数值的成员按字典序来排列(该属性是有序集提供的，不需要额外的计算)。
//     * <p>
//     * 默认情况下，区间的取值使用闭区间 (小于等于或大于等于)，你也可以通过给参数前增加 ( 符号来使用可选的开区间 (小于或大于)。
//     *
//     * @param key        redis key
//     * @param startScore start score
//     * @param endScore   end score
//     * @param <T>        type of value
//     * @return 指定区间内有序集成员的列表，异常时返回 null 。
//     */
//    public static <T> Collection<T> zRangeByScore(String key, double startScore, double endScore) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RScoredSortedSet<T> sortedSet = cli.getScoredSortedSet(key);
//            return sortedSet.valueRange(startScore, true, endScore, true);
//        } catch (Exception e) {
//            log.error(String.format("ZRANGEBYSCORE %s %f %f >> %s", key, startScore, endScore, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * 比 {@link #zRangeByScore(String, double, double)} 多了分数
//     *
//     * @param key        redis key
//     * @param startScore start score
//     * @param endScore   end score
//     * @param <T>        type of value
//     * @return 指定区间内带有分数值(可选)的有序集成员的列表，异常时返回 null 。
//     */
//    public static <T> Collection<ScoredEntry<T>> zRangeByScoreWithScores(String key, double startScore, double endScore) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RScoredSortedSet<T> sortedSet = cli.getScoredSortedSet(key);
//            return sortedSet.entryRange(startScore, true, endScore, true);
//        } catch (Exception e) {
//            log.error(String.format("ZRANGEBYSCORE WITHSCORES %s %f %f >> %s", key, startScore, endScore, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Zrank 返回有序集中指定成员的排名。其中有序集成员按分数值递增(从小到大)顺序排列。
//     *
//     * @param key   redis key
//     * @param value value
//     * @param <T>   type of value
//     * @return 如果成员是有序集 key 的成员，返回 member 的排名。如果成员不是有序集 key 的成员，返回 null 。异常时返回 null 。
//     */
//    public static <T> OptionalInt zRank(String key, T value) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RScoredSortedSet<T> sortedSet = cli.getScoredSortedSet(key);
//            return OptionalInt.of(sortedSet.rank(value));
//        } catch (Exception e) {
//            log.error(String.format("ZRANK %s %s >> %s", key, value, exceptionStack(e)));
//            onException(e, key);
//            return OptionalInt.empty();
//        }
//    }
//
//    /**
//     * Redis Zrem 命令用于移除有序集中的一个成员，不存在的成员将被忽略。
//     * <p>
//     * 当 key 存在但不是有序集类型时，返回一个错误。
//     *
//     * @param key redis key
//     * @param obj object to remove
//     * @return 删除成功返回 true ，否则返回 false ，异常时返回 null 。
//     */
//    public static Optional<Boolean> zRem(String key, Object obj) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RScoredSortedSet<?> sortedSet = cli.getScoredSortedSet(key);
//            return Optional.of(sortedSet.remove(obj));
//        } catch (Exception e) {
//            log.error(String.format("ZREM %s %s >> %s", key, obj, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Zrem 命令用于移除有序集中的一个或多个成员，不存在的成员将被忽略。
//     * <p>
//     * 当 key 存在但不是有序集类型时，返回一个错误。
//     *
//     * @param key redis key
//     * @param c   elements to remove
//     * @return 删除成功返回 true ，在没有成员被删除时返回 false ，异常时返回 null 。
//     */
//    public static Optional<Boolean> zRem(String key, Collection<Object> c) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RScoredSortedSet<?> sortedSet = cli.getScoredSortedSet(key);
//            return Optional.of(sortedSet.removeAll(c));
//        } catch (Exception e) {
//            if (c.size() <= 10) {
//                log.error(String.format("ZREM %s %s >> %s", key, c, exceptionStack(e)));
//            } else {
//                log.error(String.format("ZREM %s size(%d) >> %s", key, c.size(), exceptionStack(e)));
//            }
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Zremrangebyrank 命令用于移除有序集中，指定排名(rank)区间内的所有成员。
//     *
//     * @param key        redis key
//     * @param startIndex start index
//     * @param endIndex   end index
//     * @return 被移除成员的数量，异常时返回 null 。
//     */
//    public static OptionalInt zRemRangeByRank(String key, int startIndex, int endIndex) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RScoredSortedSet<?> sortedSet = cli.getScoredSortedSet(key);
//            return OptionalInt.of(sortedSet.removeRangeByRank(startIndex, endIndex));
//        } catch (Exception e) {
//            log.error(String.format("ZREMRANGEBYRANK %s %d %d >> %s", key, startIndex, endIndex, exceptionStack(e)));
//            onException(e, key);
//            return OptionalInt.empty();
//        }
//    }
//
//    /**
//     * Redis Zremrangebyscore 命令用于移除有序集中，指定分数（score）区间内的所有成员。
//     *
//     * @param key        redis key
//     * @param startScore start score
//     * @param endScore   end score
//     * @return 被移除成员的数量，异常时返回 null 。
//     */
//    public static OptionalInt zRemRangeByScore(String key, double startScore, double endScore) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RScoredSortedSet<?> sortedSet = cli.getScoredSortedSet(key);
//            return OptionalInt.of(sortedSet.removeRangeByScore(startScore, true, endScore, true));
//        } catch (Exception e) {
//            log.error(String.format("ZREMRANGEBYSCORE %s %f %f >> %s", key, startScore, endScore, exceptionStack(e)));
//            onException(e, key);
//            return OptionalInt.empty();
//        }
//    }
//
//    /**
//     * Redis Zrevrange 命令返回有序集中，指定区间内的成员。
//     * <p>
//     * 其中成员的位置按分数值递减(从大到小)来排列。
//     * <p>
//     * 具有相同分数值的成员按字典序的逆序(reverse lexicographical order)排列。
//     * <p>
//     * 除了成员按分数值递减的次序排列这一点外， ZREVRANGE 命令的其他方面和 ZRANGE 命令一样。
//     *
//     * @param key        redis key
//     * @param startIndex start index
//     * @param endIndex   end index
//     * @param <T>        type of value
//     * @return 指定区间内有序集成员的列表，异常时返回 null 。
//     */
//    public static <T> Collection<T> zRevRange(String key, int startIndex, int endIndex) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RScoredSortedSet<T> sortedSet = cli.getScoredSortedSet(key);
//            return sortedSet.valueRangeReversed(startIndex, endIndex);
//        } catch (Exception e) {
//            log.error(String.format("ZREVRANGE %s %d %d >> %s", key, startIndex, endIndex, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * 比 {@link #zRevRange(String, int, int)} 多了分数
//     *
//     * @param key        redis key
//     * @param startIndex start index
//     * @param endIndex   end index
//     * @param <T>        type of value
//     * @return 指定区间内带有分数值(可选)的有序集成员的列表，异常时返回 null 。
//     */
//    public static <T> Collection<ScoredEntry<T>> zRevRangeWithScores(String key, int startIndex, int endIndex) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RScoredSortedSet<T> sortedSet = cli.getScoredSortedSet(key);
//            return sortedSet.entryRangeReversed(startIndex, endIndex);
//        } catch (Exception e) {
//            log.error(String.format("ZREVRANGE WITHSCORES %s %d %d >> %s", key, startIndex, endIndex, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Zrevrangebyscore 返回有序集中指定分数区间内的所有的成员。有序集成员按分数值递减(从大到小)的次序排列。
//     * <p>
//     * 具有相同分数值的成员按字典序的逆序(reverse lexicographical order )排列。
//     * <p>
//     * 除了成员按分数值递减的次序排列这一点外， ZREVRANGEBYSCORE 命令的其他方面和 ZRANGEBYSCORE 命令一样。
//     *
//     * @param key        redis key
//     * @param startScore start score
//     * @param endScore   end score
//     * @param <T>        type of value
//     * @return 指定区间内有序集成员的列表，异常时返回 null 。
//     */
//    public static <T> Collection<T> zRevRangeByScore(String key, double startScore, double endScore) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RScoredSortedSet<T> sortedSet = cli.getScoredSortedSet(key);
//            return sortedSet.valueRangeReversed(startScore, true, endScore, true);
//        } catch (Exception e) {
//            log.error(String.format("ZREVRANGEBYSCORE %s %f %f >> %s", key, startScore, endScore, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * 比 {@link #zRevRangeByScore(String, double, double)} 多了分数
//     *
//     * @param key        redis key
//     * @param startScore start score
//     * @param endScore   end score
//     * @param <T>        type of value
//     * @return 指定区间内带有分数值(可选)的有序集成员的列表，异常时返回 null 。
//     */
//    public static <T> Collection<ScoredEntry<T>> zRevRangeByScoreWithScores(String key, double startScore, double endScore) {
//        if (!isEnable()) {
//            return null;
//        }
//        try {
//            RScoredSortedSet<T> sortedSet = cli.getScoredSortedSet(key);
//            return sortedSet.entryRangeReversed(startScore, true, endScore, true);
//        } catch (Exception e) {
//            log.error(String.format("ZREVRANGEBYSCORE WITHSCORES %s %f %f >> %s", key, startScore, endScore, exceptionStack(e)));
//            onException(e, key);
//            return null;
//        }
//    }
//
//    /**
//     * Redis Zrevrank 命令返回有序集中成员的排名。其中有序集成员按分数值递减(从大到小)排序。
//     * <p>
//     * 排名以 0 为底，也就是说， 分数值最大的成员排名为 0 。
//     * <p>
//     * 使用 ZRANK 命令可以获得成员按分数值递增(从小到大)排列的排名。
//     *
//     * @param key   redis key
//     * @param value element
//     * @param <T>   type of element
//     * @return 如果成员是有序集 key 的成员，返回成员的排名。如果成员不是有序集 key 的成员，返回 null 。异常时返回 null 。
//     */
//    public static <T> OptionalInt zRevRank(String key, T value) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RScoredSortedSet<T> sortedSet = cli.getScoredSortedSet(key);
//            return OptionalInt.of(sortedSet.revRank(value));
//        } catch (Exception e) {
//            log.error(String.format("ZREVRANK %s %s >> %s", key, value, exceptionStack(e)));
//            onException(e, key);
//            return OptionalInt.empty();
//        }
//    }
//
//    /**
//     * Redis Zscore 命令返回有序集中，成员的分数值。如果成员元素不是有序集 key 的成员，或 key 不存在，返回 null 。
//     *
//     * @param key   redis key
//     * @param value element
//     * @param <T>   type of element
//     * @return 成员的分数值，成员不存在或异常时返回 null 。
//     */
//    public static <T> OptionalDouble zScore(String key, T value) {
//        if (!isEnable()) {
//            return OptionalDouble.empty();
//        }
//        try {
//            RScoredSortedSet<T> sortedSet = cli.getScoredSortedSet(key);
//            return OptionalDouble.of(sortedSet.getScore(value));
//        } catch (Exception e) {
//            log.error(String.format("ZSCORE %s %s >> %s", key, value, exceptionStack(e)));
//            onException(e, key);
//            return OptionalDouble.empty();
//        }
//    }
//
//    // Redis HyperLogLog 命令
//
//    /**
//     * Redis Pfadd 命令将元素参数添加到 HyperLogLog 数据结构中。
//     *
//     * @param key   redis key
//     * @param value element to add
//     * @param <T>   type of element
//     * @return 元素被添加返回 true ，否则返回 false ，异常时返回 null 。
//     */
//    public static <T> Optional<Boolean> pfAdd(String key, T value) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RHyperLogLog<T> log = cli.getHyperLogLog(key);
//            return Optional.of(log.add(value));
//        } catch (Exception e) {
//            log.error(String.format("PFADD %s %s >> %s", key, value, exceptionStack(e)));
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Pfadd 命令将所有元素参数添加到 HyperLogLog 数据结构中。
//     *
//     * @param key redis key
//     * @param c   elements to add
//     * @param <T> type of element
//     * @return 如果至少有个元素被添加返回 true， 如果没有元素被添加返回 false。
//     */
//    public static <T> Optional<Boolean> pfAdd(String key, Collection<T> c) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RHyperLogLog<T> log = cli.getHyperLogLog(key);
//            return Optional.of(log.addAll(c));
//        } catch (Exception e) {
//            if (c.size() <= 10) {
//                log.error(String.format("PFADD %s %s >> %s", key, c, exceptionStack(e)));
//            } else {
//                log.error(String.format("PFADD %s size(%d) >> %s", key, c.size(), exceptionStack(e)));
//            }
//            onException(e, key);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Redis Pfcount 命令返回给定 HyperLogLog 的基数估算值。
//     *
//     * @param key redis key
//     * @return 返回给定 HyperLogLog 的基数值，异常时返回 null 。
//     */
//    public static OptionalLong pfCount(String key) {
//        if (!isEnable()) {
//            return OptionalLong.empty();
//        }
//        try {
//            RHyperLogLog<?> log = cli.getHyperLogLog(key);
//            return OptionalLong.of(log.count());
//        } catch (Exception e) {
//            log.error(String.format("PFCOUNT %s >> %s", key, exceptionStack(e)));
//            onException(e, key);
//            return OptionalLong.empty();
//        }
//    }
//
//    /**
//     * Redis Pfcount 命令返回给定 HyperLogLog 的基数估算值。
//     *
//     * @param key           redis key
//     * @param otherLogNames name of object
//     * @return 返回给定 HyperLogLog 的基数值，如果多个 HyperLogLog 则返回基数估值之和，异常时返回 null 。
//     */
//    public static OptionalLong pfCount(String key, String... otherLogNames) {
//        if (!isEnable()) {
//            return OptionalLong.empty();
//        }
//        try {
//            RHyperLogLog<?> log = cli.getHyperLogLog(key);
//            return OptionalLong.of(log.countWith(otherLogNames));
//        } catch (Exception e) {
//            log.error(String.format("PFCOUNT %s %s >> %s", key, Arrays.toString(otherLogNames), exceptionStack(e)));
//            onException(e, key);
//            return OptionalLong.empty();
//        }
//    }
//
//    /**
//     * Redis Pgmerge 命令将多个 HyperLogLog 合并为一个 HyperLogLog ，
//     * 合并后的 HyperLogLog 的基数估算值是通过对所有 给定 HyperLogLog 进行并集计算得出的。
//     *
//     * @param key           redis key
//     * @param otherLogNames name of object
//     * @return 操作成功时返回 true ，异常时返回 false 。
//     */
//    public static boolean pgMerge(String key, String... otherLogNames) {
//        if (!isEnable()) {
//            return false;
//        }
//        try {
//            RHyperLogLog<?> log = cli.getHyperLogLog(key);
//            log.mergeWith(otherLogNames);
//            return true;
//        } catch (Exception e) {
//            log.error(String.format("PFMERGE %s %s >> %s", key, Arrays.toString(otherLogNames), exceptionStack(e)));
//            onException(e, key);
//            return false;
//        }
//    }
//
//    // Redis 发布订阅 命令
//
//    /**
//     * Redis Psubscribe 命令订阅一个或多个符合给定模式的频道。
//     * <p>
//     * 每个模式以 * 作为匹配符，比如 it* 匹配所有以 it 开头的频道( it.news 、 it.blog 、 it.tweets 等等)。
//     * news.* 匹配所有以 news. 开头的频道( news.it 、 news.global.today 等等)，诸如此类。
//     *
//     * @param pattern of the topic
//     * @param type    type of message
//     * @param lis     message listener
//     * @param <T>     type of message
//     * @return Listener的ID，本地JVM唯一，异常时返回 null 。
//     */
//    public static <T> OptionalInt pSubscribe(String pattern, Class<T> type, PatternMessageListener<T> lis) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RPatternTopic topic = cli.getPatternTopic(pattern);
//            return OptionalInt.of(topic.addListener(type, lis));
//        } catch (Exception e) {
//            log.error(String.format("PSUBSCRIBE %s %s %s >> %s", pattern, type, lis, exceptionStack(e)));
//            onException(e, null);
//            return OptionalInt.empty();
//        }
//    }
//
//    /**
//     * Redis Publish 命令用于将信息发送到指定的频道。
//     *
//     * @param name of channel
//     * @param msg  to send
//     * @return 接收到信息的订阅者数量，异常时返回 null 。
//     */
//    public static OptionalLong publish(String name, Object msg) {
//        if (!isEnable()) {
//            return OptionalLong.empty();
//        }
//        try {
//            RTopic topic = cli.getTopic(name);
//            return OptionalLong.of(topic.publish(msg));
//        } catch (Exception e) {
//            log.error(String.format("PUBLISH %s %s >> %s", name, msg, exceptionStack(e)));
//            onException(e, null);
//            return OptionalLong.empty();
//        }
//    }
//
//    /**
//     * Redis Punsubscribe 命令用于退订所有给定模式的频道。
//     *
//     * @param pattern of the topic
//     * @param lisId   id of message listener
//     * @return 操作成功时返回 true ，异常时返回 null 。
//     */
//    public static boolean pUnsubscribe(String pattern, int lisId) {
//        if (!isEnable()) {
//            return false;
//        }
//        try {
//            RPatternTopic topic = cli.getPatternTopic(pattern);
//            topic.removeListener(lisId);
//            return true;
//        } catch (Exception e) {
//            log.error(String.format("PUNSUBSCRIBE %s %d >> %s", pattern, lisId, exceptionStack(e)));
//            onException(e, null);
//            return false;
//        }
//    }
//
//    /**
//     * Redis Subscribe 命令用于订阅给定的一个或多个频道的信息。
//     *
//     * @param name name of object
//     * @param type type of message
//     * @param lis  listener for messages
//     * @param <T>  type of message
//     * @return Listener的ID，本地JVM唯一，异常时返回 null 。
//     */
//    public static <T> OptionalInt subscribe(String name, Class<T> type, MessageListener<? extends T> lis) {
//        if (!isEnable()) {
//            return OptionalInt.empty();
//        }
//        try {
//            RTopic topic = cli.getTopic(name);
//            return OptionalInt.of(topic.addListener(type, lis));
//        } catch (Exception e) {
//            log.error(String.format("SUBSCRIBE %s %s %s >> %s", name, type, lis, exceptionStack(e)));
//            onException(e, null);
//            return OptionalInt.empty();
//        }
//    }
//
//    /**
//     * Redis Unsubscribe 命令用于退订给定的一个或多个频道的信息。
//     *
//     * @param name   name of object
//     * @param lisIds listener ids
//     * @return 操作成功时返回 true ，异常时返回 false 。
//     */
//    public static boolean unsubscribe(String name, Integer... lisIds) {
//        if (!isEnable()) {
//            return false;
//        }
//        try {
//            RTopic topic = cli.getTopic(name);
//            topic.removeListener(lisIds);
//            return true;
//        } catch (Exception e) {
//            log.error(String.format("UNSUBSCRIBE %s %s >> %s", name, Arrays.toString(lisIds), exceptionStack(e)));
//            onException(e, null);
//            return false;
//        }
//    }
//
//    // EVAL
//
//    /**
//     * Executes Lua script
//     *
//     * @param <T>        type of result
//     * @param codec      codec for params and result, null to ConnectionManager().getCodec()
//     * @param mode       execution mode
//     * @param luaScript  lua script
//     * @param returnType return type
//     * @param keys       keys available through KEYS param in script
//     * @param values     values available through VALUES param in script
//     * @return Optional，结果类型与returnType有关，在Redis功能关闭或异常时返回null
//     */
//    public static <T> Optional<T> eval(Codec codec, RScript.Mode mode, String luaScript, RScript.ReturnType returnType, List<Object> keys, Object... values) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RScript rScript;
//            if (codec == null) {
//                rScript = cli.getScript();
//            } else {
//                rScript = cli.getScript(codec);
//            }
//            return Optional.ofNullable(rScript.eval(mode, luaScript, returnType, keys, values));
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(String.format("EVAL \"%s\" KEYS:%s ARGV:%s >> %s", luaScript, keys.toString(), Arrays.toString(values), exceptionStack(e)));
//            onException(e, null);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Executes Lua script
//     *
//     * @param script     RedisScript
//     * @param codec      codec for params and result, null to ConnectionManager().getCodec()
//     * @param mode       execution mode
//     * @param returnType return type
//     * @param keys       keys available through KEYS param in script
//     * @param values     values available through VALUES param in script
//     * @param <T>        type of result
//     * @return Optional，结果类型与returnType有关，在Redis功能关闭或异常时返回null
//     */
//    public static <T> Optional<T> eval(RedisScript script, Codec codec, RScript.Mode mode, RScript.ReturnType returnType, List<Object> keys, Object... values) {
//        return eval(codec, mode, script.script, returnType, keys, values);
//    }
//
//    /**
//     * Executes Lua script
//     *
//     * @param <T>        type of result
//     * @param mode       execution mode
//     * @param shaDigest  SHA-1 digest
//     * @param returnType return type
//     * @param keys       keys available through KEYS param in script
//     * @param values     values available through VALUES param in script
//     * @return result object
//     */
//    public static <T> Optional<T> evalSha(RScript.Mode mode, String shaDigest, RScript.ReturnType returnType, List<Object> keys, Object... values) {
//        if (!isEnable()) {
//            return Optional.empty();
//        }
//        try {
//            RScript rScript = cli.getScript();
//            return Optional.ofNullable(rScript.evalSha(mode, shaDigest, returnType, keys, values));
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(String.format("EVALSHA \"%s\" KEYS:%s ARGV:%s >> %s", shaDigest, keys.toString(), Arrays.toString(values), exceptionStack(e)));
//            onException(e, null);
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * 清空整个 Redis 服务器的数据(删除所有数据库的所有 key )。
//     * 如果Redis将FLUSHALL命令重命名，此操作将执行失败
//     */
//    public static void flushAll() {
//        RedissonClient cli = RedissonUtil.getRedisson();
//        cli.getKeys().flushall();
//    }
//
//    /**
//     * @param e the exception
//     * @return Exception messages
//     */
//    private static String exceptionStack(Exception e) {
//        StringBuilder buf = new StringBuilder(1024);
//        buf.append(e.toString());
//        String lineSep = System.lineSeparator();
//        buf.append(lineSep);
//
//        StackTraceElement[] trace = e.getStackTrace();
//        int i = 0;
//        for (StackTraceElement traceElement : trace) {
//            buf.append("\tat ").append(traceElement).append(lineSep);
//            if (++i >= 10) {
//                break;
//            }
//        }
//        return buf.toString();
//    }
//
//    /**
//     * @param e
//     * @param key Redis - key
//     */
//    private static void onException(Exception e, String key) {
//        if (!enable.get()) {
//            return; // Redis已关闭
//        }
//        if (!Boolean.valueOf(GameConfig.stringVal("RedisCloseOnException"))) {
//            return; // 此功能未开启
//        }
//
//        // 匹配异常
//        String filters = GameConfig.stringVal("RedisCloseExceptionFilter");
//        if (StringUtils.isBlank(filters)) {
//            return; // 空不检查
//        }
//
//        filters = filters.trim();
//        boolean match = "*".equals(filters);
//        if (!match) {
//            String[] arr = filters.split("\\|");
//            for (String clazz : arr) {
//                try {
//                    Class<?> cla = Class.forName(clazz);
//                    if (cla.isAssignableFrom(e.getClass())) {
//                        match = true;
//                        break;
//                    }
//                } catch (ClassNotFoundException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//        if (!match) {
//            return; // 匹配失败
//        }
//
//        synchronized (enable) {
//            if (enable.get()) {
//                enable.set(false); // 关闭Redis功能，将在Redis恢复访问时开启
//                stateListeners.forEach(RedisStateListener::onUnavailable);
//                log.error("Redis由于功能异常已关闭");
//            }
//        }
//    }
//
//    static void addStateListener(RedisStateListener lis) {
//        stateListeners.add(lis);
//    }
//
//}
