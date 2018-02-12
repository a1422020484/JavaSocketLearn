package cn.saturn.web.code.cdkey.domain;

import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiezuojie
 */
@Component
public class CDKeyManager implements ApplicationContextAware {

    static CDKeyDAO cdKeyDAO;

    /**
     *
     * @param key 用于领取的key
     * @return CDKey
     */
    public static CDKey getCdKey(String key) {
        CDKey cdKey = null;
//        if (RedisUtils.RedisCdKey) {
//            String json = RedisUtils.hget(RedisKeys.K_CDKEY, key);
//            if (json != null) {
//                try {
//                    cdKey = JSON.parseObject(json, CDKey.class);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        if (cdKey == null) {
            cdKey = cdKeyDAO.get(key);
            if (cdKey != null && RedisUtils.RedisCdKey) {
                RedisUtils.hset(RedisKeys.K_CDKEY, key, JSON.toJSONString(cdKey));
            }
        }
        return cdKey;
    }

    /**
     *
     * @param cdKey
     * @return 添加/更新是否成功
     */
    public static boolean insertOrUpdate(CDKey cdKey) {
        Integer i = cdKeyDAO.insertOrUpdate(cdKey);
        boolean rs = i != null && i.intValue() > 0;
        RedisUtils.hdel(RedisKeys.K_CDKEY, cdKey.getKey());
//        if (rs) {
//            RedisUtils.hdel(RedisKeys.K_CDKEY, cdKey.getKey());
//        }
        return rs;
    }
    
    /**
     * 获取某个类型cdk的使用次数
     * @param cdKey
     * @param playerId
     * @return
     */
    public static int getUsedNum(CDKey cdKey, int playerId, int svrId){
    	return cdKeyDAO.getUsedNum(cdKey.getType(), playerId, svrId);
    }
    
    /**
     * 获取用户使用某个类型cdk的列表
     * @param type
     * @param playerId
     * @return
     */
    public static List<CDKey> getUsedList(int type, int playerId, int svrId){
    	return cdKeyDAO.getUsedList(type, playerId, svrId);
    }

    public static void insertOrUpdate(List<CDKey> cdKeys) {
        cdKeyDAO.insertOrUpdate(cdKeys);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        cdKeyDAO = applicationContext.getBean(CDKeyDAO.class);
    }
}
