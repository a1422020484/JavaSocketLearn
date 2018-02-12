package cn.saturn.web.controllers.statistics.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.saturn.operation.OperationExt;
import cn.saturn.web.code.update.ServerTimer;
import cn.saturn.web.controllers.server.dao.ServerDAO;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.SystemLogParams;
import cn.saturn.web.utils.Config;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.HourUtils;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.Utils;

/**
 * 第二天执行 存储过程的 时间管理类(数据采集) <br>
 * 1. 执行新增首次付费 <br>
 * 2. 执行30日LTV <br>
 * 3. 统计新付费人数 <br>
 * 4. 新服务器 7日活跃 <br>
 * 5. 新服务器收入<br>
 * 6. 执行留存统计 <br>
 * 7. 收集各游戏中的日志,并形成汇总 <br>
 * 
 * <span> 添加配置,是否开启统计 </span>
 * 
 * @author rodking
 */
@Component
public class NextDayTimer extends ServerTimer {
	protected static final Logger logger = LoggerFactory.getLogger(LogType.timeReport);

	@Resource
	LogDbSummaryManager summMgr;

	@Resource
	ServerDAO serverDao;

	@Resource
	javax.sql.DataSource dataSource;
	
	private static String poke_mgr = Config.val("poke_mgr");
	private static String homemgr=StringUtils.isNotEmpty(poke_mgr)?poke_mgr:"poke_mgr";

	@Resource
	protected NextDayTimerDAO dao;
	private boolean isExec = false;
	private long timeStamp;
	private final boolean isOpen;

	public NextDayTimer() {
        // TODO 测试用 20 秒执行一次
		//super(1, "nextDayTimer", 20 * 1000);
		// 每小时定时
		super(1, "nextDayTimer", 60 * 60 * 1000);
		timeStamp = HourUtils.getNowHour();
		isOpen = Utils.config.get("report.open", false);

	}
	
	public void update(){
		isExec = true;
		this.update(1);
	}

	@Override
	protected void update(int count) {
        System.err.println("isOpen->"+isOpen);
		// 不开启汇总统计
		if (!isOpen)
			return;

		Calendar nowC = Calendar.getInstance();
		nowC.setTime(new Date());

		long dHour = HourUtils.getHour(nowC.getTimeInMillis()) - timeStamp;
		int curHour = nowC.get(Calendar.HOUR_OF_DAY);

		System.out.println("curHour->"+curHour+",dHour->"+dHour);
		// 每天的4点执行一次
		if (curHour==4 || dHour > 24) {
			isExec = true;
		}

		// 是否要执行数据采集
		if (isExec) {
			isExec = false;
			System.out.println("nextDayTimer excute start...");
			timeStamp = HourUtils.getNowHour();
			long dStart = System.currentTimeMillis();
			execACU();
			execFistPayRetention();
			execLTV();
			execNewPay();
			execServerDAU();
			execNewServerDAU();
			execNewServerIncome();
			execRetentation();
			collectGameLog();
			execUserChange();
			execDetailedItem();
			execGamePerHourOnline();
			execStatSubChannel();
            execStatComp();
            execStatRetention();
            execStatRetentionPlatform();
            execStatPayRetention();
			try {
				logger.info("[ 开始执行时间 ]:" + dStart);
				logger.info("[ 汇总数据用时 ]:" + (System.currentTimeMillis() - dStart) + " ms..");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	 * 小时活跃
	 */
	public void execACU() {
		try {
			dao.playerACU(homemgr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 执行新增首次付费
	 */
	public void execFistPayRetention() {
		try {
			dao.firstPayRetention(homemgr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 执行30日LTV
	 */
	public void execLTV() {
		try {
			dao.startLTV30(homemgr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 统计新付费人数
	 */
	public void execNewPay() {
		try {
			dao.newPay(homemgr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 新服务器 7日活跃
	 */
	public void execNewServerDAU() {
		try {
			dao.newServerDAU(homemgr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * DAU统计
	 */
	public void execServerDAU() {
		try {
			dao.serverDAU(homemgr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * 新服务器收入
	 */
	public void execNewServerIncome() {
		try {
			dao.newServerIncome(homemgr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 执行留存统计
	 */
	public void execRetentation() {
		try {
			dao.startRetentionPlayer(homemgr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 用户转化率表
	 */
	public void execUserChange() {
		try {
			dao.startNewUserChange(homemgr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 统计详细物品
	 */
	public void execDetailedItem() {
		try {
			// TODO 统计统计详细物品
			// TODO 清空当天(物品id 缓存表,物品 名称缓存表)
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 按子渠道统计
	 */
	public void execStatSubChannel() {
		try {
			dao.startSubChannel(homemgr);
			//dao.startSubChannel();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 按服务器统计对比
	 */
	public void execStatComp() {
		try {
			dao.startCompTask(homemgr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 按服务器统计留存
	 */
	public void execStatRetention() {
		try {
			String date = DateUtils.getYesterdayStr();
			dao.statRetention(homemgr,date);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 按渠道统计留存
	 */
	public void execStatRetentionPlatform() {
		try {
			String date = DateUtils.getYesterdayStr();
			dao.statRetentionplatform(homemgr,date);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 按服务器统计充值留存
	 */
	public void execStatPayRetention() {
		try {
			String date = DateUtils.getYesterdayStr();
			dao.statPayRetention(homemgr,date);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 游戏每小时在线 dcu & dau
	 */
	@SuppressWarnings("static-access")
	public void execGamePerHourOnline() {
		try {
			JdbcTemplate template = new JdbcTemplate(dataSource);

			List<ServerModel> serverModels = serverDao.getList();
			int key = 0;
			String yesterdayStr = DateUtils.getYesterdayStr();
			for (ServerModel model : serverModels) {
				// 找到日志登录日志 key
				for (int i = 0; i < SystemLogParams.getKeys().length; i++) {
					if (SystemLogParams.getKeys()[i].equals("系统登录日志"))
						key = i;
				}

				System.out.println(model.getName() + "");

				int serverId = (int) model.getId();
				String result = OperationExt.queryLogFile(key, serverId, yesterdayStr);

				if (result.contains("@"))
					continue;

				List<String[]> results = OperationExt.getResultMsg(result);
				List<String> sqls = new ArrayList<String>();

				for (String[] str : results) {
					StringBuilder strBuilder = new StringBuilder();
					strBuilder.append("INSERT INTO game_online_user_cache(t_time,num,server_id)values('");
					strBuilder.append(str[0]).append("','").append(str[1]).append("','");
					strBuilder.append(serverId).append("');");
					sqls.add(strBuilder.toString());

					// 大于批量数提交数据
					if (sqls.size() >= summMgr.batch_max_size) {
						OperationExt.batchUpdate(template, sqls.toArray(new String[] {}));
						sqls.clear();
					}
				}
				// 提交剩余数据
				OperationExt.batchUpdate(template, sqls.toArray(new String[] {}));
				sqls.clear();
			}

			dao.startGamePerHourOnline(homemgr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 收集游戏日志
	 */
	public void collectGameLog() {
		// 收集日志,汇总报表
		try {
			summMgr.init();
			summMgr.reLoad();
			summMgr.exec();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
}
