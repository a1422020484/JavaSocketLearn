package cn.saturn.web.controllers.power.dao;

public class AccountWarnWhiteModel {
	private int id;
	private String accountid;
	private int roleid;
	private String playername;
	private int level;
	private int viplevel;
	private int yesdcrystal;
	private int todcrystal;
	private int gold;
	private int srvid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
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
	public int getSrvid() {
		return srvid;
	}
	public void setSrvid(int srvid) {
		this.srvid = srvid;
	}
	
	public int getYesdcrystal() {
		return yesdcrystal;
	}
	public void setYesdcrystal(int yesdcrystal) {
		this.yesdcrystal = yesdcrystal;
	}
	public int getTodcrystal() {
		return todcrystal;
	}
	public void setTodcrystal(int todcrystal) {
		this.todcrystal = todcrystal;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public AccountWarnWhiteModel() {
		super();
	}
	public AccountWarnWhiteModel(int id, String accountid, int roleid, String playername, int level, int viplevel,
			int yesdcrystal, int todcrystal, int gold, int srvid) {
		super();
		this.id = id;
		this.accountid = accountid;
		this.roleid = roleid;
		this.playername = playername;
		this.level = level;
		this.viplevel = viplevel;
		this.yesdcrystal = yesdcrystal;
		this.todcrystal = todcrystal;
		this.gold = gold;
		this.srvid = srvid;
	}
	@Override
	public String toString() {
		return  "账号id=" + accountid + ", 角色Id=" + roleid +", 服务器id=" + srvid + ", 等级=" + level + ", vip等级=" + viplevel + ", 昨日水晶=" + yesdcrystal
				+ ", 今日水晶=" + todcrystal + ", 金币=" + gold +  ", 玩家名字="+ playername + ";";
	}
	
	
	
}
