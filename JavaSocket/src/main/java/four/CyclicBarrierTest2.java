package four;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangxp
 *<p> reset 的使用  等待过程中reset 会报错 BrokenBarrierException
 */
public class CyclicBarrierTest2 {
	private static final Logger LOGGER = LoggerFactory.getLogger("Test2");

	public static void main(String[] args) throws InterruptedException {
		CyclicBarrier barrier2 = new CyclicBarrier(2);
		LOGGER.info("如果是一个初始的CyclicBarrier，则reset()之后，什么也不会发生");
		barrier2.reset();
		System.out.println();

		Thread.sleep(1000);

		ExecutorService ex2 = Executors.newCachedThreadPool();

		for (int i = 0; i < 2; i++) {
			ex2.submit(() -> {
				try {
					barrier2.await();
					LOGGER.info("222屏障已经打开.");
				} catch (InterruptedException e) {
					LOGGER.info("222被中断");
				} catch (BrokenBarrierException e) {
					LOGGER.info("222被重置");
				}
			});
		}

		barrier2.reset();

		Thread.sleep(1000);
		System.out.println();
		ex2.submit(() -> {
			try {
				barrier2.await();
				LOGGER.info("333屏障已经打开.");
			} catch (InterruptedException e) {
				LOGGER.info("333被中断");
			} catch (BrokenBarrierException e) {
				LOGGER.info("在等待过程中，执行reset()方法，等待的线程抛出BrokenBarrierException异常，并不再等待");
			}
		});

		Thread.sleep(1000);
		barrier2.reset();
		ex2.shutdown();
	}
}
