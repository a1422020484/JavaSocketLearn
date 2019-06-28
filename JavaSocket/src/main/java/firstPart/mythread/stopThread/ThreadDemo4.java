package firstPart.mythread.stopThread;

public class ThreadDemo4 {

	public static void main(String[] args) {
		ThreadDemo4Test threadDemo4Test = new ThreadDemo4Test();
		threadDemo4Test.start();
	}

}

class ThreadDemo4Test extends Thread {
	@Override
	public void run() {
		try {
			this.stop();
		} catch (Exception e) {
			System.out.println("进入了 catch() 方法");
			e.printStackTrace();
		}
	}
}