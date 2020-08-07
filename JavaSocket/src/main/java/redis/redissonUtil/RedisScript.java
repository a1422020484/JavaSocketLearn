package redis.redissonUtil;

/**
 * Redis Lua Script
 * 注意，使用Script一定要注意Slot问题，在使用集群时，如果多个Key的访问不是在同一个节点，那么失败
 *
 * @author xiezuojie
 */
public class RedisScript {

    public String scriptFormat;
    public Object[] args;

    public String script; // Lua script
    // Redisson已经处理了EVALSHA的缓存
//    public String sha; // 脚本的SHA1校验和，在服务器启动时加载

    public RedisScript(String scriptFormat, Object...args) {
        this.scriptFormat = scriptFormat;
        this.args = args;
    }

    public void format() {
        script = String.format(scriptFormat, args);
    }
}
