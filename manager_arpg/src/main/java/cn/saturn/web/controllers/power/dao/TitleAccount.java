package cn.saturn.web.controllers.power.dao;

public class TitleAccount {
	
	private String account_id; 
	private String pid;
	private String name;
	private String player_id;
	private String player_name;
	private String player_lv;
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(String player_id) {
		this.player_id = player_id;
	}
	public String getPlayer_name() {
		return player_name;
	}
	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}
	public String getPlayer_lv() {
		return player_lv;
	}
	public void setPlayer_lv(String player_lv) {
		this.player_lv = player_lv;
	}
	
	public TitleAccount() {
		super();
	}
	public TitleAccount(String account_id, String pid, String name, String player_id, String player_name,
			String player_lv) {
		super();
		this.account_id = account_id;
		this.pid = pid;
		this.name = name;
		this.player_id = player_id;
		this.player_name = player_name;
		this.player_lv = player_lv;
	}
	
	
	

}
