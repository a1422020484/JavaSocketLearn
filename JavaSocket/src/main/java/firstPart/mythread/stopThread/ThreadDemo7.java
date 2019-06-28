package firstPart.mythread.stopThread;

/**
 * @author yangxp
 * @date 2017年8月3日 上午10:19:24
 * <p>
 * 关键字 <i>return</i> 可以停止线程
 * </p>
 */
public class ThreadDemo7 {
	
	public static void main(String[] args) {
		ThreadDemo7Test threadDemo7Test = new ThreadDemo7Test();
		threadDemo7Test.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		threadDemo7Test.interrupt();
	}
}

class ThreadDemo7Test extends Thread {
	@Override
	public void run() {
		while (true) {
			if (this.isInterrupted()) {
				System.out.println("停止了");
				return;
			}
			System.out.println("System.currentTimeMillis() === " + System.currentTimeMillis());
		}
	}
}
