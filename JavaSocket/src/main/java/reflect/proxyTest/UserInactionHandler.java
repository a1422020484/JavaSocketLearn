package reflect.proxyTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserInactionHandler implements InvocationHandler {

	private Object target;

	public Object getProxyInstance(Class<?> clazz) {
		try {
			this.target = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		Object result;
		System.out.println("before target method .... ");
		result = method.invoke(target, args);
		System.out.println("after target method .... ");
		return result;
	}

}
