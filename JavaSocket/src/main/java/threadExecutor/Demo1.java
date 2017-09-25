package threadExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Demo1 {

	public static void main(String[] args) {
		// Demo1.doCachedThreadPool();
		// Demo1.doFixedThreadPool();
		Demo1.doScheduledThreadPool();
		// Demo1.doSingleThreadExecutor();
		System.out.println("after main");
	}

	public static void doCachedThreadPool() {
		ExecutorService cachedExecutorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			final int index = i;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cachedExecutorService.execute(new Runnable() {

				@Override
				public void run() {
					System.out.println(index);
				}
			});
		}
		cachedExecutorService.shutdown();
	}

	public static void doFixedThreadPool() {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 10; i++) {
			final int index = i;
			fixedThreadPool.execute(new Runnable() {
				public void run() {
					try {
						System.out.println(index);
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		fixedThreadPool.shutdown();
	}

	public static void doScheduledThreadPool() {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
		scheduledExecutorService.schedule(new Runnable() {
			public void run() {
				System.out.println("delay 3 seconds");
			}
		}, 3, TimeUnit.SECONDS);
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				System.out.println("delay 1 seconds and execute every 3 times");
			}
		}, 1, 1, TimeUnit.SECONDS);
	}

	public static void doSingleThreadExecutor() {
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 10; i++) {
			final int index = i;
			singleThreadExecutor.execute(new Runnable() {
				public void run() {
					try {
						System.out.println(index);
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
