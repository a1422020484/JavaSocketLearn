package cn.saturn.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cn.saturn.web.controllers.account.dao.AuthorityToMenuModel;
import cn.saturn.web.controllers.account.dao.SettingAuthorityDAO;
import cn.saturn.web.controllers.login.dao.UserModel;

/** 权限处理 **/

@Component
public class AuthorityUtils implements ApplicationContextAware {
	// 权限级别
	public static final int authority_server = UserModel.authority_three;
	public static final int authority_admin = 10;
	
	public static SettingAuthorityDAO settingAuthorityDAO;

	public Map<Integer, List<Integer>> reloadAuthorityMap() {

		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		List<AuthorityToMenuModel> list = settingAuthorityDAO
				.getAuthorityToMenuList();
		for (AuthorityToMenuModel model : list) {
			if (map.containsKey(model.getAuthority_id())) {
				map.get(model.getAuthority_id()).add(model.getMenu_id());

			} else {
				List<Integer> menuList = new ArrayList<Integer>();
				menuList.add(model.getMenu_id());
				map.put(model.getAuthority_id(), menuList);
			}
		}

		return map;
	}

	/**
	 * 检测权限
	 * 
	 * @param authority
	 *            权限级别
	 * @param mode
	 *            模块
	 * @return
	 */
	public boolean checkAuthority(int authority, int modeId) {
		// 如果是管理员一定具有
		if (authority == authority_admin)
			return true;

		List<Integer> mpdses = this.getAuthoritys(authority);
		// 空表示不具有
		if (null == mpdses)
			return false;

		// 如果包含该菜单,具有
		return mpdses.contains(modeId);
	}

	public List<Integer> getAuthoritys(int i) {
		return reloadAuthorityMap().get(i);
	}

	public static boolean authorityServer(HttpServletRequest request) {
		int authority = getAuthority(request);
		return authority >= authority_server;
	}


	/**
	 * 读取权限
	 * 
	 * @param request
	 * @return
	 */
	public static int getAuthority(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 调试模式检测
		boolean debug = Utils.config.get("debug", Boolean.class);
		int authority = 0;
		if (debug) {
			// 调试模式
			Integer value = (Integer) session.getAttribute("userAuthority");
			authority = (value != null) ? value : 0;
		} else {
			// 正常
			UserModel userModel = (UserModel) session.getAttribute("user");
			authority = (userModel != null) ? userModel.getAuthority()
					: UserModel.authority_normal;
		}

		return authority;
	}

	/**
	 * 是否是管理员<br>
	 * 
	 * @param authority
	 * @return
	 */
	public static boolean isAdminAuthority(int authority) {
		return authority == authority_admin ? true : false;
	}

	/** 权限名称 **/
	private List<String> getNames() {

		return settingAuthorityDAO.getAuthorityNames();
	}

	public List<String> getList() {
		return getNames();
	}

	public String[] getArray() {
		return getNames().toArray(new String[0]);
	}

	public int size() {
		return (getNames() != null) ? getNames().size() : 0;
	}

	public String get(int i) {
		if (i == authority_admin) {
			return "管理员";
		}
		
		String authorityName = settingAuthorityDAO.getAuthorityNamesById(String.valueOf(i)).get(0);
		if (authorityName==null) {
			return "错误";
		}
		return (authorityName != null) ? authorityName : "权限" + i;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		settingAuthorityDAO = applicationContext.getBean(SettingAuthorityDAO.class);
		
	}

	// @Override
	// public void setApplicationContext(ApplicationContext context) throws
	// BeansException {
	// this.reload();
	// }

}
