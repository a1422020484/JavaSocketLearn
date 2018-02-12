package cn.saturn.web.controllers.power;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.ProtocolWeb;
import proto.ProtocolWeb.GetLogsSC;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;

@Path("log")
public class LogController {
	public static final String defualPath = "/power/log/show";
	// public static final String[] type1s = { "全部", "系统", "建筑" };
	public static final LogType logType = new LogType();

	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, defualPath);
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.power_log)
	@Get("show")
	public String show(Invocation inv, @Param("srvId") int srvId) throws Throwable {

		// 设置当前日期 2015-01-01T00:00
		long nowTimeL = System.currentTimeMillis();
		long startTimeL = nowTimeL - 30 * WebUtils.Html.oneDayTimeL;
		long endTimeL = nowTimeL + WebUtils.Html.oneDayTimeL;
		String startTimeStr = WebUtils.Html.getDateTimeStr(startTimeL);
		String endTimeStr = WebUtils.Html.getDateTimeStr(endTimeL);

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("startTime", startTimeStr);
		request.setAttribute("endTime", endTimeStr);

		return "log";
	}
	
	@MainView
	@UserAuthority(PageModel.power_log)
	@Get("toCheck01")
	public String toCheck01(Invocation inv, @Param("srvId") int srvId, @Param("playerText") String playerText) throws Throwable {
		
		if(srvId<=0)
		{
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器id不为空");
		}
		
		if(StringUtils.isEmpty(playerText))
		{
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "接受者不为空");
		}
		
		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "请求成功");
	}

	@MainView
	@UserAuthority(PageModel.power_log)
	@Get("toCheck")
	public String toCheck(Invocation inv, @Param("srvId") int srvId, @Param("playerText") String playerText) throws Throwable {
		
		if(srvId<=0)
		{
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器id不为空");
		}
		
		if(StringUtils.isEmpty(playerText))
		{
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "接受者不为空");
		}
		
		HttpServletRequest request = inv.getRequest();

		int type1Index = Utils.getIntParameter(inv, "type1");
		// String type1 = type1s[type1Index];
		String type2 = request.getParameter("type2");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		StringBuffer url = new StringBuffer();
		url.append("/power/log/listJson");
		url.append("?srvId=" + srvId);
		url.append("&playerText=" + playerText);
		url.append("&type1Id=" + type1Index);
		url.append("&type2=" + type2);
		url.append("&startTime=" + startTime);
		url.append("&endTime=" + endTime);

		// 清除数量
		Utils.setSessionValue(inv, "logTR", 0);

		// tableurl
		String tableUrl = Utils.url(inv, url.toString());
		request.setAttribute("tableUrl", tableUrl);
		
		return "log_list";
	}

	@UserAuthority(PageModel.power_log)
	@Post("listJson")
	public String listJson(Invocation inv) throws Throwable {
		HttpServletRequest request = inv.getRequest();

		// 获取参数
		Integer srvId = Integer.valueOf(request.getParameter("srvId"));
		String playerText = request.getParameter("playerText");
		int type1Index = Utils.getIntParameter(inv, "type1Id");
		String[] types = logType.getTypes0();
		String type1 = (type1Index != 0) ? types[type1Index] : "";
		String type2 = request.getParameter("type2");
		String startTimeStr = request.getParameter("startTime");
		String endTimeStr = request.getParameter("endTime");

		// 解析时间
		long startTimeL = 0L;
		long endTimeL = 0L;
		int startTime = 0;
		int endTime = 0;
		try {
			startTimeL = WebUtils.Html.getDateTimeLoacl(startTimeStr);
			endTimeL = WebUtils.Html.getDateTimeLoacl(endTimeStr);
			startTime = (int) ((long) (startTimeL * 0.001f));
			endTime = (int) ((long) (endTimeL * 0.001f));
		} catch (Exception ex) {
		}

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

		// 读取记录的数量
		Integer totalRows1 = Utils.getSessionValue(inv, "logTR", Integer.class);
		int totalRows0 = (totalRows1 != null) ? totalRows1 : 0;
		int totalRows = totalRows0;
		// 读取参数
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		// 计算起始和结束
		int startRow = BsgridUtils.startRow(curPage, pageSize);
		// int endRow = BsgridUtils.endRow(curPage, pageSize, totalRows);

		// 发送请求
		ProtocolWeb.GetLogsCS.Builder b = ProtocolWeb.GetLogsCS.newBuilder();
		b.setPlayerText(playerText);
		b.setType1(type1);
		b.setType2(type2);
		b.setCount(pageSize);
		b.setLimit(startRow);
		b.setStartTime(startTime);
		b.setEndTime(endTime);
		if (totalRows <= 0) {
			b.setCheckMaxCount(true); // 读取数量
		}
		ProtocolWeb.GetLogsCS msgcs = b.build();

		// 发送并等待回馈
		GetLogsSC retMsg = client.call(Head.H14001, msgcs, GetLogsSC.class);
		if (retMsg == null) {
			return "@" + BsgridUtils.emptyJson;
		}

		// 遍历
		List<ProtocolWeb.LogItem> list = retMsg.getLogsList();
		int maxCount = retMsg.getMaxCount();
		totalRows = (maxCount > 0) ? maxCount : totalRows;
		if (totalRows != totalRows0) {
			Utils.setSessionValue(inv, "logTR", totalRows);
		}

		// 转换接口
		BsgridUtils.IConvert<ProtocolWeb.LogItem> action = new BsgridUtils.IConvert<ProtocolWeb.LogItem>() {
			@Override
			public boolean convert(ProtocolWeb.LogItem obj, Map<String, Object> map) {
				map.put("id", obj.getId());
				map.put("playerId", obj.getPlayerId());
				map.put("playerName", obj.getName());
				map.put("type1", obj.getType1());
				map.put("type2", obj.getType2());
				map.put("logTime", obj.getLogTime());
				map.put("crysta", obj.getCrysta());
				map.put("gold", obj.getGold());
				map.put("action_power", obj.getActionPower());
				map.put("level", obj.getLevel());
				map.put("vip_level", obj.getVipLevel());

				//字符过滤
				String cstr = obj.getContent();
				cstr = cstr.replace("\t", "\\t");
				
				map.put("content", cstr);
				// System.err.println(obj.getId() + " " + cstr);
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
