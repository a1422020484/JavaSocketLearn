package cn.saturn.web.controllers.statistics.dao;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import cn.saturn.web.utils.Config;
import zyt.spring.component.ComponentManager;

@Component
public class StatManager implements ComponentManager.IComponent{
	
	private static String poke_mgr = Config.val("poke_mgr");
	private static String homemgr=StringUtils.isNotEmpty(poke_mgr)?poke_mgr:"poke_mgr";
	
	@Resource
	private NextDayTimerDAO nextDayTimerDAO;
	private boolean isComp = false;
	
	public void statComp(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(!isComp){
					try {
						isComp = true;
						nextDayTimerDAO.startComp(homemgr);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						isComp = false;
					}
				}
			}
		}).start();
	}

	@Override
	public boolean reload(ApplicationContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}
	
	
}
