package four;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangeTest {
	private static final Exchanger<String> exgr = new Exchanger<String>();
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(2);

	public static void main(String[] args) {
		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					String A = "银行流水A";
					exgr.exchange(A);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});

		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					String B = "银行流水B";
					String A;
					A = exgr.exchange("B");
					System.out.println("A和B的数据是否一致：" + A.equals(B) + "  A 录入的是 ： " + A + ", B 录入的是 " + B);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});
		threadPool.shutdown();
	}
}
