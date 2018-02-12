package cn.saturn.web.controllers.mail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import cn.saturn.web.utils.Config;
import cn.saturn.web.utils.HttpTookit;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.Protocol;
import proto.ProtocolWeb.GetPlayerInfoAllWCS;
import proto.ProtocolWeb.GetPlayerInfoAllWSC;
import xzj.core.util.MD5;
import zyt.spring.component.ComponentManager;

/**
 * 走pay项目的虚拟充值
 *
 */
@Path("paypay")
public class PayPayController {
	protected static final Logger logger = LoggerFactory.getLogger("vpay");
	//支付地址
	private static String payaddr = Config.val("payaddr");
	//支付key
	private static String paykey = Config.val("paykey");
	String payad=payaddr+"/saturn/pay";

	@Resource
	LogDAO logDao;
	
	@Resource
	VpaycfgManager vpaycfgManager;

	
	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/mail/paypay/index");
	}

	@MainView
	@LoginCheck
	@Get("index")
	public String index(Invocation inv, @Param("srvId") int srvId) throws Throwable {

		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/mail/paypay/toSend"));

		return "paypay";
	}

	@LoginCheck
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

		try {
			if(org.apache.commons.lang.StringUtils.isEmpty(String.valueOf(recvId)))
			{
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "用户不存在");
			}
			// 连接服务器
			IClient client = ServerUtils.createClient(srvId);
			if (client == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器连接错误");// 不处理
			}

			// 发送消息
			GetPlayerInfoAllWCS.Builder msgb = GetPlayerInfoAllWCS.newBuilder();
			msgb.setPlayerText(String.valueOf(recvId));
			// 发送并等待回馈
			GetPlayerInfoAllWSC retMsg = client.call(Head.H10006, msgb.build(), GetPlayerInfoAllWSC.class);
			if (retMsg == null) {
				return "@查无此人-" + recvId; // 不处理
			}

			// 读取角色信息
			int id = retMsg.getPlayerId();
			String name = retMsg.getName();
			String account = retMsg.getAccount();
			String platform = retMsg.getPlatfrom();
			
			// int serverId, int playerId,String playerName,
			//float priced, int goodId,String accountName,String sign
			
			String sign=MD5.encode(srvId+recvId+goodsId+account+paykey);
			String sendStr="serverId="+srvId+"&playerId="+recvId+"&playerName="+name+"&priced="+theMoney+"&goodId="+goodsId+"&accountName="+account+"&platform="+platform+"&sign="+sign;
			String report=HttpTookit.doGet(payad, sendStr, "UTF-8", true);
			int reportInt=0;
			try{
				reportInt=Integer.valueOf(report.replaceAll("\r|\n", "").trim());
			} catch (Exception e) {

			}
			
			//发送成功判断，0失败，1成功;
			if(reportInt !=1)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "虚拟支付发送失败");
			
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
	
}
