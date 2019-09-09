package spring.myBatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import spring.myBatis.mapper.RoleMapper;
import spring.myBatis.mapper.RoleMapperEhcache;
import spring.myBatis.mapper.UserMapper;
import spring.myBatis.mapper.UserMapperCached;
import spring.myBatis.mapperTest.UserMapperTest;
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
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserMapperTest userMapperTest;

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
	public int insertOneUser(User user) {
		return userMapper.insertOneUser(user);
	}
	
//	使用注解的spring 事务管理测试
	@Transactional
	public int insertOneUserTransaction(User user) {
		int insertNum = 0;
		insertNum = userMapper.insertOneUser(user);
//		if(insertNum == 1) {
//			throw new RuntimeException("test");//抛出unchecked异常，触发事物，回滚
//		}
		return insertNum;
	}
	
	public void insertMoreUser(List<User> userList) {
		userMapper.insertMoreUser(userList);
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
	
	public void queryNameTest(int id) {
		System.out.println(userMapperTest.getNameTest(id));
	}
	
	@Transactional
	public void insertOneRoleCache(Role role) {
		roleMapperEhcache.insertOneRole(role);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Transactional(propagation = Propagation.NEVER)
	public void insertOneRole(Role role) {
		roleMapper.insertOneRole(role);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void insertOneUserPropagationTransaction(User user, Role role) {
		int i = insertOneUser(user);
		if(i == 1) {
			throw new RuntimeException("propagation"); 
		}
		insertOneUser(user);
	}
	
	@Transactional(propagation = Propagation.NEVER)
	public void insertOneUserPRNT(User userNewOne) {
		int i = insertOneUser(userNewOne);
		insertOneRole(Role.build());
		userNewOne.setName("eddd");
		insertOneUser(userNewOne);
	};
}
