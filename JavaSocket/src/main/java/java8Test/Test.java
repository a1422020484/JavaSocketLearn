package java8Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

	private Lock lock = new ReentrantLock();

	public static void main(String[] args) {
		int[] d = { 2, 7, 11, 15 };

		System.out.println(Arrays.toString(twoSum(d, 9)));

	}

	public void methodLock() {
		lock.lock();
		System.out.println("methodLock");
		lock.unlock();
	}

	public static int[] twoSum(int[] numbers, int target) {
		int[] result = new int[2];
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < numbers.length; i++) {
			if (map.containsKey(target - numbers[i])) {
				result[1] = i + 1;
				result[0] = map.get(target - numbers[i]);
				return result;
			}
			map.put(numbers[i], i + 1);
		}
		return result;
	}

}
