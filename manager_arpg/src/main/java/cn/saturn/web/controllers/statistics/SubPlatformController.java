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
 * @ 1-1 
 *
 */
@Path("subPlatform")
public class SubPlatformController {
	@Resource
	javax.sql.DataSource dataSource;
	
	@Resource
	NextDayTimer nextDayTimer;
	
	
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
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/subPlatform/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("subplatforms", subPlatformModels);

		return "subPlatformReport";
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
		
		resultTime = param.getParam(startTime, endTime);

		if (param.isError(resultTime))
			return resultTime;

			SubChannelSummaryManager channelSummaryManager = ComponentManager.getComponent(SubChannelSummaryManager.class);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date endDate = format.parse(endTime);
			if(channelSummaryManager.isSummary() && DateUtils.isAfterToday(endDate))
				return "@当天数据实时统计，正在努力统计中,请稍后...预计3分钟";
			return "@" + selectTable(template, type, subChannelId, startTime, endTime);
		
		
	}

	private String selectTable(JdbcTemplate template, int type, String subChannelId, String startTime, String endTime) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String platform = subChannelId.equals("-1") ? "%" : subChannelId + "";
		String sql = MessageFormat.format(temp.getSql(), platform, startTime, endTime);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
        String result = OperationExt.queryToJson(temp.getTitle_ch(), out);
		return result;
	}
	
	

}
