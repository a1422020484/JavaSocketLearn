package cn.saturn.web.controllers.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import com.alibaba.fastjson.JSONObject;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.login.dao.UserModel;
import cn.saturn.web.controllers.logs.dao.LogDAO;
import cn.saturn.web.controllers.logs.dao.LogModel;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.ProtocolTools;
import cn.saturn.web.utils.ResultMsg;
import cn.saturn.web.utils.ResultMsgUtils;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.StringExtUtil;
import cn.saturn.web.utils.Utils;

@Path("gmall")
public class GMAllController {
	@Resource
	LogDAO logDao;

	@UserAuthority(PageModel.gmall_page)
	@Get("index")
	public String index(Invocation inv) throws Throwable {
		Utils.setAttributeValue(inv, "sendUrl", Utils.url(inv, "/mail/gmall/toSend"));
		return "gmall/gmall";
	}

	@LoginCheck
	@UserAuthority(PageModel.gmall_page)
	@Post("toSend")
	public String toSend(Invocation inv, @NotBlank @Param("srvIds") String srvIdStrs, @Param("msg") String msg)
			throws Throwable {
		// 服务器ID
		int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs);
		final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];

		if (StringExtUtil.isEmpty(msg))
			return "@" + "<span style='color:red'>消息体不能为空!</span>";

		// 刷新热点精灵
		List<ResultMsg> sendRetMsg = new ArrayList<>();
		for (int srvId : srvIds) {
			ServerModel serverModel = ServerUtils.getServer(srvId, true);
			List<String> msgs = StringExtUtil.toList(msg, ";");
			sendRetMsg = ProtocolTools.toSendGMCmdWSC(serverModel, msgs);
		}

		// 做日志
		HttpSession session = inv.getRequest().getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");
		Map<String, Object> map = new HashMap<>();
		map.put("ctx", msg);
		map.put("srvIds", srvIds);
		String json = JSONObject.toJSONString(map);
		LogModel logModel = LogModel.create(userModel, LogModel.gmall_type, json);
		if (logModel != null)
			logDao.insert(logModel);

		return "@" + ResultMsgUtils.getResult2Msg(sendRetMsg);
	}
}
