package secondPart;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yangxp
 * @date 2017年8月7日 下午4:44:58
 * AtomicInteger i++ 的原子性
 */
public class ThreadAtomic {

	public static void main(String[] args) {
		ThreadAtomicTest threadAtomicTest = new ThreadAtomicTest();
		Thread t1 = new Thread(threadAtomicTest);
		t1.start();
		Thread t2 = new Thread(threadAtomicTest);
		t2.start();
		Thread t3 = new Thread(threadAtomicTest);
		t3.start();
		Thread t4 = new Thread(threadAtomicTest);
		t4.start();
		Thread t5 = new Thread(threadAtomicTest);
		t5.start();
	}

}

class ThreadAtomicTest extends Thread {
	private AtomicInteger count = new AtomicInteger(0);

	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			System.out.println(count.incrementAndGet());
		}
	}
}
