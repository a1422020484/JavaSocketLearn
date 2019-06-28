package test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeTest {
	private int a = 10;
	private Map<Integer, Integer> testMap = new HashMap<Integer, Integer>();

	public static void main(String[] args) {
		System.out.println(new Date());
		System.out.println(System.currentTimeMillis());
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		System.out.println(c.get(Calendar.DAY_OF_WEEK));
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public Map<Integer, Integer> getTestMap() {
		return testMap;
	}

	public void setTestMap(Map<Integer, Integer> testMap) {
		this.testMap = testMap;
	}

}
