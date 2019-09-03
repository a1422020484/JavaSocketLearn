package spring.aop.AnnoationTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import spring.aop.user.Boss;

@Controller
public class SpringAopAnnotion {
	
	
	public void aopDemo() {
		System.out.println("SpringAopAnnotion  --- ");
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/springAop.xml");// 此文件放在SRC目录下
		SpringAopAnnotion boss = (SpringAopAnnotion) context.getBean("springAopAnnotion");
		boss.aopDemo();
	}
}
