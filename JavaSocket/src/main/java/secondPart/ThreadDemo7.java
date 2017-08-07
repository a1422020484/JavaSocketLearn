package secondPart;

/**
 * @author yangxp
 * @date 2017年8月4日 下午7:09:57
 *       <p>
 *       synchroized 方法的弊端
 */
public class ThreadDemo7 {

	public static void main(String[] args) {
		ThreadDemo7Object threadDemo7Object = new ThreadDemo7Object();
		ThreadA7 a = new ThreadA7(threadDemo7Object);
		a.setName("A");
		a.start();
		ThreadA7 b = new ThreadA7(threadDemo7Object);
		b.setName("B");
		b.start();
	}

}

class ThreadDemo7Object {
	private String getData1 = "";
	private String getData2 = "";

	public void methodA() {
		try {
			System.out.println("begin task");
			System.out.println("begin methodA threadName = " + Thread.currentThread().getName());
			System.out.println("begin methodA methodATime = "+ Thread.currentThread().getName() + System.currentTimeMillis());
			Thread.sleep(3000);
			String privateGetData1 = "长时间处理任务后从远程返回的数据 1" + Thread.currentThread().getName();
			String privateGetData2 = "长时间处理任务后从远程返回的数据 2" + Thread.currentThread().getName();
			synchronized (this) {
				getData1 = privateGetData1;
				getData2 = privateGetData2;
			}
			System.out.println("end methodA methodATime = " + Thread.currentThread().getName() + System.currentTimeMillis());
			System.out.println(getData1);
			System.out.println(getData2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ThreadA7 extends Thread {
	private ThreadDemo7Object threadDemo2Object;

	public ThreadA7(ThreadDemo7Object threadDemo2Object) {
		super();
		this.threadDemo2Object = threadDemo2Object;
	}

	@Override
	public void run() {
		super.run();
		threadDemo2Object.methodA();
	}
}

class ThreadB7 extends Thread {
	private ThreadDemo7Object threadDemo2Object;

	public ThreadB7(ThreadDemo7Object threadDemo2Object) {
		super();
		this.threadDemo2Object = threadDemo2Object;
	}

	@Override
	public void run() {
		super.run();
		threadDemo2Object.methodA();
	}
}
