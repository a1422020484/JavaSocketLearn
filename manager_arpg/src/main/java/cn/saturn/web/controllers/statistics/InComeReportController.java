package cn.saturn.web.controllers.statistics;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.login.domain.AccountManager;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.utils.ReportTools;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import zyt.spring.cache.entity.EntityFactorys;

/**
 * @author rodking
 * @ 收入查询
 *
 */
@Path("inComeReport")
public class InComeReportController {
	@Resource
	javax.sql.DataSource dataSource;

	@MainView
	@LoginCheck
	@Get("total")
	public String channelReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();
		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
		List<String> platforms = AccountManager.getPlatforms();
		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/inComeReport/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("platforms", platforms);
		return "inComeReport";
	}

	@Get("totalInfo")
	public String channelReportTotalInfo(Invocation inv, @Param("type") int type, @Param("channelId") String channelId,
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("isSelect") int isSelect) throws Throwable {

		if (isSelect != 1)
			return "";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		
		DateParam param = new DateParam();
		String resultTime = "";
		String resultQuery = "";
		resultTime = param.getParam(startTime, endTime);

		
		if (param.isError(resultTime))
			return resultTime;

		if (type == 13) {
			resultQuery = param.getQuery(startTime, endTime);

			if (param.isError(resultQuery))
				return resultQuery;

			return "@" + ReportTools.select2DB(template, resultQuery, type, channelId, resultTime);
		}
		

		return "@" + ReportTools.select2DB(template, type, channelId, resultTime);
	}
}
