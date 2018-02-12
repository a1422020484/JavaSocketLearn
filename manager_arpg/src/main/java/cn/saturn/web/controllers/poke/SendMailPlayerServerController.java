package cn.saturn.web.controllers.poke;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.poke.dao.AccountModel;
import cn.saturn.web.controllers.poke.dao.MailAccount;
import cn.saturn.web.controllers.poke.dao.MailAcountDAO;
import cn.saturn.web.controllers.poke.dao.MailSvrPlay;
import cn.saturn.web.controllers.poke.dao.MailSvrPlayDAO;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.gamedata.ItemMsg;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.PageNoticeUtil;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.Protocol;
import proto.ProtocolWeb.SendMailWCS;
import proto.ProtocolWeb.SendMailWSC;
import zyt.spring.component.ComponentManager;

@Path("mailsrvplay")
public class SendMailPlayerServerController {
	
	
	@Resource
	MailSvrPlayDAO mailSvrPlayDAO;
	
	protected static final Logger logger = LoggerFactory.getLogger(LogType.allMailSend);
	
	
	@Get("toSend")
	public String toSend(Invocation inv) throws Throwable {

		List<MailSvrPlay> mailSvrPlayList = mailSvrPlayDAO.getMailSvrPlay();
		List<Integer> srvIdsM = new ArrayList<>();
		String text = "Dear player, please kindly check the third-party payment rebate reward. Thank you!";

		for (MailSvrPlay mailSvrPlay : mailSvrPlayList) {

			List<Protocol.ItemMsg> itemList = new ArrayList<>();
				Protocol.ItemMsg itemMsg = Protocol.ItemMsg.newBuilder().setItemId(100000002).setItemCount(mailSvrPlay.getCrystal()).build();
				itemList.add(itemMsg);
				
			SendMailWSC retMsg = null;

			try {
				int srvId =mailSvrPlay.getSrvId();
				int recvId =mailSvrPlay.getPlayerId();
				// 查找服务器
				ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
				ServerModel serverModel = serverComponent.find(srvId);
				
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
					logger.info("SrvId="+mailSvrPlay.getSrvId()+",PlayerId="+mailSvrPlay.getPlayerId()+",crystal="+mailSvrPlay.getCrystal());
				}else{
					logger.info("发送失败------"+"SrvId="+mailSvrPlay.getSrvId()+",PlayerId="+mailSvrPlay.getPlayerId()+",crystal="+mailSvrPlay.getCrystal());
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.info("发送失败------"+"SrvId="+mailSvrPlay.getSrvId()+",PlayerId="+mailSvrPlay.getPlayerId()+",crystal="+mailSvrPlay.getCrystal());
			}
		}
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, " 发送成功");
	}

}
