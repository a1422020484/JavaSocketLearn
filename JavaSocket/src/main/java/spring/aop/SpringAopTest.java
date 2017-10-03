package spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import spring.aop.user.Boss;

@Controller
public class SpringAopTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/springAop.xml");// 此文件放在SRC目录下
		Boss boss = (Boss) context.getBean("boss");
		boss.addUser1();
		boss.addUser2();
	}

}
