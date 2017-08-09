package thirdPart;

/**
 * @author yangxp
 * @date 2017年8月8日 下午3:36:14
 * <p>
 * 必须执行完notify()方法所在的同步synchroized代码块后才释放锁
 * 方法wait() 锁释放 与 notify()锁不释放
 */
public class ThreadDemo3 {

	public static void main(String[] args) {
		Object lock = new Object();
		ThreadDemo3TestA threadDemo3TestA = new ThreadDemo3TestA(lock);
		threadDemo3TestA.setName("A");
		threadDemo3TestA.start();
		ThreadDemo3TestB threadDemo3TestB = new ThreadDemo3TestB(lock);
		threadDemo3TestB.setName("C");
		threadDemo3TestB.start();
		ThreadDemo3TestC threadDemo3TestC = new ThreadDemo3TestC(lock);
		threadDemo3TestC.setName("B");
		threadDemo3TestC.start();
	}

}

class Service3 {
	public void testMethod(Object lock) {
		try {
			synchronized (lock) {
				System.out.println("begin wait()" + Thread.currentThread().getName());
				lock.wait();
				System.out.println("end   wait()" + Thread.currentThread().getName());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void synNotifyMethod(Object lock){
		try {
			synchronized (lock) {
				System.out.println("begin notify()" + Thread.currentThread().getName());
				lock.notify();
				Thread.sleep(5000);
				System.out.println("end   notify()" + Thread.currentThread().getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class ThreadDemo3TestA extends Thread {
	private Object lock;

	public ThreadDemo3TestA(Object lock) {
		super();
		this.lock = lock;
	}

	@Override
	public void run() {
		Service3 service3 = new Service3();
		service3.testMethod(lock);
	}
}

class ThreadDemo3TestB extends Thread {
	private Object lock;

	public ThreadDemo3TestB(Object lock) {
		super();
		this.lock = lock;
	}

	@Override
	public void run() {
		Service3 service3 = new Service3();
		service3.synNotifyMethod(lock);
	}
}
class ThreadDemo3TestC extends Thread {
	private Object lock;
	
	public ThreadDemo3TestC(Object lock) {
		super();
		this.lock = lock;
	}
	
	@Override
	public void run() {
		Service3 service3 = new Service3();
		service3.synNotifyMethod(lock);
	}
}