package java8Test.exitTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExitTest {
	public static void main(String[] args) {

		Runtime.getRuntime().addShutdownHook(thread2Test());
		test();
		// Thread t = new Thread(new Runnable() {
		// @Override
		// public void run() {
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// try {
		// for (;;) {
		// String str = br.readLine();
		// if ("stop".equals(str)) {
		// System.exit(0);
		// } else {
		// System.out.println("输入'stop'停止服务器!");
		// }
		// }
		// } catch (IOException e) {
		// // e.printStackTrace();
		// }
		// }
		// });
		// t.setDaemon(true);
		// t.setPriority(Thread.MIN_PRIORITY);
		// t.start();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			for (;;) {
				String str = br.readLine();
				if ("stop".equals(str)) {
					System.exit(1);
				} else {
					System.out.println("输入'stop'停止服务器!");
				}
			}
		} catch (IOException e) {
			// e.printStackTrace();
		}
		System.out.println("main");
	}

	public static Thread thread2Test() {
		Thread nw2 = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("thread2Test");
			}

		});
		return nw2;
	}
	
	public static void test() {
		Thread nw2 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (;;) {
					
				}
			}

		});
		nw2.setName("Dame Thread name");
		nw2.setDaemon(true);
		nw2.start();
	}

}
