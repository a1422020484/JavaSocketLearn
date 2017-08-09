package sixPart;

/**
 * @author yangxp
 * @date 2017年8月9日 下午4:16:24
 *       <p>
 *       这种方法在多线程条件下生成的对象是不一样的。
 */
public class Demo2 {

	public static void main(String[] args) {
		MyThread2Test myThread2Test1 = new MyThread2Test();
		MyThread2Test myThread2Test2 = new MyThread2Test();
		MyThread2Test myThread2Test3 = new MyThread2Test();
		myThread2Test1.start();
		myThread2Test2.start();
		myThread2Test3.start();
	}

}

class MyThread2Test extends Thread {
	@Override
	public void run() {
		System.out.println(Demo2Test.getInstance().hashCode());
	}
}

class Demo2Test {
	private static Demo1Test demo1Test;

	synchronized public static Demo1Test getInstance() {
		if (demo1Test == null) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			demo1Test = new Demo1Test();
		}
		return demo1Test;
	}
}
