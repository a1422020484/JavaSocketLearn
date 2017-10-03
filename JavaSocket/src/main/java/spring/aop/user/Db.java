package spring.aop.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Db {

	private static List<User> userList = new ArrayList<User>();

	public static void addUser(User user) {
		userList.add(user);
	}

	public static void deleteUser(String name) {
		Iterator iterator = userList.iterator();
		while (iterator.hasNext()) {
			User user = (User) iterator.next();
			if (user.getName().equals(name)) {
				iterator.remove();
			}
		}
	}
}
