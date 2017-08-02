package firstPart.mythread.stopThread;

/**
 * @author Administrator
 * 测试当前线程是否已经中断  this.interrupted
 * 测试线程是否已经中断 this.isInterrupted
 */
public class ThreadDemo1 {
	
	public static void main(String[] args) {
		try {
			ThreadDemo1Test threadDemo1Test = new ThreadDemo1Test();
			threadDemo1Test.start();
			Thread.sleep(2000);
//			这个方法并没有停止线程
			threadDemo1Test.interrupt();
//			System.out.println(ThreadDemo1Test.interrupted());
			System.out.println(threadDemo1Test.isInterrupted());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ThreadDemo1Test extends Thread {
	@Override
	public void run() {
		super.run();
		for (int i = 0; i < 50000; i++) {
			System.out.println("i=" + (i + 1));
		}
	}
}