package cn.saturn.web.controllers.mail.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import zyt.spring.cache.entity.EntityFactorys;
import cn.saturn.web.model.auto.ModelManager;
import cn.saturn.web.utils.GameExplorer;

@Component
public class ParamManager extends ModelManager<ParamModel, ParamDAO> implements EntityFactorys.IEntityFactory<ParamModel> {

	@Resource
	protected ParamDAO dao;

	@Override
	protected ParamDAO getDAO() {
		return dao;
	}

	@Override
	public ParamModel get(long id) {
		ParamModel model = dao.get(id);
		return model;
	}

	public ParamModel get(long id, String type) {
		ParamModel model = dao.get(id, type);
		return model;
	}

	@Override
	protected void insertByDAO(ParamModel model) {
		if (model.getId() <= 0) {
			model.setId(this.newId());
		}
		dao.insertOrUpdate(model);
	}

	@Override
	protected boolean updateByDAO(ParamModel model) {
		dao.insertOrUpdate(model);
		return true;
	}

	private static ParamModel globalParam;

	public static synchronized ParamModel getGlobalParam() {

		if (globalParam == null) {
			ParamManager paramManager = GameExplorer.get(ParamManager.class);
			globalParam = paramManager.get(0, "global");
			if (globalParam == null) {
				globalParam = new ParamModel();
				globalParam.setId(0);
				globalParam.setType("global");
				globalParam.setInfo("");
				// 保存入数据
				// paramManager.insertByDAO(globalParam);
				paramManager.dao.insertOrUpdate(globalParam);
			}
		}

		return globalParam;
	}

	public static synchronized boolean updataGlobalParam() {
		if (globalParam.saveToInfo()) {
			ParamManager paramManager = GameExplorer.get(ParamManager.class);
			paramManager.updateByDAO(globalParam);
			return true;
		}
		return false;
	}
}
