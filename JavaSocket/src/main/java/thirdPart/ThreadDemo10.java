package thirdPart;

import java.util.Date;

public class ThreadDemo10 {

	public static void main(String[] args) {
		try {
			System.out.println("初始化值： === " + Tools10.t1.get());
			for (int i = 0; i < 10; i++) {
				System.out.println("main 线程中取值 ==== " + Tools10.t1.get());
				Thread.sleep(100);
			}
			Thread.sleep(5000);
			ThreadDemo10TestA a = new ThreadDemo10TestA();
			a.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class Tools10 {
	public static InheritableThreadLocalExt t1 = new InheritableThreadLocalExt();
}

class ThreadDemo10TestA extends Thread {
	@Override
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				System.out.println("Thread A 线程中取值 ==== " + Tools10.t1.get());
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class InheritableThreadLocalExt extends InheritableThreadLocal<Object> {
	@Override
	public Object initialValue() {
		return new Date().getTime();
	}

	@Override
	public Object childValue(Object parentValue) {
		return parentValue + " 我是在子线程加的 ";
	}
}
