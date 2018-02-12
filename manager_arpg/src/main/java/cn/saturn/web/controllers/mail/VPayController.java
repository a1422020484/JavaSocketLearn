package cn.saturn.web.controllers.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.login.dao.UserModel;
import cn.saturn.web.controllers.logs.dao.LogDAO;
import cn.saturn.web.controllers.logs.dao.LogModel;
import cn.saturn.web.controllers.mail.dao.VpaycfgManager;
import cn.saturn.web.controllers.mail.dao.VpaycfgModel;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.Protocol;
import zyt.spring.component.ComponentManager;

/**
 * 虚拟充值
 *
 */
@Path("vpay")
public class VPayController {
	protected static final Logger logger = LoggerFactory.getLogger("vpay");

	@Resource
	LogDAO logDao;
	
	@Resource
	VpaycfgManager vpaycfgManager;

	@UserAuthority(PageModel.vpay_page)
	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/mail/vpay/index");
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.vpay_page)
	@Get("index")
	public String index(Invocation inv, @Param("srvId") int srvId) throws Throwable {

		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/mail/vpay/toSend"));

		return "vpay";
	}

	@LoginCheck
	@UserAuthority(PageModel.vpay_page)
	@Post("toSend")
	public String toSend(Invocation inv, @NotBlank @Param("srvId") int srvId, @NotBlank @Param("recvId") int recvId,
			@NotBlank @Param("money") String money) throws Throwable {
		// HttpServletRequest request = inv.getRequest();

		float theMoney = 0;
		money = money.trim();
		
		VpaycfgModel model = vpaycfgManager.getVpaycfgModel(money);
		if(model == null){
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "价格->"+ money +",找不到对应商品码");
		}
		
		theMoney = model.getPrice();
		int goodsId = model.getGoodId();
			
		// 判断金额
		if (theMoney <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "金额错误");
		}

		// 读取服务器ID
		// int srvId = ServerModel.getSrvId(srvCode);
		if (srvId <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器不存在");
		}

		// 查找服务器
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		ServerModel serverModel = serverComponent.find(srvId);
		if (serverModel == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器不存在");
		}

		// 连接服务器
		IClient client = serverModel.createClient();
		if (client == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器Url配置错误");
		}

		try {
			Protocol.ChargeCS.Builder ccs = Protocol.ChargeCS.newBuilder();
			ccs.setCpOrderId(0L);
			ccs.setGoodsId(goodsId);
			ccs.setGoodsPrice((goodsId != 0) ? theMoney : 0);
			ccs.setAmount(theMoney);
			ccs.setAccountId(recvId);
			ccs.setPlatform("system");

			// 发送并等待回馈
			Protocol.ChargeSC retMsg = client.call(Head.H1802, ccs.build(), Protocol.ChargeSC.class);
			if (retMsg == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "发送失败:" + client.getErrStr());
			}

			// logger
			logger.info("playerId=" + recvId + " goodsId=" + goodsId + " money=" + money);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO 创建日志(以后可以优化)
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");
		LogModel logModel = new LogModel();
		List<String> srvIds = new ArrayList<>();
		srvIds.add(serverModel.getSrvStr0());
		Map<String, Object> map = new HashMap<>();
		map.put("money", money);
		map.put("srvIds", srvIds);
		map.put("goodCode", goodsId);
		map.put("recipientId", recvId);

		logModel.setUser_id(userModel == null?0:userModel.getId());
		logModel.setUser_name(userModel == null?"unknow":userModel.getUsername());
		logModel.setType(LogModel.vpay_type);
		logModel.setContent(JSONObject.toJSON(map).toString());
		logDao.insert(logModel);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "发送成功");
	}
	
	/*@LoginCheck
	@UserAuthority(PageModel.vpay_page)
	@Post("toSend")
	public String toSend(Invocation inv, @NotBlank @Param("srvId") int srvId, @NotBlank @Param("recvId") int recvId,
			@NotBlank @Param("money") float money, @Param("goodsId") int goodsId) throws Throwable {
		// HttpServletRequest request = inv.getRequest();

		// 判断金额
		if (money <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "金额错误");
		}

		// 读取服务器ID
		// int srvId = ServerModel.getSrvId(srvCode);
		if (srvId <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器不存在");
		}

		// 查找服务器
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		ServerModel serverModel = serverComponent.find(srvId);
		if (serverModel == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器不存在");
		}

		// 连接服务器
		IClient client = serverModel.createClient();
		if (client == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器Url配置错误");
		}

		try {
			Protocol.ChargeCS.Builder ccs = Protocol.ChargeCS.newBuilder();
			ccs.setCpOrderId(0L);
			ccs.setGoodsId(goodsId);
			ccs.setGoodsPrice((goodsId != 0) ? money : 0);
			ccs.setAmount(money);
			ccs.setAccountId(recvId);
			ccs.setPlatform("system");

			// 发送并等待回馈
			Protocol.ChargeSC retMsg = client.call(Head.H1802, ccs.build(), Protocol.ChargeSC.class);
			if (retMsg == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "发送失败:" + client.getErrStr());
			}

			// logger
			logger.info("playerId=" + recvId + " goodsId=" + goodsId + " money=" + money);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO 创建日志(以后可以优化)
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");
		LogModel logModel = new LogModel();
		List<String> srvIds = new ArrayList<>();
		srvIds.add(serverModel.getSrvStr0());
		Map<String, Object> map = new HashMap<>();
		map.put("money", money);
		map.put("srvIds", srvIds);
		map.put("goodCode", goodsId);
		map.put("recipientId", recvId);

		logModel.setUser_id(userModel.getId());
		logModel.setUser_name(userModel.getUsername());
		logModel.setType(LogModel.vpay_type);
		logModel.setContent(JSONObject.toJSON(map).toString());
		logDao.insert(logModel);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "发送成功", "mail/vpay?mainView=true");
	}*/
}
