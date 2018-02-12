package cn.saturn.web.controllers.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.alibaba.fastjson.JSONObject;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.login.dao.UserModel;
import cn.saturn.web.controllers.logs.dao.LogDAO;
import cn.saturn.web.controllers.logs.dao.LogModel;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.ProtocolTools;
import cn.saturn.web.utils.ResultMsg;
import cn.saturn.web.utils.ResultMsgUtils;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.StringExtUtil;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import proto.ProtocolWeb.GetGMCmdTextWCS;
import proto.ProtocolWeb.GetGMCmdTextWSC;

@Path("gm")
public class GMController {
	@Resource
	LogDAO logDao;

	@UserAuthority(PageModel.gm_page)
	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/mail/gm/index");
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.gm_page)
	@Get("index")
	public String index(Invocation inv, @Param("srvId") int srvId) throws Throwable {
		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();
		srvId = WebUtils.checkSrvId(srvId, request);

		// 查找服务器
		ServerModel serverModel = ServerUtils.getServer(srvId, true);
		if (serverModel == null) {
			request.setAttribute("curSrvId", 0);
			request.setAttribute("srvCode", "error");
			return "gm"; // 不处理
		}
		// 设置服务器名称
		srvId = (int) serverModel.getId();
		request.setAttribute("curSrvId", srvId);
		request.setAttribute("srvCode", serverModel.getSrvStr0());

		// GM文档
		String key = "gmText_" + srvId;
		String cmdText = (String) Utils.tempDates.get(key);
		// cmdText = null;
		if (cmdText != null) {
			request.setAttribute("cmdText", cmdText);
			return "gm";
		}

		// 读取服务器GM文档
		// 连接服务器
		IClient client = serverModel.createClient();
		if (client == null) {
			return "gm"; // 不处理
		}

		// 发送消息
		GetGMCmdTextWCS.Builder msgb = GetGMCmdTextWCS.newBuilder();
		// 发送并等待回馈
		GetGMCmdTextWSC retMsg = client.call(Head.H11101, msgb.build(), GetGMCmdTextWSC.class);
		if (retMsg == null) {
			// return JumpController.error(inv, "读取失败:" + client.getErrStr(),
			// defualUrl);

			// 编写无法连接
			cmdText = "无法访问.";
			request.setAttribute("cmdText", cmdText);
			Utils.tempDates.put(key, cmdText, 60 * 1000);
			return "gm"; // 读取失败
		}
		cmdText = retMsg.getCmdText();
		// 处理文档成html字符串
		cmdText = htmlStr(cmdText);
		request.setAttribute("cmdText", cmdText);
		// 记录储存
		Utils.tempDates.put(key, cmdText, 60 * 1000);

		return "gm";
	}

	@LoginCheck
	@UserAuthority(PageModel.gm_page)
	@Get("toSend")
	public String toSend(Invocation inv, @NotBlank @Param("srvId") int srvId, @NotBlank @Param("cmd") String cmd)
			throws Throwable {
		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();
		srvId = WebUtils.checkSrvId(srvId, request);

		// 查找服务器
		ServerModel serverModel = ServerUtils.getServer(srvId, true);
		if (serverModel == null) {
			request.setAttribute("curSrvId", 0);
			request.setAttribute("srvCode", "error");
			return "gm"; // 不处理
		}

		List<String> cmds = StringExtUtil.toList(cmd, ";");
		List<ResultMsg> retMsg = ProtocolTools.toSendGMCmdWSC(serverModel, cmds);

		// 做日志
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");
		List<String> srvIds = new ArrayList<>();
		srvIds.add(serverModel.getSrvStr0());
		Map<String, Object> map = new HashMap<>();
		map.put("ctx", cmd);
		map.put("srvIds", srvIds);
		String json = JSONObject.toJSONString(map);
		LogModel logModel = LogModel.create(userModel, LogModel.gm_type, json);
		if (logModel != null)
			logDao.insert(logModel);

		return "@" + ResultMsgUtils.getResult2Msg(retMsg);
	}

	public static String htmlStr(String str) {
		StringBuffer strBuf = new StringBuffer();
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			char ch = str.charAt(i);
			if (ch == '\n') {
				strBuf.append("<br/>");
			} else {
				strBuf.append(ch);
			}
		}

		return strBuf.toString();
	}
}
