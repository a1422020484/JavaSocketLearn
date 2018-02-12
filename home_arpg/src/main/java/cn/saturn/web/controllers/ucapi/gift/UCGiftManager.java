package cn.saturn.web.controllers.ucapi.gift;

import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xiezuojie
 */
@Component
public class UCGiftManager implements ApplicationContextAware {

    public static UCGiftDAO giftDAO;
    public static UCGiftRecordDAO giftRecordDAO;

    /**
     *
     * @param giftId
     * @return
     */
    public static UCGift getGift(String giftId) {
        UCGift gift = null;
        if (RedisUtils.RedisUCGift) {
            String json = RedisUtils.hget(RedisKeys.K_UC_GIFT, giftId);
            if (json != null) {
                try {
                    gift = JSON.parseObject(json, UCGift.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (gift == null) {
            gift = giftDAO.get(giftId);
            if (gift != null && RedisUtils.RedisUCGift) {
                RedisUtils.hset(RedisKeys.K_UC_GIFT, giftId, JSON.toJSONString(gift));
            }
        }

        return gift;
    }

//    /**
//     *
//     * @return
//     */
//    public static List<UCGift> getGiftList() {
//        List<UCGift> list = giftDAO.getList();
//        return list;
//    }

    /**
     *
     * @param uniqueKey serverId+roleId+giftId+dayOfYear
     * @return 是否存在
     */
    public static boolean isExists(String uniqueKey) {
        return giftRecordDAO.count(uniqueKey) > 0;
    }

    /**
     *
     * @param giftRecord
     */
    public static void insertGiftRecord(UCGiftRecord giftRecord) {
        giftRecordDAO.insert(giftRecord);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        giftDAO = applicationContext.getBean(UCGiftDAO.class);
        giftRecordDAO = applicationContext.getBean(UCGiftRecordDAO.class);
    }
}
