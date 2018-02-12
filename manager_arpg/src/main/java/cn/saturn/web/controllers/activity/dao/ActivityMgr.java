package cn.saturn.web.controllers.activity.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import cn.saturn.web.model.auto.ModelListManager;
import zyt.spring.component.ComponentManager;

@Component
public class ActivityMgr extends ModelListManager<ActivityModel, ActivityDAO> implements ComponentManager.IComponent {

	@Resource
	ActivityDAO dao;

	@Override
	public boolean reload(ApplicationContext context) {
		init(); // 初始化

		List<ActivityModel> list = this.getListByDAO();
		int count = (list != null) ? list.size() : 0;
		if (count > 0) {
			this.list.addAll(list);
		}

		return true;
	}

	@Override
	public void release() {

	}

	@Override
	protected ActivityDAO getDAO() {
		return dao;
	}

	@Override
	public List<ActivityModel> getList() {
		return super.getList();
	}

	@Override
	public ActivityModel find(long id) {
		return super.find(id);
	}

	@Override
	public boolean add(ActivityModel model) {
		if (super.add(model)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean update(ActivityModel model) {
		return super.update(model);
	}

	@Override
	public ActivityModel remove(long id) {
		return super.remove(id);
	}
}