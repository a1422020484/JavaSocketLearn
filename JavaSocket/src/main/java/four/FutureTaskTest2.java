package four;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author yangxp
 * 2019.12.6 
 * <p> Future get 会阻塞主线程 直到获取结果
 * <p> 会根据调用get的先后顺序来返回结果
 */
public class FutureTaskTest2 {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService es1 = Executors.newCachedThreadPool();

		Future<String> task2 = es1.submit(new MyThread04());
		Future<String> task1 = es1.submit(new MyThread03());

		es1.shutdown();

		System.out.println(task1.get());
		System.out.println(task2.get());
	}
}

class MyThread03 implements Callable<String> {

	@Override
	public String call() throws Exception {
		int sum = 999;
		for (int i = 0; i < 10; i++) {
			Thread.sleep(300);
			sum += i;
		}
		return String.valueOf(sum + 300000000);
	}

}

class MyThread04 implements Callable<String> {

	@Override
	public String call() throws Exception {
		int sum = 0;
		for (int i = 0; i < 10; i++) {
			Thread.sleep(200);
			sum += i;
		}
		return String.valueOf(sum + 300000000);
	}

}
