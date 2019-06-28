package thirdPart;

/**
 * @author yangxp
 * @date 2017年8月9日 上午9:15:57
 * 生产者消费者模式 
 */
public class ThreadDemo6 {

	public static void main(String[] args) {
		String lock = new String("");
		Pthread pthread = new Pthread(lock);
		Cthread cthread = new Cthread(lock);
		ThreadDemo6TestA pThread = new ThreadDemo6TestA(pthread);
		ThreadDemo6TestB cThread = new ThreadDemo6TestB(cthread);
		pThread.start();
		cThread.start();
	}

}

class Pthread {
	private String lock;

	public Pthread(String lock) {
		super();
		this.lock = lock;
	}

	public void setValue() {

		try {
			synchronized (lock) {
				if (!ValueObject.value.equals("")) {
					lock.wait();
				}
				ValueObject.value = System.currentTimeMillis() + "_" + System.nanoTime();
				System.out.println("Pthread set的值是 ： " + ValueObject.value);
				lock.notify();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Cthread {
	private String lock;

	public Cthread(String lock) {
		super();
		this.lock = lock;
	}

	public void getValue() {

		try {
			synchronized (lock) {
				if (ValueObject.value.equals("")) {
					lock.wait();
				}
				System.out.println("Cthread get的值是 ： " + ValueObject.value);
				ValueObject.value = "";
				lock.notify();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class ValueObject {
	public static String value = "";
}

class ThreadDemo6TestA extends Thread {
	public Pthread pthread;

	public ThreadDemo6TestA(Pthread pthread) {
		this.pthread = pthread;
	}

	@Override
	public void run() {
		while (true) {
			pthread.setValue();
		}
	}
}

class ThreadDemo6TestB extends Thread {
	public Cthread cthread;

	public ThreadDemo6TestB(Cthread cthread) {
		this.cthread = cthread;
	}

	@Override
	public void run() {
		while (true) {
			cthread.getValue();
		}
	}
}
