package cn.saturn.web.controllers.statistics;

import java.text.MessageFormat;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.saturn.operation.OperationExt;
import cn.saturn.operation.ReportSqlTemp;
import cn.saturn.operation.TimeUtils;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.code.LoginOperation;
import cn.saturn.web.controllers.DataSourceOperation;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.controllers.statistics.dao.PlatformDAO;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

/**
 * @author rodking
 * @ 1-1 渠道汇总表
 *
 */
@Path("channelReportNew")
public class ChannelReportNewController {
	@Resource
	javax.sql.DataSource dataSource;

	@Resource
	PlatformDAO platformDAO;

	@MainView
	@LoginCheck
	@Get("total")
	public String channelReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/channelReportNew/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("servers", servers);

		return "channelReportTotalNew";
	}

	@Get("totalInfo")
	public String channelReportTotalInfo(Invocation inv, @Param("type") int type, @Param("platform") String platform,
			@Param("subPlatform") String subPlatform, @Param("srvId") int srvId, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("isSelect") int isSelect) throws Throwable {

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

		return "@" + selectTable(template, resultQuery, type, platform, subPlatform, srvId, resultTime);
	}

	private String selectTable(JdbcTemplate template, String strSql, int type, String platform, String subPlatform,
			int serverId, String resultTime) {
		ReportSqlTemp temp = DataSourceOperation.queryTempSql(type);

		String server_id = serverId == -1 ? "%" : serverId + "";
		platform = platform.equals("-1") ? "%" : platform + "";

		String sql = "";

		String server_id2 = serverId == -1 ? " like '-100' " : " like '" + serverId + "' ";
		sql = MessageFormat.format(temp.getSql(), strSql, resultTime, server_id, platform, subPlatform, server_id2);

		System.out.println(sql);

		List<String[]> out = LoginOperation.query(sql, temp.getTitle_en());

		temp = DataSourceOperation.queryTempSql(type + 1);
		server_id = serverId == -1 ? "%" : serverId + "";
		platform = platform.equals("-1") ? "%" : platform + "";
		server_id2 = serverId == -1 ? " like '-100' " : " like '" + serverId + "' ";
		sql = MessageFormat.format(temp.getSql(), strSql, resultTime, server_id, platform, subPlatform, server_id2);
		out = DataSourceOperation.query(sql, temp.getTitle_en());

		return OperationExt.queryToJson(temp.getTitle_ch(), out);
	}
}
