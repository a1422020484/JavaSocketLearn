package firstPart.mythread;

/**
 * @author Administrator
 * println 方法是线程安全的
 */
public class MyThreadPrintln {

	public static void main(String[] args) {
		MyThreadPrintlnTest myThreadPrintlnTest = new MyThreadPrintlnTest();
		Thread threadA = new Thread(myThreadPrintlnTest, "A");
		Thread threadB = new Thread(myThreadPrintlnTest, "B");
		Thread threadC = new Thread(myThreadPrintlnTest, "C");
		Thread threadD = new Thread(myThreadPrintlnTest, "D");
		Thread threadE = new Thread(myThreadPrintlnTest, "E");

		threadA.start();
		threadB.start();
		threadC.start();
		threadD.start();
		threadE.start();
	}

}

class MyThreadPrintlnTest extends Thread {
	private int i = 5;

	@Override
	public void run() {
		System.out.println("i=" + (i--) + "ThreadName : " + Thread.currentThread().getName());
	}
}