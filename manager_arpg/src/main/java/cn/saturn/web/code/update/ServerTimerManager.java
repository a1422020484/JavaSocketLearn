package cn.saturn.web.code.update;

import java.util.Collection;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import zyt.component.service.timer.TimerManager;

@Component
public class ServerTimerManager extends TimerManager<ServerTimer> implements ApplicationContextAware {
	// private static List<IUpdate> list = new ArrayList<IUpdate>();
	private static Thread thread = null;
	private static boolean isRun = true;

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		list.clear();
		Collection<ServerTimer> components = ctx.getBeansOfType(ServerTimer.class).values();
		list.addAll(components);

		// 处理器
		TimerManager.ITrigger<ServerTimer> trigger = new TimerManager.ITrigger<ServerTimer>() {
			@Override
			public boolean handle(ServerTimer timer, int count) {
				timer.update(count);
				return true;
			}
		};

		// 创建线程
		if (thread == null) {
			thread = new Thread("serverTimeThread") {
				@Override
				public void run() {
					long prevTimeL = System.currentTimeMillis();
					while (isRun) {
						// 处理一次
						long nowTimeL = System.currentTimeMillis();
						ServerTimerManager.super.checkTimer(prevTimeL, nowTimeL, trigger);
						prevTimeL = nowTimeL;

						try {
							Thread.sleep(5000); // 5s处理一次(间隔最少5s)
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			};
			thread.setDaemon(true);
			thread.start(); // 暂时不用
		}
	}

}
