package cn.saturn.web.controllers.login.dao;

import org.springframework.beans.BeanUtils;

import cn.saturn.web.utils.AuthorityUtils;

public class UserModel {
	public static final int authority_admin = 10;
	public static final int authority_normal = 0;
	public static final int authority_one = 1;
	public static final int authority_two = 2;
	public static final int authority_three = 3;

	private int id;
	private String username;
	private int authority = authority_normal;
	private String remark = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAuthority() {
		return authority;
	}

	public String getAuthorityStr() {
		return BeanUtils.instantiateClass(AuthorityUtils.class).get(authority);
	}

	/** 检测权限 **/
	public boolean checkAuthority(int modeId) {
		return BeanUtils.instantiateClass(AuthorityUtils.class).checkAuthority(authority, modeId);
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
