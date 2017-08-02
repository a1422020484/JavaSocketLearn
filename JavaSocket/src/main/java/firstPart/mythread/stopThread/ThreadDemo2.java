package firstPart.mythread.stopThread;

public class ThreadDemo2 {

	public static void main(String[] args) {
		try {
			ThreadDemo2Test threadDemo1Test = new ThreadDemo2Test();
			threadDemo1Test.start();
			Thread.sleep(2000);
			threadDemo1Test.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("end!");
	}

}

class ThreadDemo2Test extends Thread {
	@Override
	public void run() {
		super.run();
		for (int i = 0; i < 500000; i++) {
			if (this.interrupted()) {
				System.out.println("已经是停止状态我要停止了");
				break;
			}
			System.out.println("i=" + (i + 1));
		}
		System.out.println("我被输出了，线程未停止");
	}
}