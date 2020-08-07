package redis.redissonUtil;

/**
 * @author xiezuojie
 */
public interface RedisStateListener {

    /**
     * 在Redis集群从不可用恢复到可用时调用，如果集群一开始就是可用的，那么将不会触发。
     */
    void onAvailable();

    /**
     * 在Redis集群变为不可用时调用
     */
    void onUnavailable();

}
