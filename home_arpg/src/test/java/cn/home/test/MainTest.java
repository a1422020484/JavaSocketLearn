package cn.home.test;

import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		org.springframework.core.io.Resource res = context.getResource("config.properties");
		
		URL url = ClassLoader.getSystemResource("config.properties");
		System.out.println("呵呵:" + url);
		
		System.out.println("context" + context);

	}
}
