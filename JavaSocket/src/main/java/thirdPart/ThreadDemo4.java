package thirdPart;

/**
 * @author yangxp
 * @date 2017年8月8日 下午3:51:38
 * 当 wait() 和 interrupt()方法相遇
 */
public class ThreadDemo4 {

	public static void main(String[] args) {
		try {
			Object lock = new Object();
			ThreadDemo4Test threadDemo4Test = new ThreadDemo4Test(lock);
			threadDemo4Test.start();
			Thread.sleep(2000);
			threadDemo4Test.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class Service4 {

	public void testMethod(Object lock) {
		try {
			synchronized (lock) {
				System.out.println("begin wait()");
				lock.wait();
				System.out.println("end   wait()");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class ThreadDemo4Test extends Thread {
	public Object lock;
	public ThreadDemo4Test(Object lock){
		this.lock = lock;
	}
	
	@Override
	public void run() {
		Service4 service4 = new Service4();
		service4.testMethod(lock);
	}
}