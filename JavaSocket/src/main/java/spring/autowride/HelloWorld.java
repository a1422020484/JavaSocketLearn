package spring.autowride;

import org.springframework.stereotype.Controller;

@Controller
public class HelloWorld {

	public HelloWorld() {
		System.out.println("helloworld intializer");
		System.out.println(usernameString);
	}

	public String usernameString = "";

	public String getUsernameString() {
		return usernameString;
	}

	public void setUsernameString(String usernameString) {
		this.usernameString = usernameString;
	}

}
