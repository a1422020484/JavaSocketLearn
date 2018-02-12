package nettyServer.system;

import org.springframework.stereotype.Component;

import nettyServer.listener.ServerStartupListener;
import nettyServer.util.GameUtils;
import nettyServer.util.timer.TimerHandler;

/**
 * 测试数据库连接服务
 *
 * @author yangxp
 */
@Component
public class DataBaseTestHandler implements TimerHandler, ServerStartupListener {

//	@Resource DBDAO DBDAO;
	
	/*
	 * 循环在一定的时间周期内使用"SELECT NOW() FROM DUAL"语句查询,当查询失败时,判断为数据库连接失败,允许有一定的尝试时间,多次尝试失败后关闭服务器.
	 */
	@Override
	public void handle(long timerRuleTime) throws Exception {
		try {
//			DBDAO.test();
		} catch (Exception e) {
			int errorCount = 0;
			for (int i = 0; i < 10; i ++) {
				try {
					Thread.sleep(3000L);
				} catch (Exception e1) {
				}
				try {
//					DBDAO.test();
					return; // success
				} catch (Exception e2) {
					errorCount ++;
					GameUtils.RuntimeLog.error("数据库连接失败,尝试连接...{}", errorCount);
				}
			}
			GameUtils.RuntimeLog.error("数据库连接失败,停止服务器!");
			System.exit(0);
		}
	}

	@Override
	public void started() {
//		DBDAO.setNames();
	}
	
}
