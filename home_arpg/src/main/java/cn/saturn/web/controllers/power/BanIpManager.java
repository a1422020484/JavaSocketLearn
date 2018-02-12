package cn.saturn.web.controllers.power;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xiezuojie
 */
@Component
public class BanIpManager implements ApplicationContextAware {

    public static BanIpDAO banIpDAO;

    /**
     * @param ip
     * @return ip是否被封
     */
    public static boolean isBanIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            return false; // 参数错误
        }
        boolean exists = false;
        try {
//            exists = RedisUtils.hexists(RedisKeys.K_BAN_IP, ip);
//            if (exists) {
//                return true;
//            }

            int count = banIpDAO.count(ip);
            exists = count > 0;
//            if (exists) {
//                RedisUtils.hset(RedisKeys.K_BAN_IP, ip, "1");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        banIpDAO = applicationContext.getBean(BanIpDAO.class);
    }
}
