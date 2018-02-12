package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;

public class LoginLogModelToStatic {
	
	private int account_id;
	private int server_id;
	private String platform;
	private Date register_time;
	private Date last_log_time;
	
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public int getServer_id() {
		return server_id;
	}
	public void setServer_id(int server_id) {
		this.server_id = server_id;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public Date getRegister_time() {
		return register_time;
	}
	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}
	public Date getLast_log_time() {
		return last_log_time;
	}
	public void setLast_log_time(Date last_log_time) {
		this.last_log_time = last_log_time;
	}
	public LoginLogModelToStatic() {
		super();
	}
	public LoginLogModelToStatic(int account_id, int server_id, String platform, Date register_time, Date last_log_time) {
		super();
		this.account_id = account_id;
		this.server_id = server_id;
		this.platform = platform;
		this.register_time = register_time;
		this.last_log_time = last_log_time;
	}
	@Override
	public String toString() {
		return "LoginLog [account_id=" + account_id + ", server_id=" + server_id + ", platform=" + platform
				+ ", register_time=" + register_time + ", last_log_time=" + last_log_time + "]";
	}
	
	
}
