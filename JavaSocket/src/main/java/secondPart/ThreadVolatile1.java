package secondPart;

/**
 * @author yangxp
 * @date 2017年8月7日 下午3:46:40
 * <p>
 * volatile 关键字；主要是理解公有栈堆和线程的私有栈堆
 */
public class ThreadVolatile1 {

	public static void main(String[] args) {
		ThreadVolatile1Test threadVolatile1Test = new ThreadVolatile1Test();
		threadVolatile1Test.start();
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		threadVolatile1Test.setIsRunning(false);
		System.out.println("end ======== " + Thread.currentThread().getName());
	}

}

class ThreadVolatile1Test extends Thread {

	private volatile boolean isRunning = true;

	public boolean isRunning() {
		return isRunning;
	}

	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	@Override
	public void run() {
		System.out.println("进入了 run");
		while (isRunning == true) {

		}
		System.out.println("线程被停止了");
	}
}
