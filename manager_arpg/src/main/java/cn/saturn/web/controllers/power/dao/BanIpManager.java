package cn.saturn.web.controllers.power.dao;

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        banIpDAO = applicationContext.getBean(BanIpDAO.class);
    }
}
