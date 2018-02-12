package cn.saturn.web.controllers.poke.dao;

public class SendMailA {
	private int srvId;
	private int playerId;
	private String playerName;
	private String ext;
	private int sId;
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
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public int getsId() {
		return sId;
	}
	public void setsId(int sId) {
		this.sId = sId;
	}
	public SendMailA() {
		super();
	}
	public SendMailA(int srvId, int playerId, String playerName, String ext, int sId) {
		super();
		this.srvId = srvId;
		this.playerId = playerId;
		this.playerName = playerName;
		this.ext = ext;
		this.sId = sId;
	}
}
