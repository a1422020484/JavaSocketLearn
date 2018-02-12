package cn.saturn.web.controllers.statistics;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.saturn.operation.OperationExt;
import cn.saturn.operation.ReportSqlTemp;
import cn.saturn.operation.TimeUtils;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerMergeDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeModel;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.controllers.statistics.dao.LogDbConnectionDAO;
import cn.saturn.web.controllers.statistics.dao.LogDbConnectionModel;
import cn.saturn.web.utils.ReportTools;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

/**
 * @author rodking
 * @ 1-1 等级分布
 *
 */
@Path("gameserver")
public class GameServerController {
	@Resource
	javax.sql.DataSource dataSource;
	
	@Resource
	LogDbConnectionDAO logDbConnDAO;
	
	@Resource
	ServerMergeDAO serverMergeDAO;
	
	@MainView
	@LoginCheck
	@Get("total")
	public String channelReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();
		List<ServerMergeModel> servers=serverMergeDAO.getAllServerMerge();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/gameserver/totalInfo?type=" + type));
		request.setAttribute("servers", servers);
		request.setAttribute("dayTime", dayTime);

		return "gameserver";
	}

	@Get("totalInfo")
	public String channelReportTotalInfo(Invocation inv, @Param("type") int type, @Param("srvId") int srvId,@Param("startTime") String startTime,
			@Param("endTime") String endTime,@Param("isSelect") int isSelect) throws Throwable {

		if (isSelect != 1)
			return "";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		
		DateParam param = new DateParam();
		String resultTime = param.getParamHms(startTime, endTime);

		
		if (param.isError(resultTime))
			return resultTime;
		
		if (srvId == -1){
			return "@请选择一个服务器id{param:server_id}";
		}
		
		LogDbConnectionModel logDbConnModel = logDbConnDAO.get(srvId);
		if (logDbConnModel == null)
			return "@请配置服务器连接sql";

		BasicDataSource dSource = OperationExt.createDataSource(logDbConnDAO.get(srvId));
		return "@" + ReportTools.select2DBTime(template, dSource, type, srvId,resultTime);
	}
}
