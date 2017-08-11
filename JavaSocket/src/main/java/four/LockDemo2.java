package four;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangxp
 * @date 2017年8月10日 上午9:23:17
 * Lock 和 synchronized 都是对象锁
 */
public class LockDemo2 {

	public static void main(String[] args) {
		MyService2 myService2 = new MyService2();
		MyThread2A a2 = new MyThread2A(myService2);
		MyThread2AA a2a = new MyThread2AA(myService2);
		MyThread2B b2 = new MyThread2B(myService2);
		MyThread2BB b2b = new MyThread2BB(myService2);
		a2.start();
		a2a.start();
		b2.start();
		b2b.start();
	}

}

class MyService2 {
	private Lock lock = new ReentrantLock();

	public void testMethodA() {
		try {
			lock.lock();
			System.out.println("MethodA begin ThreadName = " + Thread.currentThread().getName() + " Time = " + System.currentTimeMillis());
			Thread.sleep(5000);
			System.out.println("MethodA end   ThreadName = " + Thread.currentThread().getName() + " Time = " + System.currentTimeMillis());
			lock.unlock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void testMethodB() {
		try {
			lock.lock();
			System.out.println("MethodA begin ThreadName = " + Thread.currentThread().getName() + " Time = " + System.currentTimeMillis());
			Thread.sleep(5000);
			System.out.println("MethodA end   ThreadName = " + Thread.currentThread().getName() + " Time = " + System.currentTimeMillis());
			lock.unlock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class MyThread2A extends Thread {
	private MyService2 myService2;

	public MyThread2A(MyService2 myService2) {
		this.myService2 = myService2;
	}

	@Override
	public void run() {
		myService2.testMethodA();
	}
}

class MyThread2AA extends Thread {
	private MyService2 myService2;

	public MyThread2AA(MyService2 myService2) {
		this.myService2 = myService2;
	}

	@Override
	public void run() {
		myService2.testMethodA();
	}
}

class MyThread2B extends Thread {
	private MyService2 myService2;

	public MyThread2B(MyService2 myService2) {
		this.myService2 = myService2;
	}

	@Override
	public void run() {
		myService2.testMethodB();
	}
}

class MyThread2BB extends Thread {
	private MyService2 myService2;

	public MyThread2BB(MyService2 myService2) {
		this.myService2 = myService2;
	}

	@Override
	public void run() {
		myService2.testMethodB();
	}
}
