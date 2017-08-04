package secondPart;

/**
 * @author yangxp
 * @date 2017年8月3日 下午5:57:24
 *       <p>
 *       锁的重入：当一个线程得到对象的锁后，再次请求此对象锁是可以再次得到该对象锁的。这也证明了再一个 synchronized
 *       方法块的内部调用本类的其他 锁方法是可以得到锁的。
 */
public class ThreadDemo5 {
	public static void main(String[] args) {
		ThreadDemo5Test threadDemo5Test = new ThreadDemo5Test();
		threadDemo5Test.start();
	}
}

class ThreadDemo5Test extends Thread{
	@Override
	public void run(){
		Service service = new Service();
		service.service1();
	}
}

class Service {
	public synchronized void service1() {
		System.out.println("service 1 ");
		service2();
	}

	public synchronized void service2() {
		System.out.println("service 2");
		service3();
	}

	public synchronized void service3() {
		System.out.println("service 3");
	}
}