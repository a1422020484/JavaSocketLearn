package firstPart;

import java.util.HashMap;
import java.util.Map;

import spring.ApplicationContextAwareTest;

public class Test extends Thread {
	static volatile int x = 0;
	static volatile int y = 0;
	static volatile int a = 0;
	static volatile int b = 0;

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 1000; i++) {
			x = y = a = b = 0;
			Thread one = new Thread() {
				public void run() {
					a = 1;
					x = b;
				}
			};
			Thread two = new Thread() {
				public void run() {
					b = 1;
					y = a;
				}
			};
			one.start();
			two.start();
			one.join();
			two.join();
			System.out.println(x + " " + y);
		}
	}
}
