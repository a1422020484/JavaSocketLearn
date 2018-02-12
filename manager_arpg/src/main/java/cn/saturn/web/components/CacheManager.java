package cn.saturn.web.components;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cn.saturn.web.utils.Utils;
import zyt.spring.cache.entity.Entities;
import zyt.spring.cache.entity.EntityFactorys;

//import zyt.utils.*;
@Component
public class CacheManager extends zyt.spring.cache.CacheManager<Long> implements ApplicationContextAware {

	public CacheManager() {
		super(Utils.cacheSize);
	}

	@Override
	protected Entities create(Long id) {
		return new Entities(id);
	}

	// public static final EntityFactorys factorys = new EntityFactorys();

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		EntityFactorys.init(context);
	}

	@Override
	public <T> T getEntity(Long id, Class<T> cls) {
		if (!Utils.cacheEnable) {
			EntityFactorys.IEntityFactory<T> factory = EntityFactorys.getFactoryOfEntityClass(cls);
			if (factory == null) {
				return null;
			}
			// 从工厂中获取创建一个对象(通常就是数据库获取)
			T obj = factory.get(id);
			return obj; // 不适用缓存
		}
		return super.getEntity(id, cls);
	}

	@Override
	public <T> T getEntityIfExist(Long id, Class<T> cls) {
		if (!Utils.cacheEnable) {
			return null; // 不适用缓存
		}
		return super.getEntityIfExist(id, cls);
	}
}
