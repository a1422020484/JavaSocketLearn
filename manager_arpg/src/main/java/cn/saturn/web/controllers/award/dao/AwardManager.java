package cn.saturn.web.controllers.award.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import zyt.spring.component.ComponentManager;

@Component
public class AwardManager implements ComponentManager.IComponent {

	@Resource
	AwardDAO dao;
	
	public List<AwardModel> getAwardList(){
		return dao.getList();
	}

	@Override
	public boolean reload(ApplicationContext context) {
		return false;
	}

	@Override
	public void release() {

	}

	
}