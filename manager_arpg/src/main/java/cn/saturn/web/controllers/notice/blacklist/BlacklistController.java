package cn.saturn.web.controllers.notice.blacklist;

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
import proto.Protocol.PlayerInfo;
import proto.ProtocolWeb.GetBlacklistListWCS;
import proto.ProtocolWeb.GetBlacklistListWSC;
import proto.ProtocolWeb.SetBlacklistWCS;

@Path("")
public class BlacklistController {

	@Get("")
	public String index(Invocation inv) {
		// return "r:blacklist/list";
		return Utils.redirect(inv, "/notice/blacklist/list");
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.black_page)
	@Get("list")
	public String blacklist(Invocation inv, @Param("srvId") int srvId) throws Throwable {
		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();
		srvId = WebUtils.checkSrvId(srvId, request);
		WebUtils.setServerSelected(request, srvId); // 切换选择的服务器

		// 查找服务器
		ServerModel serverModel = ServerUtils.getServer(srvId, true);
		if (serverModel == null) {
			request.setAttribute("curSrvId", 0);
			request.setAttribute("srvCode", "error");
			return "blacklist"; // 不处理
		}
		// 设置服务器名称
		srvId = (int) serverModel.getId();
		request.setAttribute("curSrvId", srvId);
		request.setAttribute("srvCode", serverModel.getSrvStr0());
		return "blacklist";
	}

	@UserAuthority(PageModel.black_page)
	@Get("add")
	public String add(Invocation inv, @NotBlank @Param("srvId") int srvId) {
		inv.addFlash("postUrl", Utils.url(inv, "/notice/blacklist/toAdd"));
		return Utils.redirect(inv, "/main/userDialog?srvId=" + srvId);
	}

	@LoginCheck
	@UserAuthority(PageModel.black_page)
	@Post("toAdd")
	public String toAdd(Invocation inv, @NotBlank @Param("srvId") int srvId, @NotBlank @Param("playerId") int playerId) {
		// 检测服务器ID
		if (srvId <= 0) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "未知服务器ID");
		}

		// 连接服务器
		IClient client = ServerUtils.createClient(srvId);
		if (client == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器错误");
		}

		// 发送消息
		SetBlacklistWCS.Builder msgb = SetBlacklistWCS.newBuilder();
		msgb.setPlayerId(playerId);
		msgb.setEnable(1);

		// 发送并等待回馈
		GetBlacklistListWSC retMsg = client.call(Head.H12102, msgb.build(), GetBlacklistListWSC.class);
		if (retMsg == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, client.getErrStr());
		}

		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "提交成功！");
	}

	@LoginCheck
	@UserAuthority(PageModel.black_page)
	@Get("toRemove")
	public String toRemove(Invocation inv, @NotBlank @Param("srvId") int srvId, @NotBlank @Param("playerId") int playerId) {
		// 检测服务器ID
		if (srvId <= 0) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "未知服务器ID");
		}

		// 连接服务器
		IClient client = ServerUtils.createClient(srvId);
		if (client == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器错误");
		}

		// 发送消息
		SetBlacklistWCS.Builder msgb = SetBlacklistWCS.newBuilder();
		msgb.setPlayerId(playerId);
		msgb.setEnable(0);

		// 发送并等待回馈
		GetBlacklistListWSC retMsg = client.call(Head.H12102, msgb.build(), GetBlacklistListWSC.class);
		if (retMsg == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR,  client.getErrStr());
		}

		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功！");
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
		GetBlacklistListWCS.Builder msgb = GetBlacklistListWCS.newBuilder();
		// 发送并等待回馈
		GetBlacklistListWSC retMsg = client.call(Head.H12101, msgb.build(), GetBlacklistListWSC.class);
		if (retMsg == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "读取失败:" + client.getErrStr());
		}
		
		// 提取数据
		List<PlayerInfo> list = retMsg.getPlayerListList();
		List<Map<String,Object>> lstDatas = new LinkedList<Map<String,Object>>();
		for(PlayerInfo info:list)
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id",info.getId());
			map.put("name",info.getName());
			map.put("lv",info.getLv());
			map.put("vip",info.getVipLv());
			map.put("fight",info.getFightingCapacity());
			lstDatas.add(map);
		}
		String jsonMap = OperationExt.queryToJson(lstDatas);
		return "@"+jsonMap;
	}

}
