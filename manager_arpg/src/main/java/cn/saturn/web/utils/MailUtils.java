package cn.saturn.web.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Properties;


import javax.annotation.Resource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.power.dao.AccountWarnDAO;
import cn.saturn.web.controllers.power.dao.AccountWarnSealDAO;
import cn.saturn.web.controllers.power.dao.AccountWarnSealModel;
import cn.saturn.web.controllers.power.dao.AccountWarnWhiteModel;
import cn.saturn.web.controllers.server.dao.IClient;
import proto.ProtocolWeb.GetPlayerInfoAllWCS;
import proto.ProtocolWeb.GetPlayerInfoAllWSC;

@Component
public  class MailUtils {
	
	@Resource
	AccountWarnSealDAO accountWarnSealDAO;
	
	@Resource
	AccountWarnDAO accountWarnDAO;
	
	
	// 发件人的 邮箱 和 密码
	public static String myEmailAccount = "qq3065785143@163.com";
	public static String myEmailPassword = "saturn2016";

	// 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般格式为: smtp.xxx.com
	// 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
	public static String myEmailSMTPHost = "smtp.163.com";

	// 收件人邮箱
	public static String receiveMailAccount = "121589913@qq.com";
	
	//获取config.properties配置的平台地址
	public static String getConfig(String key) throws Exception{
		Properties pros = new Properties();
        String value = "";
        
        pros.load(new InputStreamReader(MailUtils.class.getResourceAsStream("/config.properties"), "UTF-8"));
        value = pros.get(key).toString();
        return value;
		}
	
	
	public  void sendEmail() throws Exception {
		
		Date yesdayStart = null;
		Date yesdayend=null;
		Date todayStart=null;
		Date todayend=null;
		try {
		
			yesdayStart = DateUtils.getAddDayZeroPoint(new Date(), -1);
			yesdayend=DateUtils.getAddDayLastTime(new Date(),-1);
			
			todayStart = DateUtils.getAddDayZeroPoint(new Date(), 0);
			todayend=DateUtils.getAddDayLastTime(new Date(),0);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties props = new Properties(); // 参数配置
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.host", myEmailSMTPHost); // 发件人的邮箱的 SMTP 服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 请求认证，参数名称与具体实现有关
		//props.setProperty("mail.smtp.port", "587");
		
		// 2. 根据配置创建会话对象, 用于和邮件服务器交互
		Session session = Session.getDefaultInstance(props);
		
//		 session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log
		
		StringBuffer sBuffer = new StringBuffer("用户你好：<br/>");
		//加入config配置的平台地址
		sBuffer.append("平台地址:"+MailUtils.getConfig("platform")+"<br/>");
		
		sBuffer.append("异常报警：<br/>");
		List<AccountWarnSealModel>  accountWarnSealModelList=accountWarnDAO.getAccountWarnModelAll();
		int unusualcount=accountWarnSealModelList!=null?accountWarnSealModelList.size():0;
		if(unusualcount !=0){
		for(AccountWarnSealModel  accountWarnSealModel:accountWarnSealModelList){
			sBuffer.append(accountWarnSealModel.toString()+"<br/>");
		}
		}
		
		sBuffer.append("<br/>");
		sBuffer.append("白名单报警：<br/>");
		List<AccountWarnWhiteModel>  accountWarnWhiteModelList=accountWarnDAO.getAccountWarnWhiteModelListAll();
		int whitecount=(accountWarnWhiteModelList !=null)?accountWarnWhiteModelList.size():0;
		if(whitecount!=0){
			for(AccountWarnWhiteModel accountWarnWhiteModel:accountWarnWhiteModelList){
				sBuffer.append(accountWarnWhiteModel.toString()+"<br/>");
			}
		}
		
		//List<AccountWarnSealModel>  autoAccountWarnSealModel=accountWarnSealDAO.getAutoAccountWarnAllModelListAll();
		
		sBuffer.append("<br/>");
		sBuffer.append("封号报警：<br/>");
		
		List<AccountWarnSealModel>  autoAccountWarnSealModelList=accountWarnDAO.getAutoAccountWarnAllModelListAll();
		int autocount=autoAccountWarnSealModelList!=null?autoAccountWarnSealModelList.size():0;
		if(autocount !=0){
		for(AccountWarnSealModel autoAccountWarnSealModel:autoAccountWarnSealModelList){
			sBuffer.append(autoAccountWarnSealModel.toString()+"<br/>");
		}
		}
		
		sBuffer.append("<br/>");
		sBuffer.append("解封报警：<br/>");
		List<AccountWarnSealModel>  unsetAccountWarnSealModelList=accountWarnDAO.getUnsetAccountWarnSealModelAll(yesdayStart, yesdayend, todayStart, todayend);
		int unsetCount=(unsetAccountWarnSealModelList!=null)?unsetAccountWarnSealModelList.size():0;
		if(unsetCount !=0){
			for(AccountWarnSealModel  unsetAccountWarnSealModel:unsetAccountWarnSealModelList){
				int  serverId=unsetAccountWarnSealModel.getSrvid();
				String IdStr = String.valueOf(unsetAccountWarnSealModel.getRoleid());
				try{
				IClient client = ServerUtils.createClient(serverId);

				// 发送消息
				GetPlayerInfoAllWCS.Builder msgb = GetPlayerInfoAllWCS.newBuilder();
				msgb.setPlayerText(IdStr);
				// 发送并等待回馈
				GetPlayerInfoAllWSC retMsg = client.call(Head.H10006, msgb.build(), GetPlayerInfoAllWSC.class);
				unsetAccountWarnSealModel.setViplevel(retMsg.getVip());
				unsetAccountWarnSealModel.setLevel(retMsg.getLv());
				unsetAccountWarnSealModel.setGold(retMsg.getGold());
				unsetAccountWarnSealModel.setCrystal(retMsg.getCrystal());
				unsetAccountWarnSealModel.setPlayername(retMsg.getName());
				} catch (Exception e1) {	
				}
				sBuffer.append(unsetAccountWarnSealModel.toString()+"<br/>");
			}
		}
		
		

		if(unusualcount+whitecount+autocount+unsetCount==0){
			return ;
		}
		

		// 3. 创建一封邮件
		MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount,sBuffer.toString());
		
		// 4. 根据 Session 获取邮件传输对象
		Transport transport = session.getTransport();

		// 5. 使用 邮箱账号 和 密码 连接邮件服务器
		// 这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
		transport.connect(myEmailAccount, myEmailPassword);

		// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人,
		// 抄送人, 密送人
		transport.sendMessage(message, message.getAllRecipients());

		// 7. 关闭连接
		transport.close();
	
	}

	/**
	 * 创建一封只包含文本的简单邮件
	 *
	 * @param session
	 *            和服务器交互的会话
	 * @param sendMail
	 *            发件人邮箱
	 * @param receiveMail
	 *            收件人邮箱
	 * @return
	 * @throws Exception
	 */
	public  MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String textMail) throws Exception {
		// 1. 创建一封邮件
		MimeMessage message = new MimeMessage(session);

		// 2. From: 发件人
		message.setFrom(new InternetAddress(sendMail, "土星网后台", "UTF-8"));

		// 3. To: 收件人（可以增加多个收件人、抄送、密送）
		message.setRecipient(MimeMessage.RecipientType.TO,
				new InternetAddress(receiveMail, receiveMailAccount, "UTF-8"));

		// 4. Subject: 邮件主题
		message.setSubject("后台异常账号报警", "UTF-8");
		
		
		
		// 5. Content: 邮件正文（可以使用html标签）
		message.setContent(textMail, "text/html;charset=UTF-8");

		// 6. 设置发件时间
		message.setSentDate(new Date());

		// 7. 保存设置
		message.saveChanges();

		return message;
	}
}