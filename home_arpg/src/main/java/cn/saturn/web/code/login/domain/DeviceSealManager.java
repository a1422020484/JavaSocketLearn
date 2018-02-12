package cn.saturn.web.code.login.domain;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;

/**
 * 设备信息管理 通过设备信息封停
 * @author Administrator
 *
 */

@Component
public class DeviceSealManager implements ApplicationContextAware{
	
	 private static DeviceSealDAO deviceSealDAO;
	 
	 public static DeviceSeal getDeviceSeal(String del) {
		 DeviceSeal deviceSeal =  deviceSealDAO.get(del);
	       
	        return deviceSeal;
	    }
	 
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		deviceSealDAO = applicationContext.getBean(DeviceSealDAO.class);
		
	}

}
