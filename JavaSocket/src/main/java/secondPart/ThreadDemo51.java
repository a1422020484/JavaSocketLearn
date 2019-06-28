package secondPart;

/**
 * @author yangxp
 * @date 2017年8月3日 下午6:19:09
 *       <p>
 *       当存在父子类继承关系时，子类是完全可以通过“可重入锁”调用父类的同步方法
 *       <p>
 *       出现异常就会放弃锁
 */
public class ThreadDemo51 {

	public static void main(String[] args) {
		ThreadDemo51Test threadDemo51Test = new ThreadDemo51Test();
		threadDemo51Test.start();
	}

}

class ThreadDemo51Test extends Thread {
	@Override
	public void run() {
		Sub sub = new Sub();
		sub.operateIMainMethod();
	}
}

class Main {
	public int i = 10;

	public synchronized void operateIMainMethod() {
		try {
			i--;
			System.out.println("main print i = " + i);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Sub extends Main {
	synchronized public void operateIMainMethod() {
		try {
			while (i > 0) {
				i--;
				System.out.println("Sub print i = " + i);
				Thread.sleep(1000);
				super.operateIMainMethod();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}