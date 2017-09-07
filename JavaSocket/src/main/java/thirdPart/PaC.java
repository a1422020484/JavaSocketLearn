package thirdPart;

public class PaC {
	public static void main(String[] args) {
		Object lock = new Object();
		PCThread1 pct1 = new PCThread1(new PTest1(lock));
		PCThread2 pct2 = new PCThread2(new CTest1(lock));
		pct1.start();
		pct2.start();
	}
}

/**
 * @author Administrator 生产者
 */
class PTest1 {
	private Object lock;

	public PTest1(Object lock) {
		this.lock = lock;
	}

	public void setValue() {
		synchronized (lock) {
			try {
				if (!ObjectValue1.value.equals("")) {
					lock.wait();
				}
				String value = System.currentTimeMillis() + " " + Thread.currentThread().getName();
				Thread.sleep(1000);
				System.out.println("生产者：" + value);
				ObjectValue1.value = value;
				lock.notify();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

/**
 * @author Administrator 消费者
 */
class CTest1 {
	private Object lock;

	public CTest1(Object lock) {
		this.lock = lock;
	}

	public void getValue() {
		synchronized (lock) {
			try {
				if (ObjectValue1.value.equals("")) {
					lock.wait();
				}
				System.out.println("消费者：" + ObjectValue1.value);
				ObjectValue1.value = "";
				lock.notify();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}

class ObjectValue1 {
	public static String value = "";
}

class PCThread1 extends Thread {

	private PTest1 pt;

	public PCThread1(PTest1 pt) {
		this.pt = pt;
	}

	@Override
	public void run() {
		while (true) {
			pt.setValue();
		}
	}
}

class PCThread2 extends Thread {

	private CTest1 ct;

	public PCThread2(CTest1 ct) {
		this.ct = ct;
	}

	@Override
	public void run() {
		while (true) {
			ct.getValue();
		}
	}
}