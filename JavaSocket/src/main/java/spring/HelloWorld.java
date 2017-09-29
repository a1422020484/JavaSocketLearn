package spring;

import org.springframework.stereotype.Controller;

@Controller
public class HelloWorld {

	public HelloWorld() {
		System.out.println("helloworld intializer");
		setUsernameString("t");
	}

	private String usernameString = "";

	public String getUsernameString() {
		return usernameString;
	}

	public void setUsernameString(String usernameString) {
		this.usernameString = usernameString;
	}

}
