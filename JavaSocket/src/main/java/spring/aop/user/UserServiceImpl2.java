package spring.aop.user;

import org.springframework.stereotype.Controller;

@Controller
public class UserServiceImpl2 implements UserService {

	@Override
	public void addUser(User user) {
		// Db.addUser(user);
		System.out.println("UserServiceImpl2 添加成功");
	}

	@Override
	public void deleteUser(String name) {
		// Db.deleteUser(name);
		System.out.println("UserServiceImpl2 删除成功");
	}

}
