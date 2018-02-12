package cn.saturn.web.controllers.poke.dao;

public class AccountModel {
	
	private  int player_id;
	private  int srv_id;
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public int getSrv_id() {
		return srv_id;
	}
	public void setSrv_id(int srv_id) {
		this.srv_id = srv_id;
	}
	public AccountModel() {
		super();
	}
	public AccountModel(int player_id, int srv_id) {
		super();
		this.player_id = player_id;
		this.srv_id = srv_id;
	}
	@Override
	public String toString() {
		return "AccountModel [player_id=" + player_id + ", srv_id=" + srv_id + "]";
	}
	
	

}
