package four;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author yangxp
 * 2019年12月6日下午4:37:04
 * <p>描述：在线程运行过程中产生异常，线程不能正常结束，get会永远阻塞 不会终止了。
 */
public class CompletableFutureTest2 {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletableFuture<String> completableFuture = new CompletableFuture<String>();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// 模拟执行耗时任务
				System.out.println("task doing...");
				try {
				Map<String, String> map = null;
				map.get("fff");
				Thread.sleep(3000);

				} catch (Exception e) {
					e.printStackTrace();
				}
				// 告诉completableFuture任务已经完成
				completableFuture.complete("result");
			}
		});
		t1.start();
		// 获取任务结果，如果没有完成会一直阻塞等待
		String result = completableFuture.get();
		System.out.println("计算结果:" + result);
	}
}
