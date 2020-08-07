package redis.redissonUtil;

import com.alibaba.fastjson.annotation.JSONField;

import nettyServer.util.cache.PersistEntity;

/**
 * @author xiezuojie
 */
public abstract class LongIDPersistEntity extends PersistEntity {

    /**
     * @return 长整形的唯一ID，对应数据库
     */
    @JSONField(deserialize = false, serialize = false)
    public abstract long getId();

}
