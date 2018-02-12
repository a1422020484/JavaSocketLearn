package cn.saturn.web.controllers.gift.dao;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xiezuojie
 */
@Component
public class GiftManager implements ApplicationContextAware {

    public static GiftCfgDAO giftDAO;
    public static GiftRecordDAO giftRecordDAO;

    /**
     *
     * @param giftId
     * @return
     */
    public static GiftCfg getGift(String giftId) {
        return giftDAO.get(giftId);
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
    public static void insertGiftRecord(GiftRecord giftRecord) {
        giftRecordDAO.insert(giftRecord);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        giftDAO = applicationContext.getBean(GiftCfgDAO.class);
        giftRecordDAO = applicationContext.getBean(GiftRecordDAO.class);
    }
}
