package lockTest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JConloseTest2 {

	private static Lock lock = new ReentrantLock();
	private static Object lockObject = new Object();

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread(new SynAddRunalbe(lockObject, lock)).start();
			new Thread(new SynAddRunalbe(lockObject, lock)).start();
		}
	}

}

class SynAddRunalbe implements Runnable {

	private Object lockObject;
	private Lock lock;

	 SynAddRunalbe(Object lockObject, Lock lock) {
		this.lockObject = lockObject;
		this.lock = lock;
	}

	@Override
	public void run() {
		lock.lock();
		lockObject.getClass();
	}
}