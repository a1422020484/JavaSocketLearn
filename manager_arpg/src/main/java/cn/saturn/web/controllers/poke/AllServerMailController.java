package cn.saturn.web.controllers.poke;

import net.paoding.rose.web.annotation.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.mail.dao.ActionResult;
import cn.saturn.web.controllers.poke.dao.AccountModel;
import cn.saturn.web.controllers.poke.dao.AccountModelDAO;
import cn.saturn.web.controllers.poke.dao.MailAccount;
import cn.saturn.web.controllers.poke.dao.MailAcountDAO;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.Protocol;
import proto.ProtocolWeb.SendMailWCS;
import proto.ProtocolWeb.SendMailWSC;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.gamedata.ItemMsg;

/**
 * 发送全服邮件 poke/allservermail/list?mainView=true
 * 
 * @author zh
 *
 */

@Path("allservermail")
public class AllServerMailController {

	@Resource
	MailAcountDAO mailAcountDAO;

	@Resource
	AccountModelDAO accountModelDAO;

	protected static final Logger logger = LoggerFactory.getLogger(LogType.allMailSend);

	@MainView
	@LoginCheck
	@Get("list")
	public String list(Invocation inv) throws Throwable {
		Utils.setAttributeValue(inv, "sendUrl", Utils.url(inv, "/poke/allservermail/toSend"));

		return "allservermail_edit";
	}

	@Post("toSend")
	public String toEdit(Invocation inv) throws Throwable {

		List<MailAccount> mailAccountList = mailAcountDAO.getMailAccountList();
		List<Integer> srvIdsM = new ArrayList<>();
		String text = "亲爱的训练家，邮件为“预约活动礼包”，请查收！祝您游戏愉快！";

		List<ItemMsg> items = new ArrayList<ItemMsg>();
		ItemMsg imsg = new ItemMsg();
		imsg.setId(100000001);
		imsg.setCount(100000);
		items.add(imsg);

		imsg = new ItemMsg();
		imsg.setId(100000002);
		imsg.setCount(200);
		items.add(imsg);

		imsg = new ItemMsg();
		imsg.setId(100000003);
		imsg.setCount(100);
		items.add(imsg);

		imsg = new ItemMsg();
		imsg.setId(100200030);
		imsg.setCount(5);
		items.add(imsg);

		List<Protocol.ItemMsg> itemList = new ArrayList<>();
		for (ItemMsg it : items) {
			Protocol.ItemMsg itemMsg = Protocol.ItemMsg.newBuilder().setItemId(it.getId()).setItemCount(it.getCount())
					.build();
			itemList.add(itemMsg);
		}

		for (MailAccount mailAccount : mailAccountList) {

			SendMailWSC retMsg = null;

			try {
				AccountModel accountModel = accountModelDAO.getMailAccountModel(mailAccount.getAccount(),
						mailAccount.getPlatform());

				int srvId = accountModel.getSrv_id();
				int recvId = accountModel.getPlayer_id();
				// 查找服务器
				ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
				ServerModel serverModel = serverComponent.find(srvId);
				/*
				 * if (serverModel == null) {
				 * 
				 * }
				 */
				// 加入日志记录中
				srvIdsM.add(srvId);

				// 连接服务器
				IClient client = serverModel.createClient();
				/*
				 * if (client == null) { return "@" +
				 * PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器[" +
				 * serverModel.getSrvStr0() + "] Url配置错误"); }
				 */

				SendMailWCS.Builder msgb = SendMailWCS.newBuilder();
				msgb.setMsgStr(text); // 邮件消息
				msgb.setRecvId(recvId + "");

				msgb.addAllItemList(itemList); // 物品ID

				// 发送并等待回馈
				retMsg = client.call(Head.H11001, msgb.build(), SendMailWSC.class);
				if (retMsg != null) {
					logger.info(mailAccount.getAccount());
				}

			} catch (Exception e) {

			}
		}
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, " 发送成功");
	}

}
