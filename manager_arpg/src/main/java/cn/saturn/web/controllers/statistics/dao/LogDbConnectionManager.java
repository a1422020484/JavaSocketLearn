package cn.saturn.web.controllers.statistics.dao;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import cn.saturn.web.model.auto.ModelListManager;
import zyt.spring.cache.entity.EntityFactorys;

@Component
public class LogDbConnectionManager extends ModelListManager<LogDbConnectionModel, LogDbConnectionDAO>
		implements EntityFactorys.IEntityFactory<LogDbConnectionModel> {

	@Resource
	protected LogDbConnectionDAO dao;

	@Override
	protected LogDbConnectionDAO getDAO() {
		return dao;
	}
	
	@Override
	public List<LogDbConnectionModel> getList() {
		return dao.getList();
	}

	@Override
	public LogDbConnectionModel get(long id) {
		LogDbConnectionModel model = dao.get(id);
		return model;
	}
}
