package spring.aop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class Boss {
	// 这个是连个不同的初始化
	@Autowired
	private UserService userServiceImpl2;
	@Autowired
	private UserService userServiceImpl;

	public void addUser1() {
		userServiceImpl.addUser(new User("张三"));
	}

	public void addUser2() {
		userServiceImpl2.addUser(new User("张三"));
	}

	public void deleteUser() {
		userServiceImpl.deleteUser("张三");
	}
}
