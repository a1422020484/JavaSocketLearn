package test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

	private static Lock lock = new ReentrantLock();
	private static Object object = new Object();

	public static void main(String[] args) {
		lock.lock();
		object.hashCode();
		lock.unlock();
	}
}
