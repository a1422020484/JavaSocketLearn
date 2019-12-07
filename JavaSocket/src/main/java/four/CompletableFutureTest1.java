package four;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @author yangxp 2019年12月6日上午11:50:20
 *         <p>
 *         描述：CompletableFuture使用,引用链形式的
 */
public class CompletableFutureTest1 {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
//		CompletableFuture.supplyAsync(new MyThreadFutureTest1(), executorService).whenComplete((result, e) -> {
//			System.out.println(result + " " + e);
//		}).exceptionally((e) -> {
//			System.out.println("exception " + e);
//			return "execption";
//		});
//
//		CompletableFuture.supplyAsync(new MyThreadFutureTest2(), executorService).whenComplete((result, e) -> {
//			System.out.println(result + " " + e);
//		}).exceptionally((e) -> {
//			System.out.println("exception " + e);
//			return "execption";
//		});
//
//		CompletableFuture.supplyAsync(new MyThreadFutureTest2(), executorService).thenRun(new Runnable() {
//
//			@Override
//			public void run() {
//				System.out.println("fffff");
//			}
//
//		}).whenComplete((result, e) -> {
//			System.out.println(result + " " + e);
// });

		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new MyThreadFutureTest2(), executorService).thenCompose(result -> CompletableFuture.supplyAsync(() -> {
			// 模拟执行耗时任务
			System.out.println("task2 doing...");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int result2 = Integer.valueOf(result) + 800;
			// 返回结果
			return String.valueOf(result2);
		}));
		executorService.shutdown();

		System.out.println(completableFuture.get());
	}
}

class MyThreadFutureTest1 implements Supplier<String> {

	@Override
	public String get() {
		int sum = 0;
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sum += i;
		}
		return String.valueOf(sum + 300000);
	}

}

class MyThreadFutureTest2 implements Supplier<String> {

	@Override
	public String get() {
		int sum = 999;
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(201);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sum += i;
		}
		return String.valueOf(sum + 300000);
	}

}
