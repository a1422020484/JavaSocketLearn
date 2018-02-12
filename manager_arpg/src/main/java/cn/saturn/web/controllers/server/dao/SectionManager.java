package cn.saturn.web.controllers.server.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.saturn.web.model.auto.ModelListManager;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.GameExplorer;
import cn.saturn.web.utils.Utils;
import zyt.spring.component.ComponentManager;

@Component
public class SectionManager extends ModelListManager<SectionModel, SectionDAO> implements ComponentManager.IComponent {
	@Resource
	private SectionDAO dao;

	@Override
	public SectionDAO getDAO() {
		return this.dao;
	}

	@Override
	public List<SectionModel> getList() {
		return super.getList();
	}

	@Override
	public int getSize() {
		return super.getSize();
	}

	public static List<SectionModel> getSectionList() {
		List<SectionModel> list = null;
		// 使用redis共享区列表
		if (Utils.redisServer && GameExplorer.redisEnable) {
			String values = RedisUtils.get("sections");
			try {
				list = JSON.parseArray(values, SectionModel.class);
			} catch (Exception e) {
			}
		}
		// 如果redis读取不到用本地
		if (list == null && Utils.webServer) {
			// 本地获取
			SectionManager sectionManager = ComponentManager.getComponent(SectionManager.class);
			list = sectionManager.getList();
		}
		return list;
	}

	@Override
	public List<SectionModel> getList(int start, int size) {
		return super.getList(start, size);
	}

	@Override
	public SectionModel find(long id) {
		return super.find(id);
	}

	@Override
	public SectionModel remove(long id) {
		return super.remove(id);
	}

	@Override
	public boolean add(SectionModel model) {
		return super.add(model);
	}

	@Override
	public boolean update(SectionModel model) {
		return super.update(model);
	}

	@Override
	public boolean reload(ApplicationContext context) {
		init(); // 初始化

		// 读取服务器列表
		List<SectionModel> list = this.getListByDAO();
		if (list != null && list.size() > 0) {
			this.list.clear();
			this.list.addAll(list);
		}

		// for (int i = 1; i <= 10; i++) {
		// SectionModel sectionModel = new SectionModel();
		// sectionModel.setName("第" + i + "区");
		// sectionModel.setRecommend(i % 2 == 0);
		// sectionModel.setId(i);
		// int state = i % 2;
		// sectionModel.setState(state);
		//
		// this.list.add(sectionModel);
		// }

		return true;
	}

	@Override
	public void release() {
	}

	@Override
	protected void insertByDAO(SectionModel model) {
		this.getDAO().insertOrUpdate(model);

	}

	@Override
	protected boolean updateByDAO(SectionModel model) {
		this.getDAO().insertOrUpdate(model);
		return false;
	}

}
