package secondPart;

/**
 * @author yangxp
 * @date 2017年8月4日 下午3:39:40
 * synchroized( 非 this 对象 x ) 去他妈的 会出现脏读 。老子学你干球。
 */
public class ThreadDemo8 {

	public static void main(String[] args) {
		ThreadDemo8Object threadDemo8Object = new ThreadDemo8Object();
		ThreadA8 a = new ThreadA8(threadDemo8Object);
		a.setName("A");
		a.start();
		ThreadB8 b = new ThreadB8(threadDemo8Object);
		b.setName("B");
		b.start();
	}

}

class ThreadDemo8Object {
	private static String usernameRef;
	private static String passwordRef;

	public void doPost(String username, String password) {
		try {
			String anyString = new String();
			synchronized (anyString) {
				System.out.println("enter Thread name = " + Thread.currentThread().getName() + " == " + System.currentTimeMillis());
				usernameRef = username;
				if (usernameRef.equals("A")) {
					Thread.sleep(5000);
				}
				passwordRef = password;
				System.out.println("level Thread name = " + Thread.currentThread().getName() + " == " + System.currentTimeMillis());
				System.out.println("username = " + usernameRef + " password " + passwordRef);
			}
			System.out.println("Out");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

class ThreadA8 extends Thread {
	private ThreadDemo8Object threadDemo8Object;

	public ThreadA8(ThreadDemo8Object threadDemo8Object) {
		super();
		this.threadDemo8Object = threadDemo8Object;
	}

	@Override
	public void run() {
		super.run();
		threadDemo8Object.doPost("A", "AA");
	}
}

class ThreadB8 extends Thread {
	private ThreadDemo8Object threadDemo8Object;

	public ThreadB8(ThreadDemo8Object threadDemo8Object) {
		super();
		this.threadDemo8Object = threadDemo8Object;
	}

	@Override
	public void run() {
		super.run();
		threadDemo8Object.doPost("B", "BB");
	}
}