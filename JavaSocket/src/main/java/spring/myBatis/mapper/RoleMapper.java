package spring.myBatis.mapper;

import spring.po.Role;

public interface RoleMapper {
	
	Role queryRoleById(int id);
	
	void insertOneRole(Role role);
}
