package cn.saturn.web.controllers.statistics.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import zyt.spring.component.ComponentManager;

@Component
public class SubPlatformManager implements ComponentManager.IComponent{
	@Resource
	private SubPlatformDAO subPlatformDAO;
	
	private static List<SubPlatformModel> SUB_PLATFORM_MODELS = new ArrayList<>();
	
	private void init(){
		SUB_PLATFORM_MODELS.clear();
		SUB_PLATFORM_MODELS = subPlatformDAO.getList();
	}
	
	public List<SubPlatformModel> getSubPlatformList(){
		if(SUB_PLATFORM_MODELS == null || SUB_PLATFORM_MODELS.size()==0)
			init();
		return SUB_PLATFORM_MODELS;
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
