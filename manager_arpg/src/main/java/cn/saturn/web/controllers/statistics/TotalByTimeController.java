package cn.saturn.web.controllers.statistics;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.saturn.operation.TimeUtils;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.utils.ReportTools;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

/**
 * @author rodking
 * @desc 2-2 新服务器活跃
 */
@Path("totalByTime")
public class TotalByTimeController {
	@Resource
	javax.sql.DataSource dataSource;

	@MainView
	@LoginCheck
	@Get("total")
	public String channelReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();
		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/totalByTime/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		return "totalByTime";
	}

	@Get("totalInfo")
	public String channelReportTotalInfo(Invocation inv, @Param("type") int type, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("isSelect") int isSelect) throws Throwable {
		if (isSelect != 1)
			return "";
		JdbcTemplate template = new JdbcTemplate(dataSource);
		
		DateParam param = new DateParam();
		String resultTime = param.getParam(startTime, endTime);
		if(param.isError(resultTime))
			return resultTime;
		
		return "@" + ReportTools.select2DB(template, type, resultTime);
	}
}
