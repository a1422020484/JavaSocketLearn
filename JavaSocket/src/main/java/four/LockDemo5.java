package four;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo5 {
	public static void main(String[] args) {
		MyService5 myService5 = new MyService5();
		MyThread5A myThread5A = new MyThread5A(myService5);
		myThread5A.setName("A");
		myThread5A.start();
		MyThread5B myThread5B = new MyThread5B(myService5);
		myThread5B.setName("B");
		myThread5B.start();
	}
}

class MyService5 {
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private boolean value = false;

	public void getValue() {
		try {
			lock.lock();
			if (value == false) {
				System.out.println("getValue() " + Thread.currentThread().getName());
				condition.await();
			} else {
				condition.signal();
			}
			value = true;
			lock.unlock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setValue() {
		try {
			lock.lock();
			if (value == true) {
				System.out.println("setValue() " + Thread.currentThread().getName());
				condition.await();
			} else {
				condition.signal();
			}
			value = false;
			lock.unlock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class MyThread5A extends Thread {
	private MyService5 myService5;

	public MyThread5A(MyService5 myService5) {
		this.myService5 = myService5;
	}

	public void testGetValue() {
		myService5.getValue();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			testGetValue();
		}
	}
}

class MyThread5B extends Thread {
	private MyService5 myService5;

	public MyThread5B(MyService5 myService5) {
		this.myService5 = myService5;
	}

	public void testSetValue() {
		myService5.setValue();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			testSetValue();
		}
	}
}
