package four;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yangxp
 * 2019年12月6日上午11:18:39
 * <p>描述：completionService.take().get() 返回最快结果的get
 */
public class CompletionServiceTest1 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		CompletionService<String> completionService = new ExecutorCompletionService<String>(executorService);
		
		completionService.submit(new MyThread01());
		completionService.submit(new MyThread02());
		
		executorService.shutdown();
		
		System.out.println(completionService.take().get());
		System.out.println(completionService.take().get());
	}
}

class MyThread01 implements Callable<String> {
	@Override
	public String call() {
		int sum = 0;
		try {
			for (int i = 0; i < 10; i++) {
				Thread.sleep(200);
				sum += i;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return String.valueOf(sum + 30000000);
	}
}

class MyThread02 implements Callable<String> {

	@Override
	public String call() {
		int sum = 999;
		try {
			for (int i = 0; i < 10; i++) {
				Thread.sleep(400);
				sum += i;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return String.valueOf(sum + 30000000);
	}
}