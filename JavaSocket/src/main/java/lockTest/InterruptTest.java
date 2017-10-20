package lockTest;

import java.util.concurrent.locks.*;

public class InterruptTest {
	public static void main(String[] args) throws Exception {
		MyThread05 mt = new MyThread05();
		mt.test3();
	}
}

class MyThread05 extends Thread {
	public void test3() throws Exception {
		final Lock lock = new ReentrantLock();
		lock.lock();
		Thread.sleep(1000);
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
//				lock.lock();
				try {
					lock.lockInterruptibly();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				try {
//					lock.lockInterruptibly();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				System.out.println(Thread.currentThread().getName() + " interrupted.");
			}
		});
		t1.start();
		Thread.sleep(1000);
		t1.interrupt();
//		t1.interrupt();
		Thread.sleep(1000000);
	}
}
