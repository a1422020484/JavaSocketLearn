package cn.saturn.web.controllers.poke;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.poke.dao.MailAll;
import cn.saturn.web.controllers.poke.dao.MailAllDAO;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.Config;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.PageNoticeUtil;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import proto.Protocol;
import proto.ProtocolWeb.SendMailWCS;
import proto.ProtocolWeb.SendMailWSC;
import zyt.spring.component.ComponentManager;

@Path("sendMailAll")
public class SendMailAllController {

	@Resource
	MailAllDAO mailAllDAO;

	protected static final Logger logger = LoggerFactory.getLogger(LogType.allMailSend);

	private static String mailHead = Config.val("mailHead");
	
	public static Date getDaysAgo(int days) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(new Date());
		calender.add(Calendar.DATE, -days);
		return calender.getTime();
	}
	
	public static String format(Date date) {
		return getDateFormat().format(date);
	}
	
	public static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}


	@Get("toSend")
	public String toSend(Invocation inv) throws Throwable {
		
		Date sevenDays =getDaysAgo(7);

		List<MailAll> mailallList = mailAllDAO.getMailAll("Ios",sevenDays);
		List<Integer> srvIdsM = new ArrayList<>();

		for (MailAll mailall : mailallList) {

			try {

				String award = mailall.getAward();

				List<Protocol.ItemMsg> itemList = new ArrayList<>();

				SendMailWSC retMsg = null;

				StringBuffer sb = new StringBuffer();

				String[] awardArray = award.split(";");
				for (String awardarr : awardArray) {
					String[] itemArray = awardarr.split("\\*");
					int item = Integer.valueOf(itemArray[0]);
					int num = Integer.valueOf(itemArray[1]);

					sb.append(";item=").append(item).append(",num=").append(num).append(";;");
					Protocol.ItemMsg itemMsg = Protocol.ItemMsg.newBuilder().setItemId(item).setItemCount(num).build();
					itemList.add(itemMsg);
				}

				int srvId = mailall.getSrvId();
				int recvId = mailall.getPlayerId();
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
				msgb.setMsgStr(mailHead); // 邮件消息
				msgb.setRecvId(recvId + "");

				msgb.addAllItemList(itemList); // 物品ID

				// 发送并等待回馈
				retMsg = client.call(Head.H11001, msgb.build(), SendMailWSC.class);
				if (retMsg != null) {
					logger.info("SrvId=" + mailall.getSrvId() + ",PlayerId=" + mailall.getPlayerId() + ",award="  + sb.toString());
				} else {
					logger.info("发送失败------" + "SrvId=" + mailall.getSrvId() + ",PlayerId=" + mailall.getPlayerId()
							+ ",award=" + sb.toString());
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.info("发送失败------" + "SrvId=" + mailall.getSrvId() + ",PlayerId=" + mailall.getPlayerId()
						+ ",award=" + "");
			}
		}
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, " 发送成功");
	}

}
