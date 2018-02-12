package cn.saturn.web.controllers.server;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.saturn.operation.OperationExt;
import cn.saturn.operation.ReportSqlTemp;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.login.domain.AccountManager;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeModel;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import zyt.spring.cache.entity.EntityFactorys;
import zyt.spring.component.ComponentManager;


@Path("serverMerge")
public class ServerMergeController {
	
	@Resource
	ServerMergeDAO serverMergeDAO;

	@Resource
	javax.sql.DataSource dataSource;
	
	@MainView
	@LoginCheck
	@Get("list")
	public String serverReportTotal(Invocation inv) throws Throwable {

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();
		
		List<ServerMergeModel>  servermerges=serverMergeDAO.getAllServerMerge();
		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/server/serverMerge/totalInfo?"));
		request.setAttribute("servers", servers);
		request.setAttribute("servermerges", servermerges);
		return "serverMerge";
	}
	
	
	@LoginCheck
	@Post("totalInfo")
	public String serverReportTotalInfo(Invocation inv, @Param("srvId") int srvId) throws Throwable {
		if (srvId == -1)
			return "@主服务器不能为空";
			
		HttpServletRequest request = inv.getRequest();
		String[]  servermergestr=request.getParameterValues("servermerge");
		
		
		if (servermergestr ==null)
			return "@从服务器不能为空";
		for(int i=0;i<servermergestr.length;i++){
			Date date = new Date();
			int servermerge =Integer.parseInt(servermergestr[i]);
			ServerMergeModel  serverMergeModel =serverMergeDAO.getServerMergeModel(servermerge);
			serverMergeModel.setPid(srvId);
			serverMergeModel.setState(2);
			if(servermerge !=srvId){
				serverMergeModel.setMertime(date);
			}
			serverMergeDAO.insertOrUpdate(serverMergeModel);
			
		}
		
		ServerMergeModel  serverMergeModel =serverMergeDAO.getServerMergeModel(srvId);
		serverMergeModel.setState(2);
		serverMergeDAO.insertOrUpdate(serverMergeModel);
		return "@操作成功";
	}
	
}


