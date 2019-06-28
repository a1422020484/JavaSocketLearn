package firstPart.mythread;

public class MyRunnable {
	public static void main(String[] args) {
		Runnable runnable = new MyRunnableTest(); 
		Thread thread = new Thread(runnable);
		thread.start();
//		runnable.run();
		System.out.println("---main---");
	}
}

class MyRunnableTest implements Runnable {

	public void run() {
		System.out.println("MyRunnableTest");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
