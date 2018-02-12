package cn.saturn.web.controllers.power;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import proto.ProtocolWeb.GetPlayerInfoAllWCS;
import proto.ProtocolWeb.GetPlayerInfoAllWSC;

@Path("info")
public class PlayerInfoController {
	public static final String defualPath = "/power/info/show";

	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, defualPath);
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.power_log)
	@Get("show")
	public String show(Invocation inv, @Param("srvId") int srvId) throws Throwable {

		return "info";
	}

	@UserAuthority(PageModel.power_log)
	@Get("toCheck")
	public String toCheck(Invocation inv, @Param("srvId") int srvId, @Param("playerText") String playerText) throws Throwable {
		if(srvId<=0)
			return "@服务器id不存在";
		if(org.apache.commons.lang.StringUtils.isEmpty(playerText))
		{
			return "@用户不存在!";
		}
		// 连接服务器
		IClient client = ServerUtils.createClient(srvId);
		if (client == null) {
			return "@[" + WebUtils.serverStr(srvId) + "]服务器连接错误"; // 不处理
		}

		// 发送消息
		GetPlayerInfoAllWCS.Builder msgb = GetPlayerInfoAllWCS.newBuilder();
		msgb.setPlayerText(playerText);
		// 发送并等待回馈
		GetPlayerInfoAllWSC retMsg = client.call(Head.H10006, msgb.build(), GetPlayerInfoAllWSC.class);
		if (retMsg == null) {
			return "@查无此人-" + playerText; // 不处理
		}

		// 读取角色信息
		int id = retMsg.getPlayerId();
		String name = retMsg.getName();
		long accountId = retMsg.getAccountId();
		String account = retMsg.getAccount();
		String platfrom = retMsg.getPlatfrom();
		int lv = retMsg.getLv();
		int gold = retMsg.getGold();
		int vip = retMsg.getVip();
		int exp = retMsg.getExp();
		int crystal = retMsg.getCrystal();
		// 登陆时间
		int loginTime = retMsg.getLoginTime();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String loginTimeStr = sdf.format(new Date(loginTime * 1000L));
		// System.out.println(s);

		// 写入消息
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("accountId", accountId);
		request.setAttribute("account", account);
		request.setAttribute("platfrom", platfrom);
		request.setAttribute("lv", lv);
		request.setAttribute("vip", vip);
		request.setAttribute("exp", exp);
		request.setAttribute("money", gold);
		request.setAttribute("crystal", crystal);
		request.setAttribute("loginTime", loginTimeStr);

		return "playerTable";
	}
}
