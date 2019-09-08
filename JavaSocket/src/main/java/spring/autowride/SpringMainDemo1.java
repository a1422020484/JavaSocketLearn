package spring.autowride;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class SpringMainDemo1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/springMain.xml");// 此文件放在SRC目录下
		
		//spring @Autowired 和 getBean() 的区别（多例 prototype）
		HelloWorld helloWorld = (HelloWorld) context.getBean("HelloWorld");
		System.out.println(helloWorld.getUsernameString());
		System.out.println(helloWorld);
		Boos boos = (Boos) context.getBean("boos");
		boos.test();
	}

}
