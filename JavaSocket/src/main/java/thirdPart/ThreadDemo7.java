package thirdPart;

/**
 * @author yangxp
 * @date 2017年8月9日 上午9:21:57
 *       <p>
 *       练习join的使用
 */
public class ThreadDemo7 {

	public static void main(String[] args) {
		try {
			ThreadDemo7Test threadDemo7Test = new ThreadDemo7Test();
			threadDemo7Test.start();
			threadDemo7Test.join();
			System.out.println("我是主线程");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class ThreadDemo7Test extends Thread {
	@Override
	public void run() {
		try {
			System.out.println("我是子线程");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
