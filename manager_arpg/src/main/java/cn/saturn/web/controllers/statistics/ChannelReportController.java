package cn.saturn.web.controllers.statistics;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.saturn.operation.OperationExt;
import cn.saturn.operation.ReportSqlTemp;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.login.domain.AccountManager;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.controllers.statistics.dao.NextDayTimer;
import cn.saturn.web.controllers.statistics.dao.StatManager;
import cn.saturn.web.controllers.statistics.dao.SubChannelSummaryManager;
import cn.saturn.web.controllers.statistics.dao.SubPlatformManager;
import cn.saturn.web.controllers.statistics.dao.SubPlatformModel;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import zyt.spring.cache.entity.EntityFactorys;

/**
 * @author rodking
 * @ 1-1 渠道汇总表
 *
 */
@Path("channelReport")
public class ChannelReportController {
	@Resource
	javax.sql.DataSource dataSource;
	
	@Resource
	NextDayTimer nextDayTimer;
	
	private static List<String> platforms;

	@MainView
	@LoginCheck
	@Get("total")
	public String channelReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();

		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
		if(platforms == null)
			platforms = AccountManager.getPlatforms();

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/channelReport/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("platforms", platforms);
		request.setAttribute("servers", servers);

		return "channelReportTotal";
	}
	
	@Get("stat")
	public void stat(Invocation inv) throws Throwable {
		nextDayTimer.update();
	}
	
	@MainView
	@LoginCheck
	@Get("ltv")
	public String ltvReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();

		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
		List<String> platforms = AccountManager.getPlatforms();

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/channelReport/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("platforms", platforms);
		request.setAttribute("servers", servers);

		return "ltvReportTotal";
	}
	
	@MainView
	@LoginCheck
	@Get("subChannel")
	public String subChannelReportTotal(Invocation inv, @Param("type") int type) throws Throwable {
        SubChannelSummaryManager channelSummaryManager = ComponentManager.getComponent(SubChannelSummaryManager.class);
        if(!channelSummaryManager.isSummary())
        	channelSummaryManager.summary();
		String dayTime = TimeUtils.getTodayStr();
		
		SubPlatformManager subPlatformManager = ComponentManager.getComponent(SubPlatformManager.class);
		List<SubPlatformModel> subPlatformModels = subPlatformManager.getSubPlatformList();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/channelReport/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("subplatforms", subPlatformModels);

		return "subChannelReport";
	}
	
	@MainView
	@LoginCheck
	@Get("retention")
	public String retentionReportTotal(Invocation inv, @Param("type") int type) throws Throwable {
        SubChannelSummaryManager channelSummaryManager = ComponentManager.getComponent(SubChannelSummaryManager.class);
        if(!channelSummaryManager.isSummary())
        	channelSummaryManager.summary();
		String dayTime = TimeUtils.getTodayStr();
		
		SubPlatformManager subPlatformManager = ComponentManager.getComponent(SubPlatformManager.class);
		List<SubPlatformModel> subPlatformModels = subPlatformManager.getSubPlatformList();
		
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/channelReport/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("subplatforms", subPlatformModels);
		request.setAttribute("servers", servers);

		return "retentionReport";
	}
	
	@MainView
	@LoginCheck
	@Get("total2")
	public String total2(Invocation inv, @Param("type") int type) throws Throwable {
        SubChannelSummaryManager channelSummaryManager = ComponentManager.getComponent(SubChannelSummaryManager.class);
        if(!channelSummaryManager.isSummary())
        	channelSummaryManager.summary();
		String dayTime = TimeUtils.getTodayStr();
		
		SubPlatformManager subPlatformManager = ComponentManager.getComponent(SubPlatformManager.class);
		List<SubPlatformModel> subPlatformModels = subPlatformManager.getSubPlatformList();
		
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/channelReport/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("subplatforms", subPlatformModels);
		request.setAttribute("servers", servers);

		return "channelReportTotal2";
	}
	
	@MainView
	@LoginCheck
	@Get("comp")
	public String compReportTotal(Invocation inv, @Param("type") int type) throws Throwable {
		StatManager statManager = ComponentManager.getComponent(StatManager.class);
		statManager.statComp();
		String dayTime = TimeUtils.getTodayStr();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/channelReport/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);

		return "channelReportTotal";
	}

	@LoginCheck
	@Get("totalInfo")
	public String channelReportTotalInfo(Invocation inv, @Param("type") int type, @Param("channelId") String channelId,
			@Param("srvId") int srvId, @Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("isSelect") int isSelect, @Param("subChannelId") String subChannelId) throws Throwable {
		if (isSelect != 1)
			return "";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		DateParam param = new DateParam();
		String resultTime = "";
		String resultQuery = "";
		resultTime = param.getParam(startTime, endTime);

		if (param.isError(resultTime))
			return resultTime;

		if (type == 2 || type == 7 || type == 11 || type == 12 || type == 16) {
			resultQuery = param.getQuery(startTime, endTime);
			
			
			if (param.isError(resultQuery))
				return resultQuery;
			//System.out.println("resultQuery:"+resultQuery);	
			return "@" + selectTable(template, resultQuery, type, channelId, srvId, resultTime);
		}
		
		if(type == 27){
			SubChannelSummaryManager channelSummaryManager = ComponentManager.getComponent(SubChannelSummaryManager.class);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date endDate = format.parse(endTime);
			
			/*if(channelSummaryManager.isSummary() && DateUtils.isAfterToday(endDate))
				return "@当天数据实时统计，正在努力统计中,请稍后...预计3分钟";*/
			return "@" + selectTable(template, type, subChannelId, startTime, endTime);
		}
		
		if(type == 101){
			return "@" + selectTable(template, type, srvId, startTime, endTime);
		}

		if(type == 102 || type == 6){
			return "@" + selectTable(template, type, startTime, endTime, srvId, subChannelId);
		}

		return "@" + selectTable(template, type, channelId, srvId, resultTime);
	}

	private String selectTable(JdbcTemplate template, int type, String channelId, int serverId, String resultTime) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		
		
		String server_id = serverId == -1 ? "%" : serverId + "";
		String platform = channelId.equals("-1") ? "%" : channelId + "";

		String sql = MessageFormat.format(temp.getSql(), resultTime, server_id, platform);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());

		return OperationExt.queryToJson(temp.getTitle_ch(), out);
	}

	private String selectTable(JdbcTemplate template, String strSql, int type, String channelId, int serverId,
			String resultTime) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		
		String server_id = serverId == -1 ? "%" : serverId + "";
		String platform = channelId.equals("-1") ? "%" : channelId + "";

		String sql = "";

		String server_id2 = serverId == -1 ? " like '-100' " : " like '" + serverId + "' ";
		if (type == 2 || type == 6 || type == 12)
			sql = MessageFormat.format(temp.getSql(), strSql, resultTime, server_id, platform, server_id2);
		else
			sql = MessageFormat.format(temp.getSql(), strSql, resultTime, server_id, platform);
		//System.out.println(sql);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());

		
		return OperationExt.queryToJson(temp.getTitle_ch(), out);
	}
	
	private String selectTable(JdbcTemplate template, int type, String subChannelId, String startTime, String endTime) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String platform = subChannelId.equals("-1") ? "%" : subChannelId + "";
		String sql = MessageFormat.format(temp.getSql(), platform, startTime, endTime);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
        String result = OperationExt.queryToJson(temp.getTitle_ch(), out);
		return result;
	}
	
	private String selectTable(JdbcTemplate template, int type, String startTime, String endTime, int serverId, String subChannelId) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String platform = subChannelId.equals("-1") ? "%" : subChannelId + "";
		String server_id = serverId == -1 ? "%" : serverId + "";
		String sql = MessageFormat.format(temp.getSql(), startTime, endTime, server_id, platform);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
        String result = OperationExt.queryToJson(temp.getTitle_ch(), out);
		return result;
	}
	
	private String selectTable(JdbcTemplate template, int type, int serverId, String startTime, String endTime) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String server_id = serverId == -1 ? "%" : serverId + "";
		String sql = MessageFormat.format(temp.getSql(), server_id, startTime, endTime);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		
		List<String> titleList = new ArrayList<>();
		titleList.add("服务器");
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = dateFormat.parse(startTime);
			Date endDate = dateFormat.parse(endTime);
			Date theDate = new Date(startDate.getTime());
			titleList.add(startTime);
			while(theDate.before(endDate)){
				theDate = new Date(theDate.getTime()+86400000l);
				if(theDate.before(endDate)){
					titleList.add(dateFormat.format(theDate));
				}
			}
			if(!endDate.equals(startDate))
				titleList.add(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = null;
	    if(out == null || out.size()==0)
	    	result = OperationExt.queryToJson(titleList.toArray(new String[titleList.size()]), out);
	    else{
	    	List<String[]> newOut = new ArrayList<>();
	    	ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
	    	List<ServerModel> servers = serverComponent.getDAO().getList();
	    	String[] data;
	    	for(ServerModel model : servers){
	    		data = new String[titleList.size()];
	    		for(int i = 0; i<titleList.size(); i++){
	    			if("服务器".equals(titleList.get(i))){
	    				data[i] = model.getName();
	    			}else{
	    				for(String[] oldData: out){
	    					if(titleList.get(i).equals(oldData[0]) && (model.getId()+"").equals(oldData[1])){
	    						data[i] = "注册:"+oldData[2]+",付费:"+oldData[3]+",金额:"+oldData[4];
	    					}
	    				}
	    				if(data[i] == null)
	    					data[i] = "注册:"+0+",付费:"+0+",金额:"+0;
	    			}
	    		}
	    		newOut.add(data);
	    	}
	    	result = OperationExt.queryToJson(titleList.toArray(new String[titleList.size()]), newOut);
	    }
		return result;
	}

}
