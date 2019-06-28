package firstPart.mythread;

public class MyThread1 {

	public static void main(String[] args) {
//		每个线程都有自己的count
		MyThreadTest1 myThreadTest1A = new MyThreadTest1("A");
		MyThreadTest1 myThreadTest1B = new MyThreadTest1("B");
		MyThreadTest1 myThreadTest1C = new MyThreadTest1("C");
		myThreadTest1A.start();
		myThreadTest1B.start();
		myThreadTest1C.start();
	}

}

class MyThreadTest1 extends Thread {
	private int count = 5;
	private String name;
	
	public MyThreadTest1(String string) {
		this.name = string;
	}

	@Override
	public void run() {
		while (count > 0) {
			count--;
			System.out.println("线程：" + name + "的count = " + count);
		}
	}
}