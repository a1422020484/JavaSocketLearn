package secondPart;

import java.util.ArrayList;
import java.util.List;

public class ThreadDemo9 {

	public static void main(String[] args) throws Exception {
		ThreadDemo9Object list = new ThreadDemo9Object();
		ThreadA9 thread1 = new ThreadA9(list);
		thread1.setName("A");
		thread1.start();
		ThreadB9 thread2 = new ThreadB9(list);
		thread2.setName("B");
		thread2.start();
		Thread.sleep(6000);
		System.out.println("ListSIze == " + list.getSize());
	}

}

class ThreadDemo9Object {

	private List<String> list = new ArrayList<String>();

	synchronized public void add(String data) {
		list.add(data);
	}

	synchronized public int getSize() {
		return list.size();
	}

	public ThreadDemo9Object addServiceMethod(ThreadDemo9Object list, String data) {
		try {
			synchronized (list) {
				Thread.sleep(2000);
				if (list.getSize() < 1) {
					list.add(data);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return list;
	}
}

class ThreadA9 extends Thread {
	private ThreadDemo9Object threadDemo9Object;

	public ThreadA9(ThreadDemo9Object threadDemo9Object) {
		super();
		this.threadDemo9Object = threadDemo9Object;
	}

	@Override
	public void run() {
		super.run();
		threadDemo9Object.addServiceMethod(threadDemo9Object, "AA");
	}
}

class ThreadB9 extends Thread {
	private ThreadDemo9Object threadDemo9Object;

	public ThreadB9(ThreadDemo9Object threadDemo9Object) {
		super();
		this.threadDemo9Object = threadDemo9Object;
	}

	@Override
	public void run() {
		super.run();
		threadDemo9Object.addServiceMethod(threadDemo9Object, "BB");
	}
}