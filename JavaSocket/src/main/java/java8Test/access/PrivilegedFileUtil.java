package java8Test.access;

import java.io.File;
import java.io.IOException;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class PrivilegedFileUtil {
	public static boolean canRead(String fileName) {
		try {
			// 尝试普通方式创建一个新文件
			File fs = new File(fileName);
			return fs.canRead();
		} catch (AccessControlException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void makeFile(String fileName) {
		try {
			// 尝试普通方式创建一个新文件
			File fs = new File(fileName);
			fs.createNewFile();
		} catch (AccessControlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void doPrivilegedAction(final String fileName) {
		// 用特权访问方式创建文件
		AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				makeFile(fileName);
				return null;
			}
		});
	}

}
