package java8Test.newClass;

import java8Test.menu.App;
import java8Test.optionalTest.Car;

public class PlatformCreateHashMap<K extends App, V extends Car> {

	public static void main(String[] args) {
		SingleTon singleTon = SingleTon.getInstance();
		System.out.println("count1=" + singleTon.count1);
		System.out.println("count2=" + singleTon.count2);
	}
}

class SingleTon {
	private static SingleTon singleTon = new SingleTon();
	public static int count1;
	public static int count2;

	private SingleTon() {
		count1++;
		count2++;
	}

	public static SingleTon getInstance() {
		return singleTon;
	}
}
