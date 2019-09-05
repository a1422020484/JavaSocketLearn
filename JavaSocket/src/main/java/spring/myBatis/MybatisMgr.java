package spring.myBatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import spring.myBatis.mapper.RoleMapperEhcache;
import spring.myBatis.mapper.UserMapper;
import spring.myBatis.mapper.UserMapperCached;
import spring.po.Role;
import spring.po.User;

@Controller
public class MybatisMgr {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserMapperCached userMapperCached;
	@Autowired
	private RoleMapperEhcache roleMapperEhcache;

	public void queryName(int id) {
		System.out.println(userMapper.getName(id));
	};
	
	public void queryUser(int id) {
		User user = userMapper.getUser(id);
		System.out.println(user.toString());
	}
	
	public void queryAllUser() {
		List<User> user = userMapper.getAllUser();
		System.out.println(user);
	}
	
	public void updateOneUserName(User user) {
		userMapper.updateOneUserName(user);
	}
	
	public void queryNameCached(int id) {
		System.out.println(userMapperCached.getName(id));
	};
	
	public User queryUserCachedById(int id) {
		return userMapperCached.queryUserCachedById(id);
	}
	
	public Role queryRoleCachedById(int id) {
		return roleMapperEhcache.queryRoleById(id);
	}
}
