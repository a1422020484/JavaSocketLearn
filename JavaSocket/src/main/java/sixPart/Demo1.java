package sixPart;

public class Demo1 {

	public static void main(String[] args) {
		MyThread1Test myThread1Test1 = new MyThread1Test();
		MyThread1Test myThread1Test2 = new MyThread1Test();
		MyThread1Test myThread1Test3 = new MyThread1Test();
		myThread1Test1.start();
		myThread1Test2.start();
		myThread1Test3.start();
	}

}

class MyThread1Test extends Thread {
	@Override
	public void run() {
		System.out.println(Demo1Test.getInstance1().hashCode());
	}
}

class Demo1Test {
	private static Demo1Test demo1Test1 = new Demo1Test();

	public static Demo1Test getInstance1() {
		return demo1Test1;
	}
}