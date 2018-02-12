package cn.saturn.web.controllers.power;

import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xiezuojie
 */
@Component
public class VindicatorIpManager implements ApplicationContextAware {

    static VindicatorIpDAO vindicatorIpDAO;

    /**
     * @param ip
     * @return 是否是维护者ip
     */
    public static boolean isVindicatorIp(String ip) {
        Set<String> set = getVindicatorIps();
        if (set == null) {
            return false;
        }

        return set.contains(ip);
    }

    /**
     * @return 维护者ip列表
     */
    public static synchronized Set<String> getVindicatorIps() {
        List<String> list = null;
        if (RedisUtils.RedisVindicatorIp) {
            String json = RedisUtils.get(RedisKeys.K_VINDICATOR_IP);
            if (json != null) {
                try {
                    list = JSON.parseArray(json, String.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (list == null) {
            list = vindicatorIpDAO.getList();
            if (list != null && RedisUtils.RedisVindicatorIp) {
                RedisUtils.set(RedisKeys.K_VINDICATOR_IP, JSON.toJSONString(list));
            }
        }

        return list != null ? new HashSet<>(list) : null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        vindicatorIpDAO = applicationContext.getBean(VindicatorIpDAO.class);
        getVindicatorIps();
    }
}
