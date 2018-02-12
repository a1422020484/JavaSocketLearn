package cn.saturn.web.controllers.statistics;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import cn.saturn.web.controllers.server.dao.ServerMergeDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeModel;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.controllers.statistics.dao.ServerReport;
import cn.saturn.web.controllers.statistics.dao.SubChannelSummaryManager;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import zyt.spring.cache.entity.EntityFactorys;

@Path("serverReport")
public class ServerReportController {

	@Resource
	javax.sql.DataSource dataSource;

	@Resource
	ServerMergeDAO serverMergeDAO;

	@MainView
	@LoginCheck
	@Get("total")
	public String serverReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();

		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);

		List<String> platforms = AccountManager.getPlatforms();

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();
		// List<ServerMergeModel> servers =serverMergeDAO.getAllServerMerge();
		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/serverReport/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("platforms", platforms);
		request.setAttribute("servers", servers);

		return "serverReport";
	}

	@LoginCheck
	@Get("totalInfo")
	public String serverReportTotalInfo(Invocation inv, @Param("type") int type, @Param("channelId") String channelId, @Param("srvId") int srvId, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("isSelect") int isSelect, @Param("subChannelId") String subChannelId) throws Throwable {
		if (isSelect != 1)
			return "";
		
		JdbcTemplate template = new JdbcTemplate(dataSource);
		DateParam param = new DateParam();
		String startTimeHms = "";
		String resultTimeHms = "";
		
		
		startTimeHms = TimeUtils.getParamHmsst(startTime);
		resultTimeHms = param.getParamHms(startTime, endTime);
		
		if (param.isError(resultTimeHms))
			return resultTimeHms;

		return "@" + selectTable(template, type, channelId, srvId,  startTimeHms,  resultTimeHms);
	}

	private String selectTable(JdbcTemplate template, int type, String channelId,int serverId, String startTimeHms, String resultTimeHms){
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		
		String server_id = (serverId == -1) ? "%" : serverId +"";
		String platform = channelId.equals("-1") ? "%" : channelId + "";
		
		String sql = "";

		sql = MessageFormat.format(temp.getSql(),resultTimeHms,server_id ,platform ,startTimeHms);
		//System.out.println(sql);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		
		return OperationExt.queryToJson(temp.getTitle_ch(), out);
		
		
		
		//return OperationExt.queryToJson(cnStr, outer);
		}	
		
		
	
}
