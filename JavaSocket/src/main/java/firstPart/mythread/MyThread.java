package firstPart.mythread;

public class MyThread {
	public static void main(String[] args) {
		MyThreadTest myThreadTest = new MyThreadTest();
		myThreadTest.start();
		System.out.println("---main Thread---");
	}
}


class MyThreadTest extends Thread{
	@Override
	public void run(){
		System.out.println("MyThreadTest");
	}
}