package cn.saturn.web.controllers.statistics;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import cn.saturn.operation.OperationExt;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.login.domain.AccountManager;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.LogDbConnectionDAO;
import cn.saturn.web.controllers.statistics.dao.PlatformDAO;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import zyt.spring.cache.entity.EntityFactorys;

/**
 * 1.事实查询游戏中的数据 , 比较消耗游戏性能.<br>
 * 2.匹配服务器id,server_id.<br>
 * 
 * @author rodking @ 时时活跃表
 */
@Path("activeReport")
public class ActiveReportController {
	@Resource
	javax.sql.DataSource dataSource;

	@Resource
	LogDbConnectionDAO logDbConnDAO;
	
	@Resource
	PlatformDAO	platformDAO;

	@MainView
	@LoginCheck
	@Get("total")
	public String channelReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();

		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
		List<String> platforms = AccountManager.getPlatforms();

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/activeReport/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("platforms", platforms);
		request.setAttribute("servers", servers);

		return "activeReport";
	}

	@Get("totalInfo")
	public String channelReportTotalInfo(Invocation inv, @Param("type") int type, @Param("srvId") int srvId,
			@Param("isSelect") int isSelect) throws Throwable {
		if (isSelect != 1)
			return "";
		if (srvId == -1)
			return "@请选择一个服务器id{param:server_id}";

		int key = 0;
		for(int i=0;i<SystemLogParams.getKeys().length;i++)
		{
			if(SystemLogParams.getKeys()[i].equals("系统登录日志"))
				key = i;
		}
		
		String nowDay = DateUtils.getNowDay();
		String result = OperationExt.queryLogFile(key, srvId, nowDay);
		// 如果包含@ 直接返回
		if (result.contains("@"))
			return result;

		List<String[]> results = OperationExt.getResultMsg(result);

		return "@" + OperationExt.queryToJson(new String[] { "时间", "人数" }, results).toString();
	}

	
}
