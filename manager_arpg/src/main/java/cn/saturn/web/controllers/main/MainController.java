package cn.saturn.web.controllers.main;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.main.dao.Menu;
import cn.saturn.web.controllers.main.dao.UseMenu;
import cn.saturn.web.controllers.menu.dao.MenuManager;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.utils.AuthorityUtils;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.paoding.rose.web.var.Flash;
import proto.Protocol.PlayerInfo;
import proto.ProtocolWeb.ReadPlayerInfoWCS;
import proto.ProtocolWeb.ReadPlayerInfoWSC;
import zyt.spring.component.ComponentManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Path("")
public class MainController {
	protected static final boolean codeAction = Utils.config.get("codeAction", true);

	/**
	 * 读取角色菜单(右上角)
	 * 
	 * @param inv
	 * @return
	 */
	private Menu getUseMenus(Invocation inv) {
		// 角色菜单列表
		Menu useMenu = new Menu("", "", "", null);
		useMenu.add(new UseMenu("密码修改", Utils.url(inv, "/account/changePwd"), "icon-user glyph-icon"));
		useMenu.add(new UseMenu("退出", Utils.url(inv, "/login/tologout"), "icon-signout glyph-icon"));
		return useMenu;
	}

	@LoginCheck
	@UserAuthority
	@Get("")
	public String index(Invocation inv) throws Throwable {
		// 检测登陆状态
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("userName");
		request.setAttribute("username", username);
		request.setAttribute("version", Utils.config.get("version", "0.0"));

		// 角色菜单列表
		Menu useMenu = getUseMenus(inv);
		request.setAttribute("useMenus", useMenu);


		// 读取flash
		//Flash flash = inv.getFlash();
		//String indexStr = (flash != null) ? flash.get("index") : "false";
		//boolean index = zyt.utils.Utils.ObjectUtils.baseValue(indexStr, Boolean.class);

		inv.addFlash("mainView", "true");
		return "main";
	}

	@LoginCheck
	@UserAuthority
	@Get("top")
	public String top(Invocation inv) throws Throwable {
		// 检测登陆状态
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("userName");
		request.setAttribute("username", username);

		return "top";
	}

	/**
	 * 角色输入选择页面
	 * 
	 * @param inv
	 * @param srvId
	 * @return
	 * @throws Throwable
	 */
	@UserAuthority
	@Get("userDialog")
	public String userDialog(Invocation inv, @Param("srvId") int srvId) throws Throwable {

		// 读取传递参数
		Flash flash = inv.getFlash();
		String postUrl = flash.get("postUrl");

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("srvId", srvId);
		request.setAttribute("postUrl", postUrl);
		request.setAttribute("titile", "玩家输入页面");
		return "user_dialog";
	}

	/**
	 * 读取服务器角色信息
	 * 
	 * @param inv
	 * @param srvId
	 * @param playerText
	 *            角色信息, 可以是ID, 也可以是角色名
	 * @return
	 */
	@UserAuthority
	@Get("findUser")
	public String findUser(Invocation inv, @Param("srvId") int srvId, @Param("playerText") String playerText) {
		if (playerText == null || playerText.length() <= 0) {
			return "@ ";
		}

		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();
		srvId = WebUtils.checkSrvId(srvId, request);

		// 连接服务器
		IClient client = ServerUtils.createClient(srvId);
		if (client == null) {
			return "@[" + WebUtils.serverStr(srvId) + "]服务器连接错误"; // 不处理
		}

		// 文本分割
		playerText = playerText.replaceAll("\n", "");
		String playerText0[] = playerText.split(";");
		int count = (playerText0 != null) ? playerText0.length : 0;

		// 文本列表
		StringBuffer idStrBuf = new StringBuffer();
		StringBuffer playerStrBuf = new StringBuffer();

		// 遍历查询
		for (int i = 0; i < count; i++) {
			String playerText1 = playerText0[i];
			// 处理分隔符
			if (i > 0) {
				idStrBuf.append(";");
				playerStrBuf.append("\n");
			}

			// 生成消息
			ReadPlayerInfoWCS.Builder msgb = ReadPlayerInfoWCS.newBuilder();
			msgb.setPlayerText(playerText1);

			// 发送并等待回馈
			ReadPlayerInfoWSC retMsg = client.call(Head.H10005, msgb.build(), ReadPlayerInfoWSC.class);
			if (retMsg == null) {
				// return "@[" + WebUtils.serverStr(srvId) + "]查无此人-" +
				// playerText; // 不处理
				idStrBuf.append("0");
				playerStrBuf.append("查无此人-");
				playerStrBuf.append(playerText1);
				continue;
			}

			// 读取用户信息
			PlayerInfo playerInfo = retMsg.getPlayerInfo();
			int playerId = playerInfo.getId();
			if (playerId <= 0) {
				// return "@[" + WebUtils.serverStr(srvId) + "]并无这个家伙-" +
				// playerText;
				idStrBuf.append("0");
				playerStrBuf.append("并无此人-");
				playerStrBuf.append(playerText1);
				continue;
			}

			// 输出角色文本
			// String playerStr = "(" + playerId + "-" + playerInfo.getName() +
			// ")";

			idStrBuf.append(playerId);
			playerStrBuf.append("[");
			playerStrBuf.append(playerId);
			playerStrBuf.append("]");
			playerStrBuf.append(playerInfo.getName());

		}

		return "@" + idStrBuf.toString() + "," + WebUtils.serverStr(srvId) + (count > 1 ? "\n" : "-")
				+ playerStrBuf.toString();
	}


	/**
	 * 读取左边菜单
	 * 
	 * @param inv
	 * @return
	 */
	@UserAuthority
	@Post("menuList")
	public String getMenuList(Invocation inv) {
		// 读取权限
		HttpServletRequest request = inv.getRequest();
		int authority = AuthorityUtils.getAuthority(request); // 当前权限
		MenuManager mMgr = ComponentManager.getComponent(MenuManager.class);
		return "@" + mMgr.getTreeMenu(authority);
	}

}
