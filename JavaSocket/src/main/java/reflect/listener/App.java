package reflect.listener;

import java.util.Iterator;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/springAction.xml");// 此文件放在SRC目录下
		Map<String, ListenerEvent> loaderMap = context.getBeansOfType(ListenerEvent.class);
		Iterator<ListenerEvent> list = loaderMap.values().iterator();
		while (list.hasNext()) {
			list.next().putIntoListener();
		}
		EventManager.getEventManager().tiggerEvent(1);
		
		EventManager.getEventManager().tiggerEvent(2);
	}
}
