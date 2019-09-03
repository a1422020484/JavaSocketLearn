package spring.myBatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import spring.myBatis.mapper.UserMapper;
import spring.po.User;

@Controller
public class MybatisMgr {

	@Autowired
	private UserMapper userMapper;

	public void queryName(int id) {
		System.out.println(userMapper.getName(id));
		System.out.println("----------------------------------");
	};
	
	public void queryUser(int id) {
		User user = userMapper.getUser(id);
		System.out.println(user.toString());
	}
	
	public void queryAllUser() {
		List<User> user = userMapper.getAllUser();
		System.out.println(user);
	}
}
