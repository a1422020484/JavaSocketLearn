package secondPart;

/**
 * @author yangxp
 * @date 2017年8月3日 下午4:11:13
 * <p>
 * 调用关键字<i> synchronized </i>声明的方法一定是排队运行的。
 * </p>
 * <p>
 * 关键字 <i>synchronized</i> 取得锁都是对象锁，而不是把一段代码或方法当做锁，哪个线程先执行synchronized关键字的方法
 * 哪个线程就持有该方法所属对象的锁lock，其他线程只有等待状态，前提是多个线程访问的是同一个对象。
 * </p>
 */
public class ThreadDemo1 {

	public static void main(String[] args) {
//		HasSelfPrivateNum num2 = new HasSelfPrivateNum();
		HasSelfPrivateNum num2 = new HasSelfPrivateNum();
		ThreadA aThreadA = new ThreadA(num2);
		aThreadA.start();
		ThreadB bThreadA = new ThreadB(num2);
		bThreadA.start();
	}

}

class HasSelfPrivateNum {

	private int num = 0;

	public synchronized void addI(String string) {
		try {
			if (string.equals("a")) {
				num = 100;
				System.out.println("a set over!");
				Thread.sleep(2000);
			} else {
				num = 200;
				System.out.println("b set over!");
			}
			System.out.println(string + " number = " + num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class ThreadA extends Thread {
	private HasSelfPrivateNum number;

	public ThreadA(HasSelfPrivateNum number) {
		super();
		this.number = number;
	}

	@Override
	public void run() {
		super.run();
		number.addI("a");
	}
}

class ThreadB extends Thread {
	private HasSelfPrivateNum number;

	public ThreadB(HasSelfPrivateNum number) {
		super();
		this.number = number;
	}

	@Override
	public void run() {
		super.run();
		number.addI("b");
	}
}