package four;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangxp
 * @date 2017年8月10日 上午9:13:22
 *       <p>
 *       展现lock的同步效果，一个线程打印完了，其他线程才可以继续打印。
 */
public class LockDemo1 {

	public static void main(String[] args) {
		MyServcie1 myServcie1 = new MyServcie1();
		MyThread1 a1 = new MyThread1(myServcie1);
		MyThread1 a2 = new MyThread1(myServcie1);
		MyThread1 a3 = new MyThread1(myServcie1);
		MyThread1 a4 = new MyThread1(myServcie1);
		MyThread1 a5 = new MyThread1(myServcie1);
		a1.start();
		a2.start();
		a3.start();
		a4.start();
		a5.start();

	}

}

class MyServcie1 {
	private Lock lock = new ReentrantLock();

	public void testMethod() {
		System.out.println("lock 之上");
		lock.lock();
		for (int i = 0; i < 5; i++) {
			System.out.println("ThreadName = " + Thread.currentThread().getName());
		}
		lock.unlock();
	}
}

class MyThread1 extends Thread {
	private MyServcie1 myServcie1;

	public MyThread1(MyServcie1 myServcie1) {
		super();
		this.myServcie1 = myServcie1;

	}

	@Override
	public void run() {
		myServcie1.testMethod();
	}
}