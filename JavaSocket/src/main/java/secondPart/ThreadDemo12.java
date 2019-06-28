package secondPart;

/**
 * @author yangxp
 * @date 2017年8月7日 下午5:19:22
 * <p>
 * 关键字 synchroized 可以保证在同一时刻，只有一个线程能执行某一个方法或者某一个代码块。互斥性和可见性
 */
public class ThreadDemo12 {

	public static void main(String[] args) {
		try {
			Service12 service12 = new Service12();
			ThreadDemo12TestA a = new ThreadDemo12TestA(service12);
			a.start();
			Thread.sleep(1000);
			ThreadDemo12TestB b = new ThreadDemo12TestB(service12);
			b.start();
			System.out.println("已经发出停止命令了");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class Service12 {
	private boolean isContinueRun = true;

	public void runMethod() {
		String anyString = new String();
		while (isContinueRun == true) {
			synchronized (anyString) {

			}
		}
		System.out.println("停下来了");
	}

	public void stopMethod() {
		isContinueRun = false;
	}
}

class ThreadDemo12TestA extends Thread {
	private Service12 service;

	public ThreadDemo12TestA(Service12 service) {
		super();
		this.service = service;
	}

	@Override
	public void run() {
		service.runMethod();
	}
}

class ThreadDemo12TestB extends Thread {
	private Service12 service;

	public ThreadDemo12TestB(Service12 service) {
		super();
		this.service = service;
	}

	@Override
	public void run() {
		service.stopMethod();
	}
}