package nettyServer.util.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import nettyServer.util.GameConfig;
import nettyServer.util.GameExplorer;
import nettyServer.util.GameThreadFactory;
import nettyServer.util.timer.TimerHandler;

/**
 * 缓存数据定时保存处理器
 *
 * @author yangxp
 */
@Component
public class CachedDataPersistTimeHandler implements TimerHandler {
	
	private long lastPersistTime;

    static ExecutorService executorService = Executors.newSingleThreadExecutor(new GameThreadFactory("Cached Data Persist Timer", 4));

	@Override
	public void handle(long timerRuleTime) throws Exception {
		long now = System.currentTimeMillis() / 1000 * 1000;
		if (lastPersistTime == 0L) {
			lastPersistTime = now;
			return;
		}
		if (CachedDataPersistStrategy.strategy == 2) {
			int interval = GameConfig.intVal("CachedDataPersistInterval") * 60000;
			if (now < lastPersistTime + interval) {
				return;
			}
			if (CachedDataPersistService.saveFlag.get()) {
				return; // saving
			}
			lastPersistTime = now;

            executorService.execute(() -> CachedDataPersistService.save(false));
		}
	}

	public static void landImmediately(){

		executorService.execute(() -> CachedDataPersistService.save(false));

	}

}
