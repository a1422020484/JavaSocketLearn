package java8Test.access;

import java.security.AccessControlException;

import sun.nio.ch.SelectorImpl;

public class AccessControllerTest {
	public static void main(String[] args) {

		// 打开系统安全权限检查开关
//		System.setSecurityManager(new SecurityManager());

		try {
			
			
			Class.forName("sun.nio.ch.SelectorImpl").getClass().toString();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			String pathName = "G:\\Users\\yang\\Desktop\\test1\\test-path-product-1.txt";
			System.out.println(PrivilegedFileUtil.canRead(pathName));
		} catch (AccessControlException e1) {
			e1.printStackTrace();

		}

		try {
			String pathName = "G:\\Users\\yang\\Desktop\\test1\\test-wtite.txt";
			PrivilegedFileUtil.makeFile(pathName);

		} catch (AccessControlException e1) {
			e1.printStackTrace();
		}

		try {
			String pathName = "G:\\Users\\yang\\Desktop\\test1\\test-wtite-use-Privileged.txt";
			PrivilegedFileUtil.doPrivilegedAction(pathName);

		} catch (AccessControlException e1) {
			e1.printStackTrace();
		}

	}
}
