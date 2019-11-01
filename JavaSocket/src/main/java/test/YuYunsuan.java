package test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class YuYunsuan {

	private static Map<Integer, AtomicInteger> testMap = new HashMap<Integer, AtomicInteger>();

	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			Test object = new Test();
//			System.out.println(object.hashCode() + " == " + (object.hashCode() & 8 - 1));
			int j = object.hashCode() & 8 - 1;
			if (testMap.get(j) == null) {
				testMap.put(j, new AtomicInteger(1));
			} else {
				testMap.get(j).incrementAndGet();
			}
		}
		System.out.println(testMap);
	}
}
