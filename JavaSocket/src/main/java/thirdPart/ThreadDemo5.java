package thirdPart;

/**
 * @author yangxp
 * @date 2017年8月8日 下午4:12:42
 * wait(long) 的使用方法，即时有时间性的等待
 */
public class ThreadDemo5 {

	public static void main(String[] args) {
		Object lock = new Object();
		MyRunnable5A myRunnable5A = new MyRunnable5A(lock);
		MyRunnable5B myRunnable5B = new MyRunnable5B(lock);
		Thread a = new Thread(myRunnable5A.getRunnable());
		a.start();
		Thread b = new Thread(myRunnable5B.getRunnable());
		b.start();
	}

}

class MyRunnable5A {
	private Object lock;

	public MyRunnable5A(Object lock) {
		this.lock = lock;
	}

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			try {
				synchronized (lock) {
					System.out.println("wait begin timer = " + System.currentTimeMillis() + " " + Thread.currentThread().getName());
					lock.wait(2000);
					System.out.println("wait end   timer = " + System.currentTimeMillis() + " " + Thread.currentThread().getName());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	public Runnable getRunnable() {
		return runnable;
	}
}

class MyRunnable5B {
	private Object lock;

	public MyRunnable5B(Object lock) {
		this.lock = lock;
	}

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			try {
				synchronized (lock) {
					System.out.println("notify begin timer = " + System.currentTimeMillis() + " " + Thread.currentThread().getName());
					lock.notify();
					System.out.println("notify end   timer = " + System.currentTimeMillis() + " " + Thread.currentThread().getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public Runnable getRunnable() {
		return runnable;
	}
}
