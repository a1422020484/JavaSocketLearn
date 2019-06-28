package thirdPart;

public class ThreadDemo2 {

	public static void main(String[] args) {
		try {
			Object lock = new Object();
			ThreadDemo2TestA threadDemo2TestA = new ThreadDemo2TestA(lock);
			threadDemo2TestA.start();
			Thread.sleep(300);
			ThreadDemo2TestB threadDemo2TestB = new ThreadDemo2TestB(lock);
			threadDemo2TestB.start();
			ThreadDemo2TestC threadDemo2TestC = new ThreadDemo2TestC(lock);
			threadDemo2TestC.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class ThreadDemo2TestA extends Thread {
	private Object lock;

	public ThreadDemo2TestA(Object lock) {
		super();
		this.lock = lock;
	}

	@Override
	public void run() {
		try {
			synchronized (lock) {
				System.out.println("开始A ： ==== wait time " + System.currentTimeMillis());
				lock.wait();
				System.out.println("结束A ： ==== wait time " + System.currentTimeMillis());
				lock.notify();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class ThreadDemo2TestB extends Thread {
	private Object lock;

	public ThreadDemo2TestB(Object lock) {
		super();
		this.lock = lock;
	}

	@Override
	public void run() {
		try {
			synchronized (lock) {
				System.out.println("开始B ： ==== wait time " + System.currentTimeMillis());
				lock.wait();
				System.out.println("结束B ： ==== wait time " + System.currentTimeMillis());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class ThreadDemo2TestC extends Thread {
	private Object lock;

	public ThreadDemo2TestC(Object lock) {
		super();
		this.lock = lock;
	}

	@Override
	public void run() {
		try {
			synchronized (lock) {
				System.out.println("开始C ： ==== wait time " + System.currentTimeMillis());
				lock.notify();
				System.out.println("结束C ： ==== wait time " + System.currentTimeMillis());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
