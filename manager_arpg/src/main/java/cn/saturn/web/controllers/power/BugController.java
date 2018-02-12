package cn.saturn.web.controllers.power;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.Protocol.PlayerInfo;
import proto.ProtocolWeb;
import proto.ProtocolWeb.DisBugSC;
import proto.ProtocolWeb.GetBugSC;
import proto.ProtocolWeb.RemoveBugSC;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;

@Path("bug")
public class BugController {
	public static final String defualPath = "/power/bug/show";

	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, defualPath);
	}

	@MainView
	@LoginCheck	
	@UserAuthority(PageModel.bug_page)
	@Get("show")
	public String show(Invocation inv, @Param("srvId") int srvId) throws Throwable {

		// 设置当前日期 2015-01-01T00:00
		long nowTimeL = System.currentTimeMillis();
		long startTimeL = nowTimeL - 3 * WebUtils.Html.oneDayTimeL;
		long endTimeL = nowTimeL + WebUtils.Html.oneDayTimeL;
		String startTimeStr = WebUtils.Html.getDateTimeStr(startTimeL);
		String endTimeStr = WebUtils.Html.getDateTimeStr(endTimeL);

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("startTime", startTimeStr);
		request.setAttribute("endTime", endTimeStr);

		return "bug";
	}

	@MainView
	@UserAuthority(PageModel.bug_page)
	@Get("toCheck")
	public String toCheck(Invocation inv, @NotBlank @Param("srvId") int srvId) throws Throwable {
		HttpServletRequest request = inv.getRequest();

		StringBuffer url = new StringBuffer();
		url.append("/power/bug/listJson");
		url.append("?srvId=" + srvId);

		// tableurl
		String tableUrl = Utils.url(inv, url.toString());
		request.setAttribute("srvId", srvId);
		request.setAttribute("tableUrl", tableUrl);
		request.setAttribute("deleteUrl", Utils.url(inv, "/power/bug/toRemove?srvId=" + srvId));
		request.setAttribute("editUrl", Utils.url(inv, "/power/bug/toEdit?srvId=" + srvId));
		
		return "bug_list";
	}

	@UserAuthority(PageModel.bug_page)
	@Get("toEdit")
	public String toEdit(Invocation inv, @NotBlank @Param("srvId") int srvId, @NotBlank @Param("id") int id, @NotBlank @Param("disStr") String disStr) throws Throwable {
		String url = Utils.url(inv, "/power/bug/toCheck?srvId=" + srvId);

		// 检测服务器ID
		if (srvId <= 0) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "找不到服务器ID");
		}
		// 连接服务器
		IClient client = ServerUtils.createClient(srvId);
		if (client == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "找不到服务器");
		}

		// 访问删除
		ProtocolWeb.DisBugCS.Builder b = ProtocolWeb.DisBugCS.newBuilder();
		b.setId(id);
		b.setDisStr(disStr);
		ProtocolWeb.DisBugCS msgcs = b.build();

		// 发送并等待回馈
		DisBugSC retMsg = client.call(Head.H14102, msgcs, DisBugSC.class);
		if (retMsg == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "处理失败");
		}

		// 跳转
		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "处理成功",url+"&mainView=true");
	}

	@UserAuthority(PageModel.bug_page)
	@Get("toRemove")
	public String toCheck(Invocation inv, @NotBlank @Param("srvId") int srvId, @NotBlank @Param("id") int id) throws Throwable {
		String url = Utils.url(inv, "/power/bug/toCheck?srvId=" + srvId);

		// 检测服务器ID
		if (srvId <= 0) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "找不到服务器ID");
		}
		// 连接服务器
		IClient client = ServerUtils.createClient(srvId);
		if (client == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "找不到服务器");
		}

		// 访问删除
		ProtocolWeb.RemoveBugCS.Builder b = ProtocolWeb.RemoveBugCS.newBuilder();
		b.setId(id);
		ProtocolWeb.RemoveBugCS msgcs = b.build();

		// 发送并等待回馈
		RemoveBugSC retMsg = client.call(Head.H14103, msgcs, RemoveBugSC.class);
		if (retMsg == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "删除失败");
		}

		// 跳转
		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功",url+"&mainView=true");
	}
	
	@Post("listJson")
	public String listJson(Invocation inv) throws Throwable {
		HttpServletRequest request = inv.getRequest();

		// 获取参数
		Integer srvId = Integer.valueOf(request.getParameter("srvId"));

		// 网络访问数据
		// 检测服务器ID
		if (srvId <= 0) {
			return "@" + BsgridUtils.emptyJson;
		}
		// 连接服务器
		IClient client = ServerUtils.createClient(srvId);
		if (client == null) {
			return "@" + BsgridUtils.emptyJson;
		}

		// return "@" + BsgridUtils.emptyJson;
		// 读取参数
		int totalRows = -1;
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		// 计算起始和结束
		int startRow = BsgridUtils.startRow(curPage, pageSize);
		// int endRow = BsgridUtils.endRow(curPage, pageSize, totalRows);

		// 发送请求
		ProtocolWeb.GetBugCS.Builder b = ProtocolWeb.GetBugCS.newBuilder();
		b.setCount(pageSize);
		b.setStart(startRow);
		if (totalRows < 0) {
			b.setCheckMaxCount(true);
			totalRows = 0;
		}
		ProtocolWeb.GetBugCS msgcs = b.build();

		// 发送并等待回馈
		GetBugSC retMsg = client.call(Head.H14101, msgcs, GetBugSC.class);
		if (retMsg == null) {
			return "@" + BsgridUtils.emptyJson;
		}

		// 遍历
		List<ProtocolWeb.BugItem> list = retMsg.getBugsList();
		int maxCount = retMsg.getMaxCount();
		totalRows = (maxCount > 0) ? maxCount : totalRows;

		// 转换接口
		BsgridUtils.IConvert<ProtocolWeb.BugItem> action = new BsgridUtils.IConvert<ProtocolWeb.BugItem>() {
			@Override
			public boolean convert(ProtocolWeb.BugItem obj, Map<String, Object> map) {
				map.put("id", obj.getId());
				map.put("playerId", obj.getPlayerId());
				map.put("time", obj.getTime());

				String bufStr = obj.getBugStr();
				// bufStr = JSON.toJSONString(bufStr);
				// bufStr = bufStr.substring(0, 1);
				map.put("bugStr", bufStr);

				// 处理信息
				map.put("dispose", obj.getIsDispose());
				map.put("dispose0", (obj.getIsDispose() == 0) ? "未处理" : "已处理");
				map.put("disStr", obj.getDisStr());
				map.put("disTime", obj.getDisTime());

				// player info
				PlayerInfo playerInfo = obj.getPlayerInfo();
				map.put("playerName", playerInfo.getName());

				return true;
			}
		};
		// 转换数据
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list, action);

		// 生成json
		String jsonStr = BsgridUtils.jsonStr(totalRows, curPage, pageSize, listDatas);

		return "@" + jsonStr;
	}
}
