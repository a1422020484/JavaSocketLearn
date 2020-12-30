package redis.redissonUtil;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author xiezuojie
 */
public abstract class LongIDPersistEntity {

    /**
     * @return 长整形的唯一ID，对应数据库
     */
    @JSONField(deserialize = false, serialize = false)
    public abstract long getId();

}
