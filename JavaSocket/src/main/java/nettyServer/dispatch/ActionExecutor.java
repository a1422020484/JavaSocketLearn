package nettyServer.dispatch;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zuojie.x
 */
class ActionExecutor<T extends ActionTask> {
	int id;
	AtomicInteger counter; // 玩家数量计数
	ExecutorService executorService; // 执行服务

	ActionExecutor(int id) {
		this.id = id;
		counter = new AtomicInteger();
		executorService = Executors.newSingleThreadExecutor(new DefaultThreadFactory(id));
	}
	
	ActionExecutor(int id, int threadN) {
		this.id = id;
		counter = new AtomicInteger();
//		executorService = Executors.newSingleThreadExecutor(new DefaultThreadFactory(id));
		if (threadN <= 0) {
			threadN = 1;
		}
		executorService = Executors.newFixedThreadPool(threadN, new DefaultThreadFactory(id));
	}

	/**
	 * 执行一个任务
	 * 
	 * @param t
	 */
	void execute(final T t) {
		executorService.execute(t);
	}

	/**
	 * 数量加1
	 */
	void inc() {
		counter.incrementAndGet();
	}

	/**
	 * 数量减1
	 */
	void dec() {
		counter.decrementAndGet();
	}

	/**
	 * @return 玩家数量
	 */
	int count() {
		return counter.get();
	}

	/**
	 * 停止服务
	 */
	void stop() {
		try {
			// 问题,当调用shutdown()后,剩下的任务还要继续完成,在这些任务完成中可能继续提交任务,这时新任务将被拒绝
			executorService.shutdown();
			// 每次等1秒,共10次
			for (int i = 0; i < 10; i ++) {
				if (executorService.awaitTermination(1L, TimeUnit.SECONDS)) {
					break;
				}
			}
			if (!executorService.isTerminated()) {
				List<Runnable> remaining = executorService.shutdownNow();
				if (remaining != null && remaining.size() > 0) {
					// 剩余的任务处理?等这么久,再等也是浪费时间了,记录下未意义也不大,真正导致无法停止的是正在执行的任务,而无法获得
				}
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow();
			e.printStackTrace();
		}
	}
}
