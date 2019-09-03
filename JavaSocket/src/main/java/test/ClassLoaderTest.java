package test;

import java.io.IOException;
import java.io.InputStream;

/*** 类加载器与instanceof关键字演示**@authorzzm */
public class ClassLoaderTest {
	public static void main(String[] args) throws Exception {
		ClassLoader myLoader = new ClassLoader() {
			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException {
				try {
					String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
					InputStream is = getClass().getResourceAsStream(fileName);
					if (is == null) {
						return super.loadClass(name);
					}
					byte[] b = new byte[is.available()];
					is.read(b);
					return defineClass(name, b, 0, b.length);
				} catch (IOException e) {
					throw new ClassNotFoundException(name);
				}
			}
		};
		Object obj = myLoader.loadClass("test.ClassLoaderTest").newInstance();
		System.out.println(obj.getClass());
		System.out.println(obj instanceof test.ClassLoaderTest);
		
		ClassLoader classLoaderMy = myLoader.getParent();
		System.out.println(classLoaderMy.getParent());
		
		ClassLoader classLoaderMy1 = classLoaderMy.getParent();
		System.out.println(classLoaderMy1.getParent());
		
//		==========================================================================
		
		ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
		System.out.println(classLoader);
		
		ClassLoader classLoader1 = classLoader.getParent();
		System.out.println(classLoader1);
		
		ClassLoader classLoader2 = classLoader1.getParent();
		System.out.println(classLoader2);
	}
}
