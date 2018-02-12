package cn.saturn.web.controllers.poke.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.saturn.web.code.update.ServerTimer;
import cn.saturn.web.controllers.banner.dao.BannerModelManager;
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
 * 热点精灵 提前 1 天的 0 点刷新 <br>
 * 
 * @author rodking
 */
@Component
public class NextDayPokeTimer extends ServerTimer {
	protected static final Logger logger = LoggerFactory.getLogger(LogType.timeReport);

	@Resource
	ServerXFtpDAO serverXFtpDao;

	@Resource
	PokeHunterModelManager pokeModelMgr;

	private boolean isExec = false;
	private long timeStamp;
	private final boolean isOpen;

	public NextDayPokeTimer() {
		// 每小时定时
		super(1, "nextDayTimer", 60 * 60 * 1000);
		timeStamp = HourUtils.getNowHour();
		isOpen = Utils.config.get("report.open", false);
		// TODO 测试用 20 秒执行一次
		// super(1, "nextDayTimer", 20 * 1000);

	}

	@Override
	protected void update(int count) {

		// 不开启上传配置
		if (!isOpen)
			return;

		Calendar nowC = Calendar.getInstance();
		nowC.setTime(new Date());

		long dHour = HourUtils.getHour(nowC.getTimeInMillis()) - timeStamp;
		int curHour = nowC.get(Calendar.HOUR_OF_DAY);

		isExec = false;

		// 每天的0点执行一次
		if (curHour == 22 || dHour > 24) {
			isExec = true;
		}

		//
		if (isExec) {
			timeStamp = HourUtils.getNowHour();
			long dStart = System.currentTimeMillis();

			pokeUpdateByTime();
			pokeUpdateByDate();

			logger.info("[ 上传文件poke开始时间 ]:" + dStart);
			logger.info("[ 上传文件poke结束时间 ]:" + (System.currentTimeMillis() - dStart) + " ms..");
		}

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
	public void pokeUpdateByTime() {
		try {
			// 1.按照时间发送
			List<PresetModel> pModels = PresetManager.getPresetList(PresetManager.presetType_poke);
			PresetPokeHunter pPoke = null;

			for (PresetModel model : pModels) {
				PresetPokeHunter m = model.getValue(PresetPokeHunter.class);

				if (m != null) {
					// 因为要提前一天执行 ,匹配时间使用 Tomorrow
					if (m.getOpen_state() == 0 && m.getOpenTime().equals(DateUtils.getTomorrowStr())) {
						pPoke = m;
						break;
					}
				}
			}

			if (pPoke == null)
				return;

			// 要发送的服务器
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			List<ServerModel> srvlist = serverComponent.getList();
			List<ServerXFtpModel> sModel;
			
			int[] openSvrs = pPoke.getSrvs();
			if(openSvrs == null || openSvrs.length == 0)
				return;
			boolean isSvr = false;
			
			for (ServerModel sm : srvlist) {
				sModel = new ArrayList<>();
				ServerXFtpModel model = serverXFtpDao.getBySid((int) sm.getId());
				
				for(int i : openSvrs){
					if(i == sm.getId()){
						isSvr = true;
						break;
					}
				}
				
				if (isSvr && model != null)
					sModel.add(model);
				
				isSvr = false;
				
				System.out.println("Next Day Poke svrId->"+sm.getId());
				pokeModelMgr.writeReplace(pPoke);
				SftpTools.exec(sModel, PokeHunterModelManager.writeSingle);		
			}

			// pokeModelMgr.sendRefreshCode(srvlist);

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private void pokeUpdateByDate() {
		try {
			// 2.按照开服时间
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			List<PresetModel> pModels = PresetManager.getPresetList(PresetManager.presetType_poke);
			PresetPokeHunter pPoke = null;

			List<ServerModel> srvlist = serverComponent.getList();
			List<ServerXFtpModel> sModel = new ArrayList<>();

			// 遍历poke 模块
			for (PresetModel model : pModels) {
				PresetPokeHunter m = model.getValue(PresetPokeHunter.class);
				if (m != null) {
					
					int[] openSvrs = m.getSrvs();
					if(openSvrs == null || openSvrs.length == 0)
						return;
					boolean isSvr = false;
					
					for (ServerModel sm : srvlist) {
						// 遍历服务器模块
						ServerXFtpModel sftpModel = serverXFtpDao.getBySid((int) sm.getId());
						sModel = new ArrayList<>();
						if (sftpModel != null) {
							String openTimeStr = DateUtils.getAddDayStr(sm.getOpen_time(), m.getOpenDate());
							// 如果时间能匹配上 因为要提前一天执行 ,匹配时间使用 Tomorrow
							if (m.getOpen_state() == 1 && openTimeStr.equals(DateUtils.getTomorrowStr())) {
								pPoke = m;
								
								for(int i : openSvrs){
									if(i == sm.getId()){
										isSvr = true;
										break;
									}
								}
								
								if(isSvr)
								sModel.add(sftpModel);
								isSvr = false;
								
								// 如果有满足条件的这里统一发送
								if (pPoke != null && sModel.size() > 0) {
									System.out.println("Next Day Poke svrId->"+sm.getId());
									pokeModelMgr.writeReplace(pPoke);
									SftpTools.exec(sModel, PokeHunterModelManager.writeSingle);
									// pokeModelMgr.sendRefreshCode(srvlist);
									// 发送完毕后清除数据为下一次计算做准备
									sModel.clear();
//									pPoke = null;
								}							
							}
						}
					}
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

}
