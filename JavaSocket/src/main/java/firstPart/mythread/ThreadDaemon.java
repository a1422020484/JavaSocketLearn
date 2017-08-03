package firstPart.mythread;

public class ThreadDaemon {
	public static void main(String[] args) {
		ThreadDaemonTest threadDaemonTest = new ThreadDaemonTest();
		threadDaemonTest.setDaemon(true);
		threadDaemonTest.start();
		try {
			threadDaemonTest.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("老子结束了----------------------------------");
	}
}

class ThreadDaemonTest extends Thread {
	private int i = 0;

	@Override
	public void run() {
		while (true) {
			System.out.println("i = " + i++);
		}
	}
}
