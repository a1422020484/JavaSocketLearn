package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class SpringMainDemo1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/springMain.xml");// 此文件放在SRC目录下
		Boos boos = (Boos) context.getBean("boos");
		boos.test();
	}

}
