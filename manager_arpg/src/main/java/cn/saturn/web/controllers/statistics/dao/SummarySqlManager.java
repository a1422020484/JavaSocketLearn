package cn.saturn.web.controllers.statistics.dao;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import cn.saturn.web.model.auto.ModelListManager;
import zyt.spring.cache.entity.EntityFactorys;

@Component
public class SummarySqlManager extends ModelListManager<SummarySqlModel, SummarySqlDAO>
		implements EntityFactorys.IEntityFactory<SummarySqlModel> {

	@Resource
	protected SummarySqlDAO dao;

	@Override
	protected SummarySqlDAO getDAO() {
		return dao;
	}
	
	@Override
	public List<SummarySqlModel> getList() {
		return dao.getList();
	}

	@Override
	public SummarySqlModel get(long id) {
		SummarySqlModel model = dao.get(id);
		return model;
	}
}
