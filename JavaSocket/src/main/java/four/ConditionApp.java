package four;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionApp {

	private static Lock lock = new ReentrantLock();
	private static Condition notEmpty = lock.newCondition();

	public static void main(String[] args) {
		long nowBegin = System.currentTimeMillis();
		try {
			lock.lock();
			notEmpty.await();
			notEmpty.awaitNanos(1000000000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		long nowEnd = System.currentTimeMillis();
		System.out.println(nowEnd - nowBegin);
		System.out.println("notEmptydsa");
	}
}
