package nettyServer.util.timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;

import nettyServer.listener.ServerCloseListener;
import nettyServer.listener.ServerStartupListener;
import nettyServer.util.DateUtil;
import nettyServer.util.GameThreadFactory;
import nettyServer.util.resource.ResourceLoader;

@Component
public class TimerManager extends ResourceLoader implements ApplicationContextAware, ServerStartupListener, ServerCloseListener {
	protected Logger log = LoggerFactory.getLogger("game.timer");
	
	private ApplicationContext applicationContext;
	
	private List<TimerConfigure> timers;
	
	@Override
	public void load(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		Map<String, TimerHandler> beans = applicationContext.getBeansOfType(TimerHandler.class);
		
		List<TimerConfigure> timers = new ArrayList<>();
		for (String line = reader.readLine(); line != null; line = reader.readLine()) {
			line = line.trim();
			if (StringUtils.isEmpty(line) || line.startsWith("//"))
				continue;
			String[] arr = line.split(" ");
			if (arr.length != 6)
				throw new RuntimeException("不符合格式规则:" + line);
			String expression = line.substring(0, line.lastIndexOf(" "));
			// 补充'秒'
			expression = "0 " + expression;
			String handlersStr = line.substring(line.lastIndexOf(" ")).trim();
			String[] handlerStrArr = handlersStr.split(",");
			List<TimerHandler> handlers = new ArrayList<>();
			for (String handlerStr : handlerStrArr) {
				// 首字母小写
				handlerStr = handlerStr.substring(0, 1).toLowerCase() + handlerStr.substring(1);
				TimerHandler handler = beans.get(handlerStr);
				if (handler == null)
					throw new RuntimeException("没有找到bean:" + handlerStr);
				handlers.add(handler);
			}
			CronSequenceGenerator gen = new CronSequenceGenerator(expression, TimeZone.getDefault());
			timers.add(new TimerConfigure(expression, gen, handlers));
		}
		this.timers = timers;
		reader.close();
	}
	
	// 定时循环器
	private ScheduledExecutorService scheduleLoop;
	
	private Runnable timerRunnable = new Runnable() {
		@Override
		public void run() {
			if (timers == null)
				return;
			final long now = System.currentTimeMillis();
			for (TimerConfigure con : timers) {
				con.handle(now);
			}
		}
	};
	
	private class TimerConfigure {
		String expression;
		CronSequenceGenerator generator;
		List<TimerHandler> handlers;
		volatile long nextTime; // 下一次执行的时间
		
		TimerConfigure(
				String expression, 
				CronSequenceGenerator generator,
				List<TimerHandler> handlers) {
			this.expression = expression;
			this.generator = generator;
			this.handlers = handlers;
			nextTime = generator.next(new Date()).getTime();
		}
		
		void handle(long now) {
			if (now >= nextTime) {
				// 开始执行
				long currTime = nextTime;
				nextTime = generator.next(new Date(nextTime)).getTime();
				for (TimerHandler handler : handlers) {
					try {
						long start = System.currentTimeMillis();
						handler.handle(currTime);
						long end = System.currentTimeMillis();
						long d = end - start;
						log.info("{} {} {} 执行完成,耗时{}ms.",
								expression,
								DateUtil.format(new Date(currTime), DateUtil.Pattern_yyyy_MM_dd_HH_mm_ss),
								handler.getClass().getName(),
								d);
					} catch (Exception e) {
						e.printStackTrace();
						log.error("{} {} {} 异常:: {}", expression,
								DateUtil.format(new Date(currTime), DateUtil.Pattern_yyyy_MM_dd_HH_mm_ss),
								handler.getClass().getName(),
								e.getMessage());
					}
				}
			}
		}
		
	}

	@Override
	public String getResourceName() {
		return "Timers";
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		scheduleLoop = Executors.newSingleThreadScheduledExecutor(new GameThreadFactory("timer"));
	}

	@Override
	public void started() {
		scheduleLoop.scheduleWithFixedDelay(timerRunnable, 0L, 1L, TimeUnit.SECONDS);
		log.info("timer executor started.");
	}

	@Override
	public void close() {
		scheduleLoop.shutdown();
	}
}
