package secondPart;

import secondPart.Thread13Test.Inner;

public class ThreadDemo13 {

	public static void main(String[] args) {
		final Inner inner = new Inner();
		Thread a = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				inner.play(1, "AA");
			}
		});
		Thread b = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				inner.play2(1, "BB");
			}
		});
		a.start();
		b.start();
	}

}

class Thread13Test {
	static class Inner {

		public synchronized void play(int a, String name) {
			for (int i = 0; i < 10; i++) {
				System.out.println(name + " == " + a++);
			}
		}

		public synchronized void play2(int a, String name) {
			for (int i = 0; i < 10; i++) {
				System.out.println(name + " == " + a++);
			}
		}
	}
}
