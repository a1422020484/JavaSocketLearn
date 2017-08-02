package firstPart.mythread;

public class MyRunnable {
	public static void main(String[] args) {
		Runnable runnable = new MyRunnableTest(); 
		Thread thread = new Thread(runnable);
		thread.start();
		System.out.println("---main---");
	}
}

class MyRunnableTest implements Runnable {

	public void run() {
		System.out.println("MyRunnableTest");
	}

}
