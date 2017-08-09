package thirdPart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangxp
 * @date 2017年8月8日 上午10:44:53
 * <p>
 * 不使用于线程通信，相当于线程检测 
 */
public class ThreadDemo1 {

	public static void main(String[] args) {
		MyList1 list = new MyList1();
		ThreadDemo1TestA threadDemo1TestA = new ThreadDemo1TestA(list);
		threadDemo1TestA.setName("A");
		threadDemo1TestA.start();
		ThreadDemo1TestB threadDemo1TestB = new ThreadDemo1TestB(list);
		threadDemo1TestB.setName("B");
		threadDemo1TestB.start();
	}

}

class ThreadDemo1TestA extends Thread {

	private MyList1 list;

	public ThreadDemo1TestA(MyList1 list) {
		this.list = list;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				list.addName();
				System.out.println("添加了" + (i + 1) + "个元素");
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ThreadDemo1TestB extends Thread {
	private MyList1 list;

	public ThreadDemo1TestB(MyList1 list) {
		super();
		this.list = list;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(200);
				if (list.getSize() == 5) {
					System.out.println("B 线程结束了");
					throw new InterruptedException();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class MyList1 {
	private List<String> list = new ArrayList<String>();

	public void addName() {
		list.add("张建国");
	}

	public int getSize() {
		return list.size();
	}

}