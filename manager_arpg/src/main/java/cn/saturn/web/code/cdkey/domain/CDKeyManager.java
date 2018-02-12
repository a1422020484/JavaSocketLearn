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
        return cdKeyDAO.get(key);
    }

    /**
     *
     * @param cdKey
     * @return 添加/更新是否成功
     */
    public static boolean insertOrUpdate(CDKey cdKey) {
        Integer i = cdKeyDAO.insertOrUpdate(cdKey);
        boolean rs = i != null && i > 0;
        return rs;
    }
    
    /**
     * 获取某个类型cdk的使用次数
     * @param cdKey
     * @param playerId
     * @return
     */
    public static int getUsedNum(CDKey cdKey, int playerId){
    	return cdKeyDAO.getUsedNum(cdKey.getType(), playerId);
    }
    
    /**
     * 获取用户使用某个类型cdk的列表
     * @param type
     * @param playerId
     * @return
     */
    public static List<CDKey> getUsedList(int type, int playerId){
    	return cdKeyDAO.getUsedList(type, playerId);
    }
    
    /**
     * 获取某个类型激活码的配置数量
     * @param type
     * @return
     */
    public static int getAllNumByType(int type){
    	return cdKeyDAO.getAllNumByType(type);
    }
    
    /**
     * 查找指定类型的激活码的使用数量
     * @param type
     * @return
     */
    public static int getUsedNumByType(int type){
    	return cdKeyDAO.getUsedNumByType(type);
    }
    
    /**
     * 通过类型获取其中一条cdk
     * @param type
     * @return
     */
    public static CDKey getByType(int type){
    	return cdKeyDAO.getByType(type);
    }

    public static void insertOrUpdate(List<CDKey> cdKeys) {
        cdKeyDAO.insertOrUpdate(cdKeys);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        cdKeyDAO = applicationContext.getBean(CDKeyDAO.class);
    }
}
