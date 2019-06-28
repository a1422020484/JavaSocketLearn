package annotation.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.esotericsoftware.reflectasm.MethodAccess;

public class MainTest {

	private static Map<Integer, Method> actionMap = new HashMap<>();
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/springAction.xml");
		Map<String, Object> objects = context.getBeansWithAnnotation(Action.class);
		for(String className : objects.keySet()) {
			AbstractAction abstractAction = (AbstractAction) objects.get(className);
			for(Method method : abstractAction.getClass().getDeclaredMethods()) {
				if(method.getAnnotation(Action.class) != null) {
					int id = method.getAnnotation(Action.class).id();
					actionMap.put(id, method);
				}
			}
		}
		
		for(Map.Entry<Integer, Method> method : actionMap.entrySet()) {
			MethodAccess access = MethodAccess.get(method.getClass());
			Class<?>[] params = method.getValue().getParameterTypes();
//			int accessIndex = access.getIndex(1, params);
//			for(Class c : params) {
//				System.out.println(c.getTypeName());
//			}
		}
	}

}
