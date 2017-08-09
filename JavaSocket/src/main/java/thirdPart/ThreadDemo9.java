package thirdPart;

/**
 * @author yangxp
 * @date 2017年8月9日 上午11:30:11 ThreadLocal 是线程的共享对象。每个线程都是独立。
 */
public class ThreadDemo9 {

	public static void main(String[] args) {
		System.out.println("Main get Value ==== " + Tools.t1.get());
		ThreadDemo9TestA threadDemo9TestA = new ThreadDemo9TestA();
		ThreadDemo9TestB threadDemo9TestB = new ThreadDemo9TestB();
		threadDemo9TestA.start();
		threadDemo9TestB.start();
		for (int i = 0; i < 10; i++) {
			Tools.t1.set("Main ====" + (i + 1));
			System.out.println("Main get Value ==== " + Tools.t1.get());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}

class Tools {
	public static ThreadLocalExt t1 = new ThreadLocalExt();
//	public static ThreadLocal<String> t2 = new ThreadLocal<String>();
}

class ThreadDemo9TestA extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			Tools.t1.set("ThreadA ====" + (i + 1));
			System.out.println("ThreadA get Value ==== " + Tools.t1.get());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

class ThreadDemo9TestB extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			Tools.t1.set("ThreadB ====" + (i + 1));
			System.out.println("ThreadB get Value ==== " + Tools.t1.get());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class ThreadLocalExt extends ThreadLocal<Object> {
	@Override
	public Object initialValue() {
		return "这个是初始化的默认值，第一次get 不再为 null";
	}
}
