package secondPart;

/**
 * @author yangxp
 * @date 2017年8月3日 下午5:18:00
 * <ul>
 * <li>A 线程现持有 <i>ThreadDemo3Object</i> 对象的Lock锁，B线程可以以异步的方式调用object对象中的非synchronized方法
 * <li>A 线程现持有 <i>ThreadDemo3Object</i> 对象的Lock锁，B线程如果调用object对象中的synchronized方法，则也需要等待。
 * </ul>
 */
public class ThreadDemo3 {
	
	public static void main(String[] args) {
		ThreadDemo3Object threadDemo2Object = new ThreadDemo3Object();
		ThreadA3 aThreadA2 = new ThreadA3(threadDemo2Object);
		aThreadA2.setName("A");
		ThreadB3 aThreadB2 = new ThreadB3(threadDemo2Object);
		aThreadB2.setName("B");
		aThreadA2.start();
		aThreadB2.start();
	}

}

class ThreadDemo3Object {
	public synchronized void methodA() {
		try {
			System.out.println("begin methodA threadName = " + Thread.currentThread().getName());
			System.out.println("begin methodA methodATime = " + System.currentTimeMillis());
			Thread.sleep(5000);
			System.out.println("end " + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 可以调用异步的非synchronized方法
	 */
	public void methodB() {
		try {
			System.out.println("begin methodB threadName = " + Thread.currentThread().getName());
			System.out.println("begin methodB methodBTime = " + System.currentTimeMillis());
			Thread.sleep(5000);
			System.out.println("end " + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ThreadA3 extends Thread {
	private ThreadDemo3Object threadDemo2Object;

	public ThreadA3(ThreadDemo3Object threadDemo2Object) {
		super();
		this.threadDemo2Object = threadDemo2Object;
	}

	@Override
	public void run() {
		super.run();
		threadDemo2Object.methodA();
		// threadDemo2Object.methodB();
	}
}

class ThreadB3 extends Thread {
	private ThreadDemo3Object threadDemo2Object;

	public ThreadB3(ThreadDemo3Object threadDemo2Object) {
		super();
		this.threadDemo2Object = threadDemo2Object;
	}

	@Override
	public void run() {
		super.run();
		threadDemo2Object.methodB();
	}
}