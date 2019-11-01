package test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

	private static Lock lock = new ReentrantLock();
	private static Object object = new Object();

	public static void main(String[] args) {
		Map<String, String> linkedHashMap = new LinkedHashMap<String, String>(8, 0.75F, true) {
			private static final long serialVersionUID = 1L;

			protected boolean removeEldestEntry(java.util.Map.Entry<String, String> eldest) {
				boolean b = size() > 8;
				return b;
			}
		};
		for (int i = 0; i < 10; i++) {
			linkedHashMap.put("name" + i, "value" + i);
		}
		System.out.println(linkedHashMap);
		
		linkedHashMap.get("name2");
		System.out.println(linkedHashMap);
		linkedHashMap.get("name3");
		System.out.println(linkedHashMap);
		linkedHashMap.get("name1");
		
		System.out.println(linkedHashMap);
	}
}
