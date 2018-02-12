package cn.saturn.web.controllers.test;

import cn.saturn.web.code.action.LoginAction;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("ip")
public class VindicatorIpController {

	@Get("check")
	public String show(Invocation inv, @Param("ip") String ip) throws Throwable {
		if (ip == null) {
			return "@ip参数为空";
		}
		boolean result = LoginAction.vindicatorIp.find(ip);
		return "@" + (result ? "在ip内" : "不在ip内");
	}

	@Get("reload")
	public String toChange(Invocation inv) {
		boolean result = LoginAction.vindicatorIp.reload(); // 重载
		return "@" + (result ? "重载成功" : "重载失败");
	}

}
