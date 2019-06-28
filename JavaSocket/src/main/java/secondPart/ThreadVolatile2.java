package secondPart;

/**
 * @author yangxp
 * @date 2017年8月7日 下午3:49:04
 * <p>
 * 注意一个高并发的例子，如果加了synchroized关键字就没有必要添加volatile了。
 */
public class ThreadVolatile2 {

	public static void main(String[] args) {
		ThreadVolatile2Test[] threadVolatile2Tests = new ThreadVolatile2Test[100];
		for (int i = 0; i < threadVolatile2Tests.length; i++) {
			threadVolatile2Tests[i] = new ThreadVolatile2Test();
		}
		for (int i = 0; i < threadVolatile2Tests.length; i++) {
//			这个是个高并发
			threadVolatile2Tests[i].start();
		}
	}

}

class ThreadVolatile2Test extends Thread {
	public static int count;

	synchronized private static void addCount(){
		for (int i = 0; i < 100; i++) {
			count++;
		}
		System.out.println("count ==== " + count);
	}
	
	@Override
	public void run() {
		addCount();
	}
}