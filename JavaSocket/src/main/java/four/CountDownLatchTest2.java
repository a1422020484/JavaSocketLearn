package four;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest2 {
	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		CountDownLatch cdl = new CountDownLatch(100);
		for (int i = 0; i < 100; i++) {
			CountRunnable runnable = new CountRunnable(cdl);
			pool.execute(runnable);
		}
		pool.shutdown();
	}
}

class CountRunnable implements Runnable {
	private CountDownLatch countDownLatch;

	public CountRunnable(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		// countDown 是原子的   但是 countDown 和  getCount 这个组合操作不是原子的
		synchronized (countDownLatch) {
			countDownLatch.countDown();
			System.out.println("thread counts = " + (countDownLatch.getCount()));
		}

		try {
			countDownLatch.await();
			System.out.println("concurrency counts = " + (100 - countDownLatch.getCount()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}