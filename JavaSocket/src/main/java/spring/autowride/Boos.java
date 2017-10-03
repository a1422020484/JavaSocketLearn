package spring.autowride;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class Boos {
	
	@Autowired
	public HelloWorld helloWorld;
	
	public void test(){
		System.out.println(helloWorld.getUsernameString());
	}
}
