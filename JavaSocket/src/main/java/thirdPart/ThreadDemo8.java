package thirdPart;

/**
 * @author yangxp
 * @date 2017年8月9日 上午9:51:01
 * 方法join的使用
 * join(long) 的功能内部是使用wait(long) 方法来实现的。所以 join(long) 方法具有释放锁的特点。
 */
public class ThreadDemo8 {

	public static void main(String[] args) {
		ThreadDemo8TestB threadDemo8TestB = new ThreadDemo8TestB();
		threadDemo8TestB.start();

		ThreadDemo8TestC threadDemo8TestC = new ThreadDemo8TestC(threadDemo8TestB);
		threadDemo8TestC.start();
		System.out.println("我是主线程");
	}

}

class ThreadDemo8TestA extends Thread {
	@Override
	public void run() {
		System.out.println("我是子线程");
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			String newString = new String();
			Math.random();
		}
	}
}

class ThreadDemo8TestB extends Thread {
	@Override
	public void run() {
		try {
			ThreadDemo8TestA threadDemo8TestA = new ThreadDemo8TestA();
			threadDemo8TestA.start();
			threadDemo8TestA.join();
			System.out.println("线程B 打印");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ThreadDemo8TestC extends Thread {
	private ThreadDemo8TestB threadDemo8TestB;

	public ThreadDemo8TestC(ThreadDemo8TestB threadDemo8TestB) {
		this.threadDemo8TestB = threadDemo8TestB;
	}

	@Override
	public void run() {
		threadDemo8TestB.interrupt();
	}
}
