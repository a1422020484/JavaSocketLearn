package cn.saturn.web.controllers.statistics.dao;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import cn.saturn.web.utils.Config;
import zyt.spring.component.ComponentManager;

@Component
public class SubChannelSummaryManager implements ComponentManager.IComponent{
	@Resource
	private SubChannelSummaryDAO summaryDAO;
	
	private static String poke_mgr = Config.val("poke_mgr");
	private static String homemgr=StringUtils.isNotEmpty(poke_mgr)?poke_mgr:"poke_mgr";
	
	private boolean isSummary;
	
	public void summary(){
		isSummary = true;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					summaryDAO.summary(homemgr);	
					isSummary = false;
				} catch (Exception e) {
					e.printStackTrace();
					isSummary = false;
				}
			}
		}).start();;
	}

	public boolean isSummary() {
		return isSummary;
	}

	public void setSummary(boolean isSummary) {
		this.isSummary = isSummary;
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
