package cn.saturn.web.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class GameExplorer implements ApplicationContextAware {
//	public static final CacheManager cacheManager = new CacheManager();
//
//	public static <T> T getEntity(long id, Class<T> cls) {
//		return cacheManager.getEntity(id, cls);
//	}
//
//	public static <T> T getEntityIfExist(long id, Class<T> cls) {
//		return cacheManager.getEntityIfExist(id, cls);
//	}
//
//	public static <T> T getAccount(Class<T> clazz) {
//		return (context != null) ? context.getBean(clazz) : null;
//	}
//
//	public static Object getAccount(String beanName) {
//		return (context != null) ? context.getBean(beanName) : null;
//	}
//
//	private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		context = applicationContext;
    }
}
