package firstPart.mythread;

public class MyServletTest {

	private static String usernameRef;
	private static String passwordRef;

	public static void main(String[] args) {
		LoginA loginA1 = new LoginA("a", "aa");
		LoginA loginA2 = new LoginA("b", "bb");
		loginA1.start();
		loginA2.start();
	}

	public static synchronized void doPost(String username, String password) {
		try {
			usernameRef = username;
			if (usernameRef.equals("a")) {
				Thread.sleep(5000);
			}
			passwordRef = password;
			System.out.println("username = " + usernameRef + " password " + passwordRef);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

class LoginA extends Thread{
	private String username;
	private String password;

	public LoginA(String username, String password) {
		this.username = username;
		this.password = password;
	}

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

	@Override
	public void run(){
		MyServletTest.doPost(username, password);
	}
}
