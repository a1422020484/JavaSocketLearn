package spring.myBatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import spring.myBatis.mapper.UserMapper;

@Controller
public class MybatisMgr {
	
	@Autowired
	private UserMapper userMapper;

	public void queryName() {
		System.out.println(userMapper.getUsername());

	};
}
