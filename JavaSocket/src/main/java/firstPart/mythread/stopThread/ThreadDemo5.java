package firstPart.mythread.stopThread;

public class ThreadDemo5 {

	public static void main(String[] args) {
		User user = new User();
		ThreadDemo5Test threadDemo5Test = new ThreadDemo5Test(user);
		threadDemo5Test.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		threadDemo5Test.stop();
		System.out.println("username : " + user.getUsername() + " password : " + user.getPassword());
	}

}

class User {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class ThreadDemo5Test extends Thread {
	
	private User user;
	
	public ThreadDemo5Test(User user){
		super();
		this.user = user;
	}
	
	@Override
	public void run() {
		user.doSomething("b", "bb");
	}
}