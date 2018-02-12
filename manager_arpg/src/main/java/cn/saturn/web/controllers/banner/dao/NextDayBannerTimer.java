package cn.saturn.web.controllers.banner.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.saturn.web.code.update.ServerTimer;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.server.dao.ServerXFtpDAO;
import cn.saturn.web.controllers.server.dao.ServerXFtpModel;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.HourUtils;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.sftp.SftpTools;
import zyt.spring.component.ComponentManager;

/**
 * banner 0点发送 <br>
 * 
 * @author rodking
 */
@Component
public class NextDayBannerTimer extends ServerTimer {
	protected static final Logger logger = LoggerFactory.getLogger(LogType.timeReport);

	@Resource
	ServerXFtpDAO serverXFtpDao;

	@Resource
	BannerModelManager bannerModelMgr;

	private boolean isExec = false;
	private long timeStamp;
	private final boolean isOpen;

	public NextDayBannerTimer() {
		// 每小时定时
		super(1, "nextDayTimer", 60 * 60 * 1000);
		timeStamp = HourUtils.getNowHour();
		isOpen = Utils.config.get("report.open", false);
		// TODO 测试用 20 秒执行一次
		// super(1, "nextDayTimer", 20 * 1000);

	}

	@Override
	protected void update(int count) {
		try {
			// 不开启上传配置
			if (!isOpen)
				return;

			Calendar nowC = Calendar.getInstance();
			nowC.setTime(new Date());

			long dHour = HourUtils.getHour(nowC.getTimeInMillis()) - timeStamp;
			int curHour = nowC.get(Calendar.HOUR_OF_DAY);
			System.out.println("banner timer dHour->"+dHour+",curHour->"+curHour);

			isExec = false;

			// 每天的0点执行一次
			if (curHour == 0 || dHour > 24) {
				isExec = true;
			}

			//
			if (isExec) {
				timeStamp = HourUtils.getNowHour();
				long dStart = System.currentTimeMillis();

				bannerUpdateByTime();
//			bannerUpdateByDate();

				logger.info("[ 上传文件banner开始时间 ]:" + dStart);
				logger.info("[ 上传文件banner结束时间 ]:" + (System.currentTimeMillis() - dStart) + " ms..");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Banner timer execute complete");

		// 测试用
		// System.out.println(" time i am here");
		// collectGameLog();
		// if (isRun) {
		// System.out.println("开始");
		// execGamePerHourOnline();
		// isRun = false;

		// System.out.println("结束");
		// }
	}

	/**
	 * 是否发送热点精灵
	 */
	/*public void bannerUpdateByTime() {
		try {
			// 1.按照时间发送
			List<PresetModel> pModels = PresetManager.getPresetList(PresetManager.presetType_banner);
			PresetBanner pBanner = null;

			for (PresetModel model : pModels) {
				PresetBanner m = model.getValue(PresetBanner.class);

				if (m != null) {
					if (!m.isOpenTime && m.getSendNormalTime().equals(DateUtils.getNowDay())) {
						pBanner = m;
						break;
					}
				}
			}

			if (pBanner == null)
				return;

			bannerModelMgr.writeFile(BannerModel.createBanners(pBanner));

			// 要发送的服务器
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			List<ServerModel> srvlist = serverComponent.getList();
			List<ServerXFtpModel> sModel = new ArrayList<>();
			int[] svrs = pBanner.getSrvs();
			if(svrs == null || svrs.length == 0)
				return;
			boolean status = false;
			for (ServerModel sm : srvlist) {
				ServerXFtpModel model = serverXFtpDao.getBySid((int) sm.getId());
				for(int i =0; i<svrs.length; i++){
					if(svrs[i] == sm.getId())
						status = true;
				}
				if(!status)
					continue;
				
				if (model != null)
					sModel.add(model);
			}
			SftpTools.exec(sModel, BannerModelManager.writeSingle);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}*/
	
	public void bannerUpdateByTime(int[] svrIds) {
		try {
			// 1.按照时间发送
			List<PresetModel> pModels = PresetManager.getPresetList(PresetManager.presetType_banner);
			PresetBanner pBanner = null;

			//所有活动
			List<PresetModel> all = PresetManager.getPresetList(PresetManager.presetType_activity);
			// 要发送的服务器
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			List<ServerModel> srvlist = serverComponent.getList();
			List<ServerXFtpModel> sModel;
			for (ServerModel sm : srvlist) {
				sModel = new ArrayList<>();
				if(svrIds != null && !ArrayUtils.contains(svrIds, (int)sm.getId()))
					continue;
				
				ServerXFtpModel sft = serverXFtpDao.getBySid((int) sm.getId());
			    System.out.println("sft->"+(sft==null?"null":sft.getS_id()));
				
				if (sft == null)
					continue;
				
				for (PresetModel model : pModels) {
					PresetBanner m = model.getValue(PresetBanner.class);
					if (m != null) {
						if (!m.isOpenTime && DateUtils.getNowDay().equals(m.getSendNormalTime())) {
							pBanner = m;
							break;
						}
						String openTimeStr = DateUtils.getAddDayStr(sm.getOpen_time(), m.getSendOpenTime()>0?m.getSendOpenTime()-1:0);
						// 如果时间能匹配上
						if (m.isOpenTime() && DateUtils.getNowDay().equals(openTimeStr)) {
							pBanner = m;
							break;
						}
					}
				}
				
				sModel.add(sft);
				
				bannerModelMgr.writeFile(BannerModel.createBanners(pBanner, sft.getS_id(), all));
				System.out.println("------------bannerUpdateByTime exec--------------");
				SftpTools.exec(sModel, BannerModelManager.writeSingle);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void bannerUpdateByTime(){
		this.bannerUpdateByTime(null);
	}

	/*private void bannerUpdateByDate() {
		try {
			// 2.按照开服时间
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			List<PresetModel> pModels = PresetManager.getPresetList(PresetManager.presetType_banner);
			PresetBanner pBanner = null;

			List<ServerModel> srvlist = serverComponent.getList();
			List<ServerXFtpModel> sModel = new ArrayList<>();

			// 遍历banner 模块
			for (PresetModel model : pModels) {
				PresetBanner m = model.getValue(PresetBanner.class);
				if (m != null) {
					
					int[] svrs = m.getSrvs();
					if(svrs == null || svrs.length == 0)
						return;
					boolean status = false;
					
					for (ServerModel sm : srvlist) {
						// 遍历服务器模块
						ServerXFtpModel sftpModel = serverXFtpDao.getBySid((int) sm.getId());
						if (sftpModel != null) {
							String openTimeStr = DateUtils.getAddDayStr(sm.getOpen_time(), m.getSendOpenTime());
							// 如果时间能匹配上
							if (m.isOpenTime() && openTimeStr.equals(DateUtils.getNowDay())) {
								pBanner = m;
								
								for(int i =0; i<svrs.length; i++){
									if(svrs[i] == sm.getId())
										status = true;
								}
								if(!status)
									continue;
								
								sModel.add(sftpModel);
							}
						}
					}
				}

				// 如果有满足条件的这里统一发送
				if (pBanner != null && sModel.size() > 0) {
					bannerModelMgr.writeFile(BannerModel.createBanners(pBanner));
					SftpTools.exec(sModel, BannerModelManager.writeSingle);

					// 发送完毕后清除数据为下一次计算做准备
					sModel.clear();
					pBanner = null;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}*/

}
