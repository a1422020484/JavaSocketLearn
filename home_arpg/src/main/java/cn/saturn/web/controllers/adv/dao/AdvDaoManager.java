package cn.saturn.web.controllers.adv.dao;

import java.util.Date;
import java.util.List;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class AdvDaoManager implements ApplicationContextAware{

	private static AdvDAO  advDAO;
	
	
	public static AdvModel getAdvByDev(String dev,Date data ){
		return advDAO.getAdvByDev(dev,data);
	}
	
	public  static void  insertOrUpdate(AdvModel adv){
		advDAO.insertOrUpdate(adv);
	}
	
	public  static void  Update(AdvModel adv){
		advDAO.Update(adv);
	}
	
	
	 @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		 advDAO = applicationContext.getBean(AdvDAO.class);
    }
	

}