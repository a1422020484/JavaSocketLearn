package cn.saturn.web.controllers.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import zyt.spring.component.ComponentManager;
import cn.saturn.web.model.auto.ModelListManager;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.GameExplorer;
import cn.saturn.web.utils.Utils;

import com.alibaba.fastjson.JSON;

@Component
public class ServerComponent extends ModelListManager<ServerModel, ServerDAO> implements ComponentManager.IComponent {

	// 静态对象
	@Resource
	private ServerDAO dao;

	// @Override ApplicationContextAware
	// public void setApplicationContext(ApplicationContext context) throws BeansException {
	// this.reload(context);
	// }

	@Override
	public void release() {
	}

	/**
	 * 根据索引获取
	 * 
	 * @param id
	 * @return
	 */
	public synchronized ServerModel get(int index) {
		return this.list.get(index);
	}

	public static List<ServerModel> getServerList() {
		List<ServerModel> list = null;
		// 使用redis共享区列表
		if (Utils.redisServer && GameExplorer.redisEnable) {
			String values = RedisUtils.get("servers");
			try {
				list = JSON.parseArray(values, ServerModel.class);
			} catch (Exception e) {
			}
		}

		// 如果redis读取不到用本地
		if (list == null && Utils.webServer) {
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			list = serverComponent.getList();
		}

		return list;
	}

	/** 获取操作列表 **/
	public static List<ServerModel> getOperateServerList() {
		List<ServerModel> list = getServerList();
		int size = (list != null) ? list.size() : 0;
		// 遍历服务器列表
		List<ServerModel> outlist = new ArrayList<ServerModel>();
		for (int i = 0; i < size; i++) {
			ServerModel server = list.get(i);
			if (server == null || !server.isOperate()) {
				continue;
			}
			outlist.add(server);
		}
		return outlist;
	}

	/**
	 * 查找出对应ID的值
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public synchronized ServerModel find(long id) {
		return super.find(id);
	}

	/**
	 * 根据ID删除服务器
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public synchronized ServerModel remove(long id) {
		return super.remove(id);
	}

	@Override
	public synchronized boolean add(ServerModel model) {
		return super.add(model);
	}

	@Override
	public synchronized boolean update(ServerModel model) {
		return super.update(model);
	}

	@Override
	public synchronized boolean reload(ApplicationContext context) {
		init(); // 初始化

		// 读取服务器列表
		List<ServerModel> list = this.getListByDAO();
		if (list != null && list.size() > 0) {
			this.list.clear();
			this.list.addAll(list);
		}

		return true;
	}

	@Override
	public ServerDAO getDAO() {
		return this.dao;
	}

	/**
	 * 获取服务器列表
	 * 
	 * @return
	 */
	@Override
	public synchronized List<ServerModel> getList() {
		return super.getList();
	}

	@Override
	public synchronized List<ServerModel> getList(int start, int size) {
		return super.getList(start, size);
	}

	@Override
	public int getSize() {
		return super.getSize();
	}

	@Override
	protected void insertByDAO(ServerModel model) {
		dao.insertOrUpdate(model);
	}

	@Override
	protected boolean updateByDAO(ServerModel model) {
		dao.insertOrUpdate(model);
		return true;
	}

}
