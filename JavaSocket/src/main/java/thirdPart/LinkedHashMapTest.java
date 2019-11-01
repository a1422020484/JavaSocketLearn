package thirdPart;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapTest {
	
	public static int capaCity = 8;
	
	public static void main(String[] args) {
		Map<String, String> linkedHashMap = new LinkedHashMap<String, String>(capaCity, 0.75F, true) {
			private static final long serialVersionUID = 1L;

			protected boolean removeEldestEntry(java.util.Map.Entry<String, String> eldest) {
				boolean b = size() > capaCity;
				return b;
			}
		};
		for (int i = 0; i < 10; i++) {
			linkedHashMap.put("name" + i, "value" + i);
		}
		linkedHashMap.put(null, null);
		System.out.println(linkedHashMap);
		
		linkedHashMap.get("name2");
		System.out.println(linkedHashMap);
		linkedHashMap.get("name3");
		System.out.println(linkedHashMap);
		linkedHashMap.get("name6");
		System.out.println(linkedHashMap);
		linkedHashMap.put("name11", "name11");
		System.out.println(linkedHashMap);
		
		capaCity = 20;
		
		for (int i = 10; i < 20; i++) {
			linkedHashMap.put("name" + i, "value" + i);
		}
		
		System.out.println(linkedHashMap);
		capaCity = 8;
		
		linkedHashMap.put("name21", "name21");
		System.out.println(linkedHashMap);
	}
}
