package cn.saturn.web.controllers.power.dao;

import java.util.Date;

public class AccountWarnSealModel {
	
	private String accountid;
	private int roleid;
	private String playername;
	private int level;
	private int viplevel;
	private int crystal;
	private int gold;
	private int srvid;
	private Date createtime;
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	public String getPlayername() {
		return playername;
	}
	public void setPlayername(String playername) {
		this.playername = playername;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getViplevel() {
		return viplevel;
	}
	public void setViplevel(int viplevel) {
		this.viplevel = viplevel;
	}
	public int getCrystal() {
		return crystal;
	}
	public void setCrystal(int crystal) {
		this.crystal = crystal;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getSrvid() {
		return srvid;
	}
	public void setSrvid(int srvid) {
		this.srvid = srvid;
	}
	
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public AccountWarnSealModel() {
		super();
	}
	
	
	public AccountWarnSealModel(String accountid, int roleid, String playername, int level, int viplevel, int crystal,
			int gold, int srvid, Date createtime) {
		super();
		this.accountid = accountid;
		this.roleid = roleid;
		this.playername = playername;
		this.level = level;
		this.viplevel = viplevel;
		this.crystal = crystal;
		this.gold = gold;
		this.srvid = srvid;
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "账号id=" + accountid + ", 角色id=" + roleid + ", 服务器id="+ srvid 
				+ ", 等级=" + level + ", vip等级=" + viplevel + ", 水晶=" + crystal + ", 金币=" + gold + ", 玩家名字=" + playername+ ";";
	}
	
	

}
