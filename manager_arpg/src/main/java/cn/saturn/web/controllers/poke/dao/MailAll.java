package cn.saturn.web.controllers.poke.dao;

import java.util.Date;

public class MailAll {
	
	private  int srvId;
	private int playerId;
	private String award;
	private Date createtime;
	
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
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	
	
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public MailAll() {
		super();
	}
	
	
	public MailAll(int srvId, int playerId, String award, Date createtime) {
		super();
		this.srvId = srvId;
		this.playerId = playerId;
		this.award = award;
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "MailAll [srvId=" + srvId + ", playerId=" + playerId + ", award=" + award + ", createtime=" + createtime
				+ "]";
	}
	
	
}
