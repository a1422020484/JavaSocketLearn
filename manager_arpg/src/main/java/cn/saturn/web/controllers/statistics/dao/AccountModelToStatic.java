package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;

public class AccountModelToStatic {
	private int  id;
	private  String account;
	private  String password;
	private String platform;
	private  Date create_time;
	private int  prev_srv_id;
	private int login_key;
	private int vindicator;
	private String systemInfo;
	private Date login_time;
	private String lastip;
	private String version;
	private String platform_ext;
	private int accountActived;
	private String subPlatform;
	private String third_user_id;
	private String cdk_types;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public int getPrev_srv_id() {
		return prev_srv_id;
	}
	public void setPrev_srv_id(int prev_srv_id) {
		this.prev_srv_id = prev_srv_id;
	}
	public int getLogin_key() {
		return login_key;
	}
	public void setLogin_key(int login_key) {
		this.login_key = login_key;
	}
	public int getVindicator() {
		return vindicator;
	}
	public void setVindicator(int vindicator) {
		this.vindicator = vindicator;
	}
	public String getSystemInfo() {
		return systemInfo;
	}
	public void setSystemInfo(String systemInfo) {
		this.systemInfo = systemInfo;
	}
	public Date getLogin_time() {
		return login_time;
	}
	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}
	public String getLastip() {
		return lastip;
	}
	public void setLastip(String lastip) {
		this.lastip = lastip;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPlatform_ext() {
		return platform_ext;
	}
	public void setPlatform_ext(String platform_ext) {
		this.platform_ext = platform_ext;
	}
	public int getAccountActived() {
		return accountActived;
	}
	public void setAccountActived(int accountActived) {
		this.accountActived = accountActived;
	}
	public String getSubPlatform() {
		return subPlatform;
	}
	public void setSubPlatform(String subPlatform) {
		this.subPlatform = subPlatform;
	}
	public String getThird_user_id() {
		return third_user_id;
	}
	public void setThird_user_id(String third_user_id) {
		this.third_user_id = third_user_id;
	}
	public String getCdk_types() {
		return cdk_types;
	}
	public void setCdk_types(String cdk_types) {
		this.cdk_types = cdk_types;
	}
	public AccountModelToStatic() {
		super();
	}
	public AccountModelToStatic(int id, String account, String password, String platform, Date create_time, int prev_srv_id,
			int login_key, int vindicator, String systemInfo, Date login_time, String lastip, String version,
			String platform_ext, int accountActived, String subPlatform, String third_user_id, String cdk_types) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.platform = platform;
		this.create_time = create_time;
		this.prev_srv_id = prev_srv_id;
		this.login_key = login_key;
		this.vindicator = vindicator;
		this.systemInfo = systemInfo;
		this.login_time = login_time;
		this.lastip = lastip;
		this.version = version;
		this.platform_ext = platform_ext;
		this.accountActived = accountActived;
		this.subPlatform = subPlatform;
		this.third_user_id = third_user_id;
		this.cdk_types = cdk_types;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", account=" + account + ", password=" + password + ", platform=" + platform
				+ ", create_time=" + create_time + ", prev_srv_id=" + prev_srv_id + ", login_key=" + login_key
				+ ", vindicator=" + vindicator + ", systemInfo=" + systemInfo + ", login_time=" + login_time
				+ ", lastip=" + lastip + ", version=" + version + ", platform_ext=" + platform_ext + ", accountActived="
				+ accountActived + ", subPlatform=" + subPlatform + ", third_user_id=" + third_user_id + ", cdk_types="
				+ cdk_types + "]";
	}
	
	
	
}
