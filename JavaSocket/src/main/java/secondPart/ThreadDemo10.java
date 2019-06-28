package secondPart;

/**
 * @author yangxp
 * @date 2017年8月4日 下午4:44:57
 *       <p>
 *       如果 data 的值是一样的，那么两个线程持有的是相同是锁，String常量池的原因。所以线程ThreaA10就会独占代码块。否则，则会交替运行。
 *       
 * 
 */
public class ThreadDemo10 {

	public static void main(String[] args) {
		ThreadDemo10Object threadDemo10Object = new ThreadDemo10Object();
		ThreadA10 a = new ThreadA10(threadDemo10Object);
		a.setName("A");
		a.start();
		ThreadB10 b = new ThreadB10(threadDemo10Object);
		b.setName("B");
		b.start();
	}

}

class ThreadDemo10Object {
	public void addServiceMethod(String data) {
		synchronized (data) {
			while (true) {
				System.out.println(Thread.currentThread().getName());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

class ThreadA10 extends Thread {
	private ThreadDemo10Object threadDemo10Object;

	public ThreadA10(ThreadDemo10Object threadDemo10Object) {
		super();
		this.threadDemo10Object = threadDemo10Object;
	}

	@Override
	public void run() {
		super.run();
		threadDemo10Object.addServiceMethod("AA");
	}
}

class ThreadB10 extends Thread {
	private ThreadDemo10Object threadDemo10Object;

	public ThreadB10(ThreadDemo10Object threadDemo10Object) {
		super();
		this.threadDemo10Object = threadDemo10Object;
	}

	@Override
	public void run() {
		super.run();
		threadDemo10Object.addServiceMethod("AA");
	}
}
