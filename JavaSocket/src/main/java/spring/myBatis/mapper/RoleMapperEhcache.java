package spring.myBatis.mapper;

import spring.po.Role;

public interface RoleMapperEhcache {
	
	Role queryRoleById(int id);
	
	void insertOneRole(Role role);
}
