package cn.saturn.web.controllers.statistics;

import javax.servlet.http.HttpServletRequest;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import cn.saturn.operation.OperationExt;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.utils.Utils;

@Path("systemLog")
public class SystemLogController {

	@MainView
	@LoginCheck
	@Get("show")
	public String statistics(Invocation inv) throws Throwable {
		String dayTime = TimeUtils.getTodayStr();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/systemLog/toSend"));
		request.setAttribute("dayTime", dayTime);

		return "serverLog";
	}

	@Get("toSend")
	public String toSend(Invocation inv, @Param("srvId") int srvId, @Param("dayStr") String dayStr,
			@Param("type") int type) throws Throwable {
		String resultMsg = OperationExt.queryLogFile(type,srvId, dayStr);
		if (resultMsg.contains("@"))
			return resultMsg;

		StringBuffer strBuf = new StringBuffer();
		strBuf.append("@");
		strBuf.append("<label>");
		strBuf.append(resultMsg.replace("\n", "<br>"));
		strBuf.append("</label>");
		String outStr = strBuf.toString();
		return outStr;
	}
}
