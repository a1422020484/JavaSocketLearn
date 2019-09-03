package four;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest<T> {
	private int[] elements;
	private Lock lock = new ReentrantLock();
	private Condition notEmpty = lock.newCondition();
	private Condition notFull = lock.newCondition();

	private int length = 0, addIndex = 0, removeIndex = 0;

	public ConditionTest(int size) {
		elements = new int[size];
	}

	public static void main(String[] args) throws InterruptedException {
		@SuppressWarnings("rawtypes")
		ConditionTest conditionTest = new ConditionTest(2);
		Thread rs = new Thread(new Runnable() {

			@Override
			public void run() {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				try {
					for (;;) {
						String str = br.readLine();
						int i = Integer.valueOf(str).intValue();
						switch (i) {
						case 1:
							addThead(conditionTest);
							continue;
						case 2:
							removeThead(conditionTest);
							continue;
						default:
							continue;
						}

					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		rs.start();
	}

	public static void addThead(ConditionTest conditionTest) {
		Thread rs = new Thread(new Runnable() {

			@Override
			public void run() {
				conditionTest.add(1);
			}

		});
		rs.start();
	}

	public static void removeThead(ConditionTest conditionTest) {
		Thread rs = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					conditionTest.remove();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		rs.start();
	}

	public void add(int value) {
		lock.lock();
		try {
			while (length == elements.length) {
				System.out.println("队列已满 请等待");
				notFull.await();
			}
			System.out.println(value);
			elements[addIndex] = value;
			if (addIndex++ == elements.length) {
				addIndex = 0;
			}
			length++;
			notEmpty.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public int remove() throws InterruptedException {
		lock.lock();
		try {
			while (0 == length) {
				System.out.println("队列为空 请等待");
				notFull.await();
			}
			int element = elements[removeIndex];
			if (removeIndex++ == elements.length) {
				removeIndex = 0;
			}
			length--;
			notFull.signal();
			return element;
		} finally {
			lock.unlock();
		}
	}

}
