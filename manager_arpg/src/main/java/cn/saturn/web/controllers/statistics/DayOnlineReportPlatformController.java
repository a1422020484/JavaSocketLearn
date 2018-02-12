package cn.saturn.web.controllers.statistics;

import java.text.MessageFormat;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.saturn.operation.OperationExt;
import cn.saturn.operation.ReportSqlTemp;
import cn.saturn.web.code.login.domain.AccountManager;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import zyt.spring.cache.entity.EntityFactorys;

/**
 * @author rodking
 * @ 1-1 
 *
 */
@Path("dayOnlineReportplatform")
public class DayOnlineReportPlatformController {
	@Resource
	javax.sql.DataSource dataSource;

	@MainView
	@LoginCheck
	@Get("total")
	public String dayOnlineReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
		List<String> platforms = AccountManager.getPlatforms();

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/dayOnlineReportplatform/totalInfo?type=" + type));
		request.setAttribute("platforms", platforms);
		request.setAttribute("servers", servers);

		return "dayOnlineReportplatform";
	}

	@Get("totalInfo")
	public String dayOnlineReportTotalInfo(Invocation inv, @Param("type") int type,
			@Param("srvId") int srvId,@Param("isSelect") int isSelect) throws Throwable {

		if (isSelect != 1)
			return "";
		DateParam param = new DateParam();
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String nowDayStr= DateUtils.getNowDay();
		String resultTime = param.getParam(nowDayStr, nowDayStr);
		String resultQuery = param.getQuery(nowDayStr, nowDayStr);
		
		return "@" + selectTable(template, type, srvId, resultTime,resultQuery);
	}

	private String selectTable(JdbcTemplate template, int type, int serverId, String resultTime,String resultQuery) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);

		String server_id = serverId == -1 ? "%" : serverId + "";
		
		String sql = MessageFormat.format(temp.getSql(), resultQuery,resultTime, server_id);
		//System.out.println(sql);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		return OperationExt.queryToJson(temp.getTitle_ch(), out);
	}
}
