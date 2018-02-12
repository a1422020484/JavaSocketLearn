package cn.saturn.web.controllers.statistics;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.saturn.operation.OperationExt;
import cn.saturn.operation.ReportSqlTemp;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeModel;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("activityReport")
public class ActivityReportController {

	
	@Resource
	javax.sql.DataSource dataSource;
	
	@Resource
	ServerDAO serverDAO;
	
	@MainView
	@LoginCheck
	@Get("total")
	public String serverReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();
		List<ServerModel> servers = serverDAO.getList();
		
		//List<ServerMergeModel>  servers =serverMergeDAO.getAllServerMerge();
		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/activityReport/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("servers", servers);

		return "activityreport";
	}
	
	@LoginCheck
	@Get("totalInfo")
	public String serverReportTotalInfo(Invocation inv,@Param("type") int type,@Param("srvId") int srvId,
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("isSelect") int isSelect) throws Throwable {

		if (isSelect != 1)
			return "";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		DateParam param = new DateParam();
		String resultTime = "";
		resultTime = param.getParamHms(startTime, endTime);

		if (param.isError(resultTime))
			return resultTime;

		//return "@" + selectTable(template, type, channelId, srvId, resultTime);
		return "@" + selectTable(template, type, srvId,resultTime);
	}

	
	private String selectTable(JdbcTemplate template,  int type, int serverId,String resultTime) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		
		String server_id = serverId == -1 ? "%" : serverId + "";
	
		String sql = "";

		sql = MessageFormat.format(temp.getSql(),server_id ,resultTime);
		//System.out.println(sql);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		
		return OperationExt.queryToJson(temp.getTitle_ch(), out);
	}

}
