package cn.saturn.web.utils;

import java.util.List;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import zyt.spring.component.ComponentManager;
import zyt.utils.ListUtils;

public class ServerUtils {

	/** 服务器ID解析 **/
	public static int[] getSrvIds(String srvIdStrs) {
		if (srvIdStrs == null) {
			return null;
		}
		// 解析ID
		int[] srvIds = null;
		String[] srvIdStrs0 = srvIdStrs.split(",");
		int count = srvIdStrs0.length;
		if (count <= 0) {
			return null;
		}
		try {
			// 遍历解析
			srvIds = new int[count];
			for (int i = 0; i < count; i++) {
				srvIds[i] = Integer.valueOf(srvIdStrs0[i]);
			}
		} catch (Exception ex) {
			return null;
		}
		return srvIds;
	}

	/** 操作服务器遍历执行 **/
	public static boolean operateServerAction(ListUtils.IAction<ServerModel> action) {
		// 服务器提交
		List<ServerModel> list = ServerComponent.getOperateServerList();
		int size = (list != null) ? list.size() : 0;
		for (int i = 0; i < size; i++) {
			ServerModel serverModel = list.get(i);
			if (serverModel == null) {
				continue;
			}

			if (!action.action(serverModel, null)) {
				return false;
			}
			// // 连接服务器
			// IClient client = serverModel.createClient();
			// if (client == null) {
			// continue;
			// }
		}

		return true;
	}

	/**
	 * 获取服务器
	 * 
	 * @param srvId
	 * @return
	 */
	public static ServerModel getServer(int srvId) {
		return getServer(srvId, false);
	}

	/**
	 * 获取服务器
	 * 
	 * @param srvId
	 *            服务器ID
	 * @param defualt
	 *            如果ID错误是否返回默认
	 * @return
	 */
	public static ServerModel getServer(int srvId, boolean defaultSrvId) {
		// if (srvId <= 0) {
		// return null;
		// }
		// 查找服务器
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		// srvId为0的情况, 返回第一个
		if (srvId <= 0) {
			return serverComponent.get(0);
		}

		// 获取返回
		ServerModel serverModel = serverComponent.find(srvId);
		if (serverModel != null && serverModel.isOperate()) {
			return serverModel;
		}

		// 是否返回默认
		if (!defaultSrvId) {
			return null;
		}
		// 返回默认
		return serverComponent.get(0);
	}

	/**
	 * 创建服务器客户端
	 * 
	 * @param srvId
	 *            0为默认选择或者第一个
	 * @return
	 */
	public static IClient createClient(int srvId) {
		return createClient(srvId, false);
	}

	/**
	 * 创建服务器客户端
	 * 
	 * @param srvId
	 * @param defaultSrvId
	 * @return
	 */
	public static IClient createClient(int srvId, boolean defaultSrvId) {
		// 查找服务器
		ServerModel serverModel = getServer(srvId, defaultSrvId);
		if (serverModel == null) {
			return null;
		}

		// 连接服务器
		IClient client = serverModel.createClient();
		if (client == null) {
			return null;
		}
		return client;
	}

}

class ActionResult {
	public static final int code_succeed = 1;
	public static final int code_error = 0;
	public final int code;
	public final String msg;

	public ActionResult(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public boolean isSucceed() {
		return code > 0;
	}

	public static ActionResult Ok(String msg) {
		return new ActionResult(code_succeed, msg);
	}

	public static ActionResult Error(String msg) {
		return new ActionResult(code_error, msg);
	}
}