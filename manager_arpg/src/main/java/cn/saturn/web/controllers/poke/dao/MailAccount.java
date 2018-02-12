package cn.saturn.web.controllers.poke.dao;

public class MailAccount {
	
	private  int id;
	private String  account;
	private  String platform;
	
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
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public MailAccount() {
		super();
	}
	public MailAccount(int id, String account, String platform) {
		super();
		this.id = id;
		this.account = account;
		this.platform = platform;
	}
	@Override
	public String toString() {
		return "MailAccount [id=" + id + ", account=" + account + ", platform=" + platform + "]";
	}
	

}
