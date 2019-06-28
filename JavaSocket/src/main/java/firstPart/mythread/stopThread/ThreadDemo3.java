package firstPart.mythread.stopThread;

/**
 * @author Administrator 在沉睡中被停止 如果在 sleep 状态下停止某一线程，
 * 会进入 catch 语句，并且清除停止状态值，使之变成false。
 */
public class ThreadDemo3 {
	public static void main(String[] args) {
		ThreadDemo3Test threadDemo3Test = new ThreadDemo3Test();
		threadDemo3Test.start();
		threadDemo3Test.interrupt();
		System.out.println("end!");
	}
}

class ThreadDemo3Test extends Thread {
	@Override
	public void run() {
		try {
			for (int i = 0; i < 100000; i++) {
				System.out.println("i= " + (i + 1));
			}
			System.out.println("run begin");
			Thread.sleep(2000);
			System.out.println("run end");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}