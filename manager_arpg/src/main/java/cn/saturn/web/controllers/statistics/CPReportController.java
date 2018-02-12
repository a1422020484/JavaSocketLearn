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
 * @author @ 1-1 子渠道汇总表(查看单个渠道信息)
 *
 */
@Path("cpReport")
public class CPReportController {
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
		if (platforms == null)
			platforms = AccountManager.getPlatforms();

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/cpReport/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("platforms", platforms);
		request.setAttribute("servers", servers);

		return "cpReportTotal";
	}

	@LoginCheck
	@Get("totalInfo")
	public String channelReportTotalInfo(Invocation inv, @Param("type") int type,
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

		resultQuery = param.getQuery(startTime, endTime);
	
		if (param.isError(resultQuery))
			return resultQuery;
		
			//System.out.println("resultQuery:"+resultQuery);	
		return "@" + selectTable(template, resultQuery, type,srvId, resultTime);
	}
	private String selectTable(JdbcTemplate template, String strSql, int type, int serverId,
			String resultTime) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);

		String server_id = serverId == -1 ? "%" : serverId + "";
		String platform =  "%" ;

		String sql = "";

		String server_id2 = serverId == -1 ? " like '-100' " : " like '" + serverId + "' ";
		
		sql = MessageFormat.format(temp.getSql(), strSql, resultTime, server_id, platform, server_id2);
		
		//System.out.println(sql);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());

		return OperationExt.queryToJson(temp.getTitle_ch(), out);
	}

}
