package cn.saturn.web.controllers.statistics;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.saturn.operation.OperationExt;
import cn.saturn.operation.ReportSqlTemp;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerMergeDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeModel;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.AdvDaoManager;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import zyt.spring.cache.entity.EntityFactorys;
import zyt.spring.component.ComponentManager;


@Path("advDay")
public class AdvDayConController {
	
	

	@Resource
	javax.sql.DataSource dataSource;
	
	
	@MainView
	@LoginCheck
	@Get("total")
	public String serverReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();

		
				
		List<String> adplatforms = AdvDaoManager.getAdPlatforms();
		
		List<String> adsubplatforms = AdvDaoManager.getAdSubPlatforms();

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();
		
		//List<ServerMergeModel>  servers =serverMergeDAO.getAllServerMerge();
		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/advDay/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("adplatforms", adplatforms);
		request.setAttribute("adsubplatforms", adsubplatforms);
		request.setAttribute("servers", servers);

		return "advDay";
	}
	
	
	@LoginCheck
	@Get("totalInfo")
	public String serverReportTotalInfo(Invocation inv, @Param("type") int type, @Param("adplatform") String adplatform,
			@Param("adsubplatform") String adsubplatform, @Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("isSelect") int isSelect) throws Throwable {
		if (isSelect != 1)
			return "";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		DateParam param = new DateParam();
		String resultTime = "";
		String resultQuery = "";
		resultTime = param.getParamHms(startTime, endTime);

		if (param.isError(resultTime))
			return resultTime;
		
		resultQuery = param.getQuery(startTime, endTime);
		
		
		if (param.isError(resultQuery))
			return resultQuery;

		//return "@" + selectTable(template, type, channelId, srvId, resultTime);
		return "@" + selectTable(template, type, adplatform,adsubplatform,resultTime,resultQuery);
	}

	
	private String selectTable(JdbcTemplate template,  int type,String adplatform ,String adsubplatform , String resultTime,String resultQuery) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		
		
		String adplatformstr = adplatform.equals("-1") ? "%" : adplatform + "";
		
		String adsubplatformstr = adsubplatform.equals("-1") ? "%" : adsubplatform + "";
		
		String sql = "";

		sql = MessageFormat.format(temp.getSql(),adplatformstr,adsubplatformstr ,resultTime,resultQuery);
		//System.out.println(sql);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		
		return OperationExt.queryToJson(temp.getTitle_ch(), out);
	}
	
}


