package secondPart;

/**
 * @author yangxp
 * @date 2017年8月3日 下午5:24:19
 *       <p>
 *       脏读 <i>dirtyRead</i> 注意取值的时候也可能出现意外：在读取实例变量的时候，此值已经别其他线程更改过了
 * @See ThreadDemo3
 */
public class ThreadDemo4 {

	public static void main(String[] args) {
		PublicVar publicVar = new PublicVar();
		ThreadDemo4Test threadDemo4Test = new ThreadDemo4Test(publicVar);
		threadDemo4Test.start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		publicVar.getValue();

	}

}

class PublicVar {
	public String username = "A";
	public String password = "AA";

	public synchronized void setValue(String username, String password) {
		try {
			this.username = username;
			Thread.sleep(5000);
			this.username = password;
			System.out.println(Thread.currentThread().getName() + " username = " + username + " password " + password);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 该方法不是同步的
	 */
	public void getValue() {
		System.out.println(Thread.currentThread().getName() + " username = " + username + " password " + password);
	}
}

class ThreadDemo4Test extends Thread {

	private PublicVar publicVar;

	public ThreadDemo4Test(PublicVar publicVar) {
		super();
		this.publicVar = publicVar;
	}

	@Override
	public void run() {
		super.run();
		publicVar.setValue("B", "BB");
	}
}