package reflect;

import java.lang.reflect.Method;

public class TestClassLoad {
	public static void main(String[] args) throws Exception {
		Class<?> clz = A.class;
		Object o = clz.newInstance();
		Method m = clz.getMethod("foo", String.class);
		for (int i = 0; i < 20; i++) {
			m.invoke(o, Integer.toString(i));
		}
	}
}

class A {
	public void foo(String name) {
		System.out.println("Hello, " + name);
	}
}