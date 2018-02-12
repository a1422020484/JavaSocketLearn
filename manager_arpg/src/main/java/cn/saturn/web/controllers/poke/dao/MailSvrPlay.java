package cn.saturn.web.controllers.poke.dao;

public class MailSvrPlay {
	
	private  int srvId;
	private int playerId;
	private int crystal;
	
	public int getSrvId() {
		return srvId;
	}
	public void setSrvId(int srvId) {
		this.srvId = srvId;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getCrystal() {
		return crystal;
	}
	public void setCrystal(int crystal) {
		this.crystal = crystal;
	}
	public MailSvrPlay() {
		super();
	}
	public MailSvrPlay(int srvId, int playerId, int crystal) {
		super();
		this.srvId = srvId;
		this.playerId = playerId;
		this.crystal = crystal;
	}
	
}
