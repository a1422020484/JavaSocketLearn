package firstPart.mythread;

public class MyThread2 {

	public static void main(String[] args) {
		MyThreadTest2 myThreadTest2 = new MyThreadTest2();
		Thread threadA = new Thread(myThreadTest2, "A");
		Thread threadB = new Thread(myThreadTest2, "B");
		Thread threadC = new Thread(myThreadTest2, "C");
		Thread threadD = new Thread(myThreadTest2, "D");
		threadA.start();
		threadB.start();
		threadC.start();
		threadD.start();
	}

}

class MyThreadTest2 extends Thread {
	private int count = 5;

	@Override
	public synchronized void run() {
		while (count > 0) {
			count--;
			System.out.println("线程：" + this.currentThread().getName() + "的count = " + count);
		}
	}
}
