package secondPart;

/**
 * @author yangxp
 * @date 2017年8月3日 下午5:08:50
 * <p>
 * 只有共享资源的时候才需要同步，区别加synchronized 和 不加。
 * </p>
 */
public class ThreadDemo2 {
	
	public static void main(String[] args) {
		ThreadDemo2Object threadDemo2Object = new ThreadDemo2Object();
		ThreadA2 aThreadA2 = new ThreadA2(threadDemo2Object);
		aThreadA2.setName("A");
		ThreadB2 aThreadB2 = new ThreadB2(threadDemo2Object);
		aThreadB2.setName("B");
		aThreadA2.start();
		aThreadB2.start();

	}
}

class ThreadDemo2Object {
	public void methodA() {
		try {
			System.out.println("begin methodA threadName = " + Thread.currentThread().getName());
			Thread.sleep(5000);
			System.out.println("end " + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public synchronized void methodB() {
		try {
			System.out.println("begin methodA threadName = " + Thread.currentThread().getName());
			Thread.sleep(5000);
			System.out.println("end " + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ThreadA2 extends Thread {
	private ThreadDemo2Object threadDemo2Object;

	public ThreadA2(ThreadDemo2Object threadDemo2Object) {
		super();
		this.threadDemo2Object = threadDemo2Object;
	}

	@Override
	public void run() {
		super.run();
//		threadDemo2Object.methodA();
		threadDemo2Object.methodB();
	}
}

class ThreadB2 extends Thread {
	private ThreadDemo2Object threadDemo2Object;

	public ThreadB2(ThreadDemo2Object threadDemo2Object) {
		super();
		this.threadDemo2Object = threadDemo2Object;
	}

	@Override
	public void run() {
		super.run();
//		threadDemo2Object.methodA();
		threadDemo2Object.methodB();
	}
}