package cn.saturn.web.controllers.power;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.saturn.operation.OperationExt;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.ProtocolWeb.AccountInfo;
import proto.ProtocolWeb.BannedListWCS;
import proto.ProtocolWeb.BannedListWSC;
import proto.ProtocolWeb.BannedPlayerWCS;
import proto.ProtocolWeb.BannedPlayerWSC;

@Path("banned")
public class BannedController {
	public static final String defualPath = "/power/banned";

	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/power/banned/list");
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.banned_page)
	@Get("list")
	public String banned(Invocation inv, @Param("srvId") int srvId) throws Throwable {

		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();
		srvId = WebUtils.checkSrvId(srvId, request);

		// 查找服务器
		ServerModel serverModel = ServerUtils.getServer(srvId, true);
		if (serverModel == null) {
			request.setAttribute("curSrvId", 0);
			request.setAttribute("srvCode", "error");
			return "banned"; // 不处理
		}

		// 设置服务器名称
		srvId = (int) serverModel.getId();
		request.setAttribute("curSrvId", srvId);
		request.setAttribute("srvCode", serverModel.getSrvStr0());
		return "banned";
	}

	@UserAuthority(PageModel.banned_page)
	@Get("add")
	public String add(Invocation inv, @NotBlank @Param("srvId") int srvId) {
		inv.addFlash("postUrl", Utils.url(inv, "/power/banned/toAdd"));
		return Utils.redirect(inv, "/main/userDialog?srvId=" + srvId);
	}

	@LoginCheck
	@UserAuthority(PageModel.banned_page)
	@Post("toAdd")
	public String toAdd(Invocation inv, @NotBlank @Param("srvId") int srvId,
			@NotBlank @Param("playerId") int playerId) {
		// 检测服务器ID
		if (srvId <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "未知服务器ID");
		}
		// 连接服务器
		IClient client = ServerUtils.createClient(srvId);
		if (client == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器错误");
		}

		// 发送消息
		BannedPlayerWCS.Builder msgb = BannedPlayerWCS.newBuilder();
		msgb.setPlayerId(playerId);
		msgb.setEnable(true);

		// 发送并等待回馈
		BannedPlayerWSC retMsg = client.call(Head.H13001, msgb.build(), BannedPlayerWSC.class);
		if (retMsg == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, client.getErrStr());
		}

		RedisUtils.del(RedisKeys.K_ACCOUNT);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "添加成功");
	}

	@LoginCheck
	@UserAuthority(PageModel.banned_page)
	@Get("toRemove")
	public String toRemove(Invocation inv, @NotBlank @Param("srvId") int srvId,
			@NotBlank @Param("playerId") int playerId) {
		//String defualUrl = Utils.url(inv, defualPath);
		// 检测服务器ID
		if (srvId <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "未知服务器ID");
		}
		// 连接服务器
		IClient client = ServerUtils.createClient(srvId);
		if (client == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器错误");
		}
		// 发送消息
		BannedPlayerWCS.Builder msgb = BannedPlayerWCS.newBuilder();
		msgb.setPlayerId(playerId);
		msgb.setEnable(false);
		// 发送并等待回馈
		BannedPlayerWSC retMsg = client.call(Head.H13001, msgb.build(), BannedPlayerWSC.class);
		if (retMsg == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, client.getErrStr());
		}

		RedisUtils.del(RedisKeys.K_ACCOUNT);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功！");
	}

	@Post("listJson")
	public String listJson(Invocation inv) throws IOException {
		// return "@";

		HttpServletRequest request = inv.getRequest();
		int srvId = Integer.parseInt(request.getParameter("srvId"));
		srvId = WebUtils.checkSrvId(srvId, request);

		// 查找服务器
		ServerModel serverModel = ServerUtils.getServer(srvId, true);
		if (serverModel == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有选择服务器！"); // 不处理
		}

		// 设置服务器名称
		srvId = (int) serverModel.getId();

		// 连接服务器
		IClient client = serverModel.createClient();
		if (client == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "服务器Url配置错误!");
		}
		// 发送消息
		BannedListWCS.Builder msgb = BannedListWCS.newBuilder();
		// 发送并等待回馈
		BannedListWSC retMsg = client.call(Head.H13002, msgb.build(), BannedListWSC.class);
		if (retMsg == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "读取失败:" + client.getErrStr());
		}

		// 提取数据
		List<AccountInfo> list = retMsg.getAccountListList();
		List<Map<String, Object>> lstDatas = new LinkedList<Map<String, Object>>();
		for (AccountInfo info : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", info.getAccount());
			map.put("playerId", info.getPlayerId());
			map.put("playerName", info.getPlayerName());
			map.put("lv", info.getPlayerLv());
			lstDatas.add(map);
		}

		String jsonMap = OperationExt.queryToJson(lstDatas);
		return "@" + jsonMap;
	}

}
