package hotReload;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	private static ApplicationContext context;

	public static void main(String[] args) {
		App.context();
	}
	
	public static void context() {
		context = new ClassPathXmlApplicationContext("defaultHotReload.xml");
		context.getBean(ResourceMonitor.class).monitoring();
	}
}
