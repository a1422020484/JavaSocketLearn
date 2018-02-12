package cn.saturn.web.controllers.statistics;


import java.text.MessageFormat;
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
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import zyt.spring.cache.entity.EntityFactorys;

/**
 * @author 
 * @ 1-1 留存
 *
 */
@Path("retentionplatform")
public class RetenReportController {
	@Resource
	javax.sql.DataSource dataSource;
	
	@Resource
	NextDayTimer nextDayTimer;
	
	@MainView
	@LoginCheck
	@Get("retention")
	public String retentionReportTotal(Invocation inv, @Param("type") int type) throws Throwable {
    
		String dayTime = TimeUtils.getTodayStr();
		
		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
		List<String> platforms = AccountManager.getPlatforms();
		
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/retentionplatform/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("platforms", platforms);
		request.setAttribute("servers", servers);

		return "retentionplatform";
	}
	
	@LoginCheck
	@Get("totalInfo")
	public String channelReportTotalInfo(Invocation inv, @Param("type") int type, @Param("platform") String platform,
			 @Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("isSelect") int isSelect, @Param("subChannelId") String subChannelId) throws Throwable {
		if (isSelect != 1)
			return "";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		DateParam param = new DateParam();
		String resultTime = "";
		resultTime = param.getParam(startTime, endTime);

		if (param.isError(resultTime))
			return resultTime;

		return "@" + selectTable(template, type, startTime, endTime, platform);
		
	}

	private String selectTable(JdbcTemplate template, int type, String startTime, String endTime, String platform) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String platformStr = platform.equals("-1") ? "%" : platform + "";

		//String sql = MessageFormat.format(temp.getSql(), startTime, endTime, server_id, platform);
		String sql = MessageFormat.format(temp.getSql(), startTime, endTime, platformStr);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
        String result = OperationExt.queryToJson(temp.getTitle_ch(), out);
		return result;
	}
	
	

}
