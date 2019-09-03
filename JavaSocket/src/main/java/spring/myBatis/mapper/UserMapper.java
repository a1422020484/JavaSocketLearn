package spring.myBatis.mapper;

import java.util.List;

import spring.po.User;

public interface UserMapper {
	String getName(int id);
	
	User getUser(int id);
	
	List<User> getAllUser();

}
