package cn.saturn.web.utils;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.saturn.web.center.SendCenter;
import cn.saturn.web.controllers.power.AutoBanned;
import cn.saturn.web.controllers.power.Banned;
import cn.saturn.web.controllers.power.ClockWhite;
import cn.saturn.web.controllers.statistics.AccountBindToStatic;
import cn.saturn.web.controllers.statistics.AccountToStatic;
import cn.saturn.web.controllers.statistics.FilePathLog;
import cn.saturn.web.controllers.statistics.LoginLogToStatic;
import cn.saturn.web.controllers.statistics.OrderToStatic;
import cn.saturn.web.controllers.statistics.dao.NextDayTimerDAO;

@Component
@Lazy(value = false)
public class QuartzJob {

	@Resource
	FilePathLog filePath;

	@Resource
	AccountToStatic accountToStatic;

	@Resource
	LoginLogToStatic loginLogToStatic;

	@Resource
	OrderToStatic orderToStatic;

	@Resource
	AccountBindToStatic accountBindToStatic;
	
	@Resource
	Banned banned;
	
	@Resource
	ClockWhite clockWhite;
	
	@Resource
	AutoBanned  autoBanned;
	
	@Resource
	MailUtils mailUtils;
	
	@Resource
	SendCenter sendCenter;

	private final boolean isOpen;

	public QuartzJob() {

		isOpen = Utils.config.get("report.open", false);
	}
	
	@Scheduled(cron = "0 0/10 * * * ?")
	public void dayline() throws Throwable{
		// 日报汇总表实时发送
		//System.out.println("SendDayline");
		sendCenter.SendDayline(1001);
    }
	
	@Scheduled(cron = "0 15 5 * * ?")
	public void daydate() throws Throwable{
		// 日报汇总表统计发送
		sendCenter.SendDaydate(1000);
    }
	
	/*@Scheduled(cron = "0 51 20 * * ?")
	public void daydate() throws Throwable{
		// 日报汇总表统计发送
		sendCenter.SendDaydate(1000);
    }*/
	
	@Scheduled(cron = "0 30 0 * * ?")
	public void accountToStatic() throws Throwable {
		// 是否开启汇总统计
		if (!isOpen)
			return;
		accountToStatic.queryAccountToStatic();
		accountBindToStatic.queryAccountBindToStatic();
		orderToStatic.queryOrderModelToStatic();
		loginLogToStatic.queryLoginLogToStatic();
	}
	
	@Scheduled(cron = "0 50 0 * * ?")
	public void queryGoodsInfo() throws Throwable {
		// 是否开启汇总统计
		if (!isOpen)
			return;
		filePath.queryGoodsInfo(0);
		filePath.queryGoodsInfo(1);
		filePath.queryGoodsInfoString("behavior/get_{0}.log");
		filePath.queryGoodsInfoString("behavior/guide_{0}.log");
		filePath.queryGoodsInfoString("behavior/pay_{0}.log");
		filePath.queryGoodsInfoString("behavior/task_{0}.log");
	}
	
	
	@Scheduled(cron = "0 0 9 * * ?")
	public void sendMail() throws Throwable{
		// 是否开启汇总统计
				if (!isOpen)
				return;
			banned.queryBannedInfo();
			autoBanned.queryAutoBannedInfo();
			clockWhite.updateClockWhite();
			mailUtils.sendEmail();
			System.out.println("sendEmail");
		
    }
	
}
