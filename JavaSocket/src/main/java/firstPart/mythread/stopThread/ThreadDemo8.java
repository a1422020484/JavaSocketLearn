package firstPart.mythread.stopThread;

/**
 * @author yangxp
 * @date 2017年8月3日 上午10:28:17
 *       <p>
 *       暂停一个线程，可以用 <i>suspend()</i> 方法暂停线程。是可以恢复的。可以用 <i>resume()</i> 恢复一个线程。
 *       </p>
 *       <p>
 *       <i>suspend</i> and <i>resume</i> 极易造成公共对象的独占，而且不同步。
 *       </p>
 * 
 */
public class ThreadDemo8 {

	public static void main(String[] args) {
		try {
			ThreadDemo8Test threadDemo8Test = new ThreadDemo8Test();
			threadDemo8Test.start();
			Thread.sleep(5000);
			threadDemo8Test.suspend();
			System.out.println("A= " + System.currentTimeMillis() + "i= " + threadDemo8Test.getI());
			Thread.sleep(5000);
			threadDemo8Test.resume();
			System.out.println("B= " + System.currentTimeMillis() + "i= " + threadDemo8Test.getI());
			Thread.sleep(5000);
			threadDemo8Test.suspend();
			System.out.println("C= " + System.currentTimeMillis() + "i= " + threadDemo8Test.getI());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class ThreadDemo8Test extends Thread {
	private long i = 0;

	public long getI() {
		return i;
	}

	public void setI(long i) {
		this.i = i;
	}

	@Override
	public void run() {
		while (true) {
			i++;
		}
	}
}
