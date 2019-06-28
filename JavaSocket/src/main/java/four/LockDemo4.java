package four;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo4 {

	public static void main(String[] args) {
		try {
			MyService4 myService4 = new MyService4();
			MyThread4A myThread4A = new MyThread4A(myService4);
			MyThread4B myThread4B = new MyThread4B(myService4);
			myThread4A.setName("A");
			myThread4A.start();
			myThread4B.setName("B");
			myThread4B.start();
			Thread.sleep(5000);
			myService4.testMethodCA();
			myService4.testMethodCB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class MyService4 {
	private Lock lock = new ReentrantLock();
	private Condition conditionA = lock.newCondition();
	private Condition conditionB = lock.newCondition();

	public void testMethodA() {
		try {
			lock.lock();
			System.out.println("MethodA begin ThreadName = " + Thread.currentThread().getName() + " Time = " + System.currentTimeMillis());
			conditionA.await();
			System.out.println("MethodA end   ThreadName = " + Thread.currentThread().getName() + " Time = " + System.currentTimeMillis());
			lock.unlock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void testMethodB() {
		try {
			lock.lock();
			System.out.println("MethodB begin ThreadName = " + Thread.currentThread().getName() + " Time = " + System.currentTimeMillis());
			conditionB.await();
			System.out.println("MethodB end   ThreadName = " + Thread.currentThread().getName() + " Time = " + System.currentTimeMillis());
			lock.unlock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void testMethodCA() {
		lock.lock();
		System.out.println("MethodA begin ThreadName = " + Thread.currentThread().getName() + " Time = " + System.currentTimeMillis());
		conditionA.signalAll();
		System.out.println("MethodA end   ThreadName = " + Thread.currentThread().getName() + " Time = " + System.currentTimeMillis());
		lock.unlock();
	}
	public void testMethodCB() {
		lock.lock();
		System.out.println("MethodA begin ThreadName = " + Thread.currentThread().getName() + " Time = " + System.currentTimeMillis());
		conditionB.signalAll();
		System.out.println("MethodA end   ThreadName = " + Thread.currentThread().getName() + " Time = " + System.currentTimeMillis());
		lock.unlock();
	}
}

class MyThread4A extends Thread {
	private MyService4 myService4;

	public MyThread4A(MyService4 myService4) {
		this.myService4 = myService4;
	}

	@Override
	public void run() {
		myService4.testMethodA();
	}
}

class MyThread4B extends Thread {
	private MyService4 myService4;

	public MyThread4B(MyService4 myService4) {
		this.myService4 = myService4;
	}

	@Override
	public void run() {
		myService4.testMethodB();
	}
}
