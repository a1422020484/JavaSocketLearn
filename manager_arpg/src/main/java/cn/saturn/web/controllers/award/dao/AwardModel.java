package cn.saturn.web.controllers.award.dao;

public class AwardModel {
	private int svrId;
	
	private int account_id;
	
	private String account;
	
	private int player_id;
	
	private int num;

	public int getSvrId() {
		return svrId;
	}

	public void setSvrId(int svrId) {
		this.svrId = svrId;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
}
