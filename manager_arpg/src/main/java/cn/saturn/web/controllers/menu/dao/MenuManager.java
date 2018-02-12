package cn.saturn.web.controllers.menu.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.saturn.web.code.cache.ModelDaoCacheDestory;
import cn.saturn.web.utils.AuthorityUtils;
import zyt.spring.component.ComponentManager;

@Component
public class MenuManager extends ModelDaoCacheDestory<MenuModel> implements ComponentManager.IComponent {

	@Resource
	MenuDAO dao;

	private  Map<Integer, String> modelJsons = new HashMap<Integer, String>();

	@Override
	protected boolean onLoad() {
		List<MenuModel> ms = dao.getList();
		for (MenuModel m : ms) {
			lists.add(m);
		}
		
		return true;
	}

	@Override
	public boolean destory() {
		super.destory();
		modelJsons.clear();
		return true;
	}

	@Override
	public boolean reload(ApplicationContext context) {
		return load();
	}

	/**
	 * 递归调用创建树形结构菜单栏
	 * 
	 * @param lst
	 * @param pmodels
	 * @param pid
	 */
	private void addTreeMenu(List<MenuModel> lst, List<MenuModels> models, MenuModels pmodels, long pid,
			int authority) {
		for (int i = 0; i < lst.size(); i++) {
			MenuModel m = null;
			try {
				m = lst.get(i);
			} catch (Exception e) {
			}
			if(m == null)
				continue;
			if (m.getPid() == pid && BeanUtils.instantiate(AuthorityUtils.class).checkAuthority(authority, (int) m.getId())) {
				if (pmodels == null) {
					pmodels = new MenuModels(m);
					models.add(pmodels);
					addTreeMenu(lst, models, pmodels, m.getId(), authority);
				} else {
					MenuModels pmodels2 = new MenuModels(m);
					if (null == pmodels.getChildren()) {
						pmodels.setModels(new ArrayList<MenuModels>());
					}
					pmodels.getChildren().add(pmodels2);
					addTreeMenu(lst, models, pmodels2, m.getId(), authority);
				}
			}
		}
	}

	public String getTreeMenu(int authority) {
		String json = "";

		load();
		modelJsons.clear();
		if (!modelJsons.containsKey(authority)) {
			synchronized(this.lists){
				if (this.lists.size() > 0) {
					List<MenuModels> models = new LinkedList<MenuModels>();
					addTreeMenu(this.lists, models, null, -1, authority);
					json = JSON.toJSONString(models);
					modelJsons.put(authority, json);
				}
			}
		} else
			json = modelJsons.get(authority);

		return json;
	}

	@Override
	public void release() {
	}
}