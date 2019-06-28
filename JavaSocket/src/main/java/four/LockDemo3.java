package four;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangxp
 * @date 2017年8月10日 上午10:00:04
 *       <p>
 *       condition 的正确用法
 * 
 */
public class LockDemo3 {

	public static void main(String[] args) {
		MyService3 myService3 = new MyService3();
		MyThread3 aMyThread3 = new MyThread3(myService3);
		aMyThread3.start();
	}

}

class MyService3 {
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public void testMethod() {
		try {
			lock.lock();
			System.out.println("A");
			// condition.wait(); 不能用这个方法
			condition.await();
			System.out.println("B");
			lock.unlock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

class MyThread3 extends Thread {
	private MyService3 myService3;

	public MyThread3(MyService3 myService3) {
		this.myService3 = myService3;
	}

	@Override
	public void run() {
		myService3.testMethod();
	}
}