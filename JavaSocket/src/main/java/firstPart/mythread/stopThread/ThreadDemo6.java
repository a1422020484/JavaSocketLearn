package firstPart.mythread.stopThread;

/**
 * @author Administrator
 * ThreadDemo6 和 ThreadDemo5 是一样的
 */
public class ThreadDemo6 {
	
	public static void main(String[] args) {
		User1 user1 = new User1();
		ThreadDemo6Test threadDemo6Test = new ThreadDemo6Test(user1);
		threadDemo6Test.start();
		try {
			Thread.sleep(500); //目的是让start运行一会儿
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		threadDemo6Test.stop();
		
		System.out.println("username " + user1.getUsername() + " password :" + user1.getPassword());
	}
}

class ThreadDemo6Test extends Thread {
	private User1 user;
	
	public ThreadDemo6Test(User1 user){
		this.user = user;
	}
	
	@Override
	public void run() {
		user.doSomething("b", "bb");
	}
}

class User1 {
	private String username = "a";
	private String password = "aa";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	synchronized void doSomething(String username, String password) {
		try {
			this.username = username;
			Thread.sleep(10000);
			this.password = password;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
