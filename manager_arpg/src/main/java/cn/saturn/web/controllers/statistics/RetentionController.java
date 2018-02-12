package cn.saturn.web.controllers.statistics;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import zyt.spring.cache.entity.EntityFactorys;
import zyt.spring.component.ComponentManager;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import cn.saturn.web.code.login.domain.AccountManager;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;


@Path("retention")
public class RetentionController {
	
	@Get("")
	public String index(Invocation inv) throws Throwable {
		
		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
		List<String> platforms = AccountManager.getPlatforms();
		
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		HttpServletRequest request = inv.getRequest();
//		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/channelReport/totalInfo?type=" + type));
		request.setAttribute("platforms", platforms);
		request.setAttribute("servers", servers);
		
		return "retention";
	}
	
	

}
