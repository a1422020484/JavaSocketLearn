package lockTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.util.concurrent.DefaultThreadFactory;

public class ThreadOOM {

	// -XX:+AlwaysPreTouch -Xmx20m -Xms20m -Xmn2m -XX:-OmitStackTraceInFastThrow -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps 
	//-XX:SurvivorRatio=6 -XX:ThreadStackSize=256 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC -XX:NewSize=1024 -XX:OldSize=19327352832
	
	private static AtomicInteger num = new AtomicInteger();

	public static void main(String[] args) {

		while (true) {
			ExecutorService es = Executors.newFixedThreadPool(10, new DefaultThreadFactory("main thread"));
			es.execute(new Runnable() {

				@Override
				public void run() {
					int a = 1;
					int b = 1;
					int c = a + b;
					// System.out.println(a + b);
				}
			});
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int i = num.incrementAndGet();
			if (i % 1000 == 0) {
				System.out.println(num.incrementAndGet() + "||" + Runtime.getRuntime().freeMemory() / 1024 + "K;");
			}
//			es.shutdown();
//			es = null;
		}
	}
}
