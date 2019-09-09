package four;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo1 {

	private static Lock lock = new ReentrantLock();
	private static Object object = new Object();
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable(){

			@Override
			public void run() {
				lock.lock();
				lock.lock();
				System.out.println("t1 == " + object.getClass());
				lock.unlock();
				lock.unlock();
			}
			
		});
		
		Thread t2 = new Thread(new Runnable(){

			@Override
			public void run() {
				lock.lock();
				System.out.println("t2 == " + object.getClass());
//				lock.unlock();
			}
			
		});
		
		t1.start();
		t2.start();
	}

}
