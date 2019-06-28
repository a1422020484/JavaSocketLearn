package firstPart.mythread.yieldTest;

/**
 * @author yangxp
 * @date 2017年8月3日 上午11:10:28
 * <p>
 * 这个主要是学 <i>yield()</i> 方法
 * </p>
 */
public class TheadDemo1 {

	public static void main(String[] args) {
		TheadDemo1Test theadDemo1Test = new TheadDemo1Test();
		theadDemo1Test.start();
	}

}

class TheadDemo1Test extends Thread {
	@Override
	public void run() {
		long beginTime = System.currentTimeMillis();
		int count = 0;
		for (int i = 0; i < 5000000; i++) {
			Thread.yield();
			count = count + (i + 1);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("用时：" + (endTime - beginTime) + "毫秒！");
	}
}
