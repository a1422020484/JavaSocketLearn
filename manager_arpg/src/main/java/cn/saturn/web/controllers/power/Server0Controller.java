package cn.saturn.web.controllers.power;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import proto.ProtocolWeb.ServerStopWCS;
import proto.ProtocolWeb.ServerStopWSC;
import zyt.utils.ListUtils;

@Path("server")
public class Server0Controller {
	public static final String defualPath = "/power/server/show";

	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, defualPath);
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.power_server_page)
	@Get("show")
	public String show(Invocation inv) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("toCloseUrl", Utils.url(inv, "/power/server/toClose"));

		return "server";
	}

	@LoginCheck
	@UserAuthority(PageModel.power_server_page)
	@Get("toClose")
	public String toClose(Invocation inv, @Param("srvId") String srvStrs) throws Throwable {

		// 解析服务器ID
		String srvIdStrs = inv.getParameter("srvId");
		if (srvIdStrs == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器ID为空");
		}
		// 解析出服务器ID列表
		int[] srvIds = ServerUtils.getSrvIds(srvIdStrs);
		int count = (srvIds != null) ? srvIds.length : 0;
		if (count <= 0) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器ID数量为空");
		}

		// 获取参数
		int countDown = Utils.getIntParameter(inv, "countDown");
		String closeMsg = inv.getParameter("closeMsg");
		countDown = Math.max(countDown, 1);

		// 发送消息
		ServerStopWCS.Builder msgb = ServerStopWCS.newBuilder();
		msgb.setCountDown(countDown);
		if (closeMsg != null && closeMsg.length() > 0) {
			msgb.setMsg(closeMsg);
		}

		StringBuffer strBuf = new StringBuffer();
		strBuf.append("执行成功:");
		// 遍历执行
		//boolean result = 
		ServerUtils.operateServerAction(new ListUtils.IAction<ServerModel>() {
			@Override
			public boolean action(ServerModel serverModel, Iterator<?> iter) {
				long srvId = serverModel.getId();
				// 判断是否在列表中
				boolean enable = Utils.ArrayUtils.find(srvIds, (int) srvId);
				if (!enable) {
					return true; // 跳过
				}

				// 连接服务器
				IClient client = serverModel.createClient();
				if (client == null) {
					return true; // url错误, 跳过.
				}

				// 发送并等待回馈
				ServerStopWSC retMsg = client.call(Head.H14002, msgb.build(), ServerStopWSC.class);
				if (retMsg == null) {
					return true; // 跳过
				}

				// 成功处理
				strBuf.append(serverModel.getSrvStr0());
				strBuf.append(",");

				return true; // 下一个
			}
		});

		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "操作成功:" + strBuf.toString(),"power/server/show?mainView=true");
	}

}
