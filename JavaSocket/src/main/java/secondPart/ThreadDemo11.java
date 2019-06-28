package secondPart;

/**
 * @author yangxp
 * @date 2017年8月4日 下午5:31:54
 *       <p>
 *       线程A 和 线程B 都在竞争 abc 锁 所以 B永远的得不到代码块
 */
public class ThreadDemo11 {
	public static void main(String[] args) {
		ThreadDemo11Object threadDemo11Object = new ThreadDemo11Object();
		ThreadA11 a = new ThreadA11(threadDemo11Object);
		a.setName("a");
		ThreadB11 b = new ThreadB11(threadDemo11Object);
		b.setName("b");
		a.start();
		b.start();
	}
}

class ThreadDemo11Object {
	String lock = "abc";
	int i = 0;

	public void addServiceMethod() {
		synchronized (lock) {
			while (i < 100) {
				lock = "efg";
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " " + i++);
			}
		}
	}
}

class ThreadA11 extends Thread {
	private ThreadDemo11Object threadDemo11Object;

	public ThreadA11(ThreadDemo11Object threadDemo11Object) {
		super();
		this.threadDemo11Object = threadDemo11Object;
	}

	@Override
	public void run() {
		super.run();
		threadDemo11Object.addServiceMethod();
	}
}

class ThreadB11 extends Thread {
	private ThreadDemo11Object threadDemo11Object;

	public ThreadB11(ThreadDemo11Object threadDemo11Object) {
		super();
		this.threadDemo11Object = threadDemo11Object;
	}

	@Override
	public void run() {
		super.run();
		threadDemo11Object.addServiceMethod();
	}
}