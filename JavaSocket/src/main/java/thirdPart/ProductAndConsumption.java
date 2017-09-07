package thirdPart;

public class ProductAndConsumption {

	public static void main(String[] args) {
		Object lock = new Object();
		ThreadPCA pca = new ThreadPCA(new PTest(lock));
		ThreadPCB pcb = new ThreadPCB(new CTest(lock));
		pca.start();
		pcb.start();
	}

}

/**
 * @author Administrator 生产者
 */
class PTest {
	private Object lock;

	public PTest(Object lock) {
		this.lock = lock;
	}

	public void setValue() {
		synchronized (lock) {
			try {
				if (!ObjectValue.value.equals("")) {
					lock.wait();
				}
				String value = System.currentTimeMillis() + "---" + Thread.currentThread().getName();
				Thread.sleep(1000);
				ObjectValue.value = value;
				System.out.println("生产者：" + value);
				lock.notify();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

/**
 * @author Administrator 消费者
 */
class CTest {
	private Object lock;

	public CTest(Object lock) {
		this.lock = lock;
	}

	public void getValue() {
		synchronized (lock) {
			try {
				if (ObjectValue.value.equals("")) {
					lock.wait();
				}
				String value = ObjectValue.value;
				System.out.println("消费者：" + value);
				ObjectValue.value = "";
				lock.notify();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class ObjectValue {
	public static String value = "";
}

class ThreadPCA extends Thread {

	private PTest pt;

	public ThreadPCA(PTest pt) {
		this.pt = pt;
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			pt.setValue();
		}
	}
}

class ThreadPCB extends Thread {
	private CTest ct;

	public ThreadPCB(CTest ct) {
		this.ct = ct;
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			ct.getValue();
		}
	}
}