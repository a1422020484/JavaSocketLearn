package cn.saturn.web.controllers.statistics;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.login.domain.AccountManager;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.controllers.statistics.dao.FBHard;
import cn.saturn.web.utils.ReportTools;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import zyt.spring.cache.entity.EntityFactorys;

/**
 * 副本统计相关
 * 
 * @author rodking
 *
 */
@Path("FBReport")
public class FBReportController {

	@Resource
	javax.sql.DataSource dataSource;

	@MainView
	@LoginCheck
	@Get("total")
	public String fbReportTotal(Invocation inv, @Param("type") int type, @Param("fb") int fb) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();

		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
		List<String> platforms = AccountManager.getPlatforms();

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		List<FBHard> hardLst = new ArrayList<FBHard>();

		if (fb == 1) {
			hardLst.add(new FBHard(1, "简单"));
			hardLst.add(new FBHard(2, "普通"));
			hardLst.add(new FBHard(3, "困难"));
		}
		if (fb == 2) {
			hardLst.add(new FBHard(101, "容易"));
			hardLst.add(new FBHard(102, "简单"));
			hardLst.add(new FBHard(103, "普通"));
			hardLst.add(new FBHard(104, "中等"));
			hardLst.add(new FBHard(105, "困难"));
		}
		if (fb == 3) {
			hardLst.add(new FBHard(210, "简单 1"));
			hardLst.add(new FBHard(220, "简单 2"));
			hardLst.add(new FBHard(230, "简单 3"));
			hardLst.add(new FBHard(240, "简单 4"));
			hardLst.add(new FBHard(250, "简单 5"));
		}

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/FBReport/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("platforms", platforms);
		request.setAttribute("servers", servers);
		request.setAttribute("hardLst", hardLst);

		return "FBReport";
	}

	@Get("totalInfo")
	public String channelReportTotalInfo(Invocation inv, @Param("type") int type, @Param("channelId") String channelId,
			@Param("srvId") int srvId, @Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("hardId") int hardId, @Param("isSelect") int isSelect) throws Throwable {

		if (isSelect != 1)
			return "";

		if (hardId <= 0) {
			return "@请选择难度!";
		}

		DateParam param = new DateParam();
		String resultTime = "";
		resultTime = param.getParam(startTime, endTime);

		if (param.isError(resultTime))
			return resultTime;

		JdbcTemplate template = new JdbcTemplate(dataSource);

		return "@" + ReportTools.select2DB(template, resultTime, type, channelId, srvId, hardId);
	}
}
