package threadExecutor;

import java.util.concurrent.atomic.AtomicInteger;

public class RetryTest {

	private static AtomicInteger numi = new AtomicInteger();
	private static AtomicInteger numj = new AtomicInteger();
	private static AtomicInteger numf = new AtomicInteger();
	private static AtomicInteger numg = new AtomicInteger();

	public static void main(String[] args) {
		doRetry();
		doBreak();
	}

	public static void doRetry() {
		outLoop: for (; numi.get() < 4;) {
			System.out.println(numi.get());
			for (; numj.get() < 3;) {
				if (numi.get() == 2) {
					continue outLoop;
				}
				numj.incrementAndGet();
			}
			numi.incrementAndGet();
		}
		System.out.println("fffff");
	}

	public static void doBreak() {
		outLoop: while (numf.get() < 4) {
			System.out.println(numf.get());
			while (numg.get() < 3) {
				if (numf.get() == 2) {
					break outLoop;
				}
				numf.incrementAndGet();
				numg.incrementAndGet();
			}
		}
		System.out.println("dddd");
	}

}
