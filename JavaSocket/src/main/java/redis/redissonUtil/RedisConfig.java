package redis.redissonUtil;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;

/**
 */
public class RedisConfig {

    public String name;
    public int initializerServerId;

    public static RedisConfig get() {
        RedissonClient cli = RedissonUtil.getRedisson();
        RBucket<RedisConfig> bucket = cli.getBucket(RedisKeys.Public.CONFIG, JsonJacksonCodec.INSTANCE);
        RedisConfig cfg = bucket.get();
        if (cfg == null) {
            cfg = new RedisConfig();
        }
        return cfg;
    }

    public void update() {
        RedissonClient cli = RedissonUtil.getRedisson();
        RBucket<RedisConfig> bucket = cli.getBucket(RedisKeys.Public.CONFIG, JsonJacksonCodec.INSTANCE);
        bucket.set(this);
    }

}
