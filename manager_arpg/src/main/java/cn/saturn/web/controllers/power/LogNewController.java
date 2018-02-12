package cn.saturn.web.controllers.power;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.ProtocolWeb;
import proto.ProtocolWeb.GetLogsSC;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.power.dao.LogNewDAO;
import cn.saturn.web.controllers.power.dao.LogNewModel;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;

@Path("log2")
public class LogNewController {
	
	/*@Resource
	javax.sql.DataSource logmainDB;*/
	
	@Resource
	LogNewDAO logNewDAO;
	
	public static final String defualPath = "/power/log2/show";

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

		return "log2";
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

		//int type1Index = Utils.getIntParameter(inv, "type1");
		// String type1 = type1s[type1Index];
		String type1 = request.getParameter("type1");
		String type2 = request.getParameter("type2");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		StringBuffer url = new StringBuffer();
		url.append("/power/log2/listJson");
		url.append("?srvId=" + srvId);
		url.append("&playerText=" + playerText);
		url.append("&type1=" + type1);
		url.append("&type2=" + type2);
		url.append("&startTime=" + startTime);
		url.append("&endTime=" + endTime);

		// 清除数量
		Utils.setSessionValue(inv, "logTR", 0);

		// tableurl
		String tableUrl = Utils.url(inv, url.toString());
		request.setAttribute("tableUrl", tableUrl);
		
		return "log2_list";
	}

	@UserAuthority(PageModel.power_log)
	@Post("listJson")
	public String listJson(Invocation inv) throws Throwable {
		
		//JdbcTemplate template = new JdbcTemplate(logmainDB);
		
		HttpServletRequest request = inv.getRequest();

		// 获取参数
		Integer srvId = Integer.valueOf(request.getParameter("srvId"));
		String playerText = request.getParameter("playerText");
		int player_id=Integer.valueOf(playerText);
		//int type1Index = Utils.getIntParameter(inv, "type1Id");
		/*String[] types = logType.getTypes0();
		String type3 = request.getParameter("type1");
		//String type1 = (type1Index != 0) ? types[type1Index] : "";
		String type1 = (request.getParameter("type1").trim() !=null)?request.getParameter("type1").trim():"%";
		String type2 = (request.getParameter("type2").trim()!=null)?request.getParameter("type2").trim():"%";*/
		
		String startTimeStrT = request.getParameter("startTime");
		String endTimeStrT = request.getParameter("endTime");
		
		// 解析时间
		String startTimeStr =startTimeStrT.replace("T", " ");
		String endTimeStr =endTimeStrT.replace("T", " ");
		Date startTime=TimeUtils.getTDate(startTimeStr);
		Date endTime=TimeUtils.getTDate(endTimeStr);
		// 读取记录的数量
		//Integer totalRows1 = Utils.getSessionValue(inv, "logTR", Integer.class);
		//int totalRows0 = (totalRows1 != null) ? totalRows1 : 0;
		//int totalRows = totalRows0;
		// 读取参数
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		// 计算起始和结束
		int startRow = BsgridUtils.startRow(curPage, pageSize);
		// int endRow = BsgridUtils.endRow(curPage, pageSize, totalRows);

		
		String srvIdstr=String.valueOf(srvId);
		String table="log_"+srvIdstr;
		//List<LogNewModel> LogNewModelList=logNewDAO.getList(table);
		List<LogNewModel> LogNewModelList=logNewDAO.getList(srvId,player_id,startTime,endTime,startRow,pageSize);
		
		int count=logNewDAO.getCount(srvId,player_id,startTime,endTime);
		
		//int count=logNewDAO.getCount();
		
		if (LogNewModelList == null) {
			return "@" + BsgridUtils.emptyJson;
		}
		
		
		// 转换接口
		BsgridUtils.IConvert<LogNewModel> action = new BsgridUtils.IConvert<LogNewModel>() {
			@Override
			public boolean convert(LogNewModel obj, Map<String, Object> map) {
				map.put("id", obj.getId());
				map.put("playerId", obj.getPlayer_id());
				map.put("playerName", obj.getName());
				map.put("type1", obj.getType1());
				map.put("type2", obj.getType2());
				map.put("logTime", obj.getLog_time());
				map.put("crysta", obj.getCrystal());
				map.put("gold", obj.getGold());
				map.put("action_power", obj.getAction_power());
				map.put("level", obj.getLevel());
				map.put("vip_level", obj.getVip_level());

				//字符过滤
				String cstr = obj.getContent();
				cstr = cstr.replace("\t", "\\t");
				
				map.put("content", cstr);
				// System.err.println(obj.getId() + " " + cstr);
				return true;
			}
		};
		// 转换数据
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(LogNewModelList, action);

		// 生成json
		String jsonStr = BsgridUtils.jsonStr(count, curPage, pageSize, listDatas);

		return "@" + jsonStr;
	}
}
