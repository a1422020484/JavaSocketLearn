package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;

public class AccountBindModelToStatic {
	private int id;
	private  int account_id;
	private int srv_id;
	private int player_id;
	private String player_name;
	private  int player_lv;
	private Date create_time;
	private String iconUrl;
	private  String iconFrame;
	private String  fightingCapacity;
	private  int vipLv;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public int getSrv_id() {
		return srv_id;
	}
	public void setSrv_id(int srv_id) {
		this.srv_id = srv_id;
	}
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public String getPlayer_name() {
		return player_name;
	}
	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}
	public int getPlayer_lv() {
		return player_lv;
	}
	public void setPlayer_lv(int player_lv) {
		this.player_lv = player_lv;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getIconFrame() {
		return iconFrame;
	}
	public void setIconFrame(String iconFrame) {
		this.iconFrame = iconFrame;
	}
	public String getFightingCapacity() {
		return fightingCapacity;
	}
	public void setFightingCapacity(String fightingCapacity) {
		this.fightingCapacity = fightingCapacity;
	}
	public int getVipLv() {
		return vipLv;
	}
	public void setVipLv(int vipLv) {
		this.vipLv = vipLv;
	}
	public AccountBindModelToStatic() {
		super();
	}
	public AccountBindModelToStatic(int id, int account_id, int srv_id, int player_id, String player_name, int player_lv,
			Date create_time, String iconUrl, String iconFrame, String fightingCapacity, int vipLv) {
		super();
		this.id = id;
		this.account_id = account_id;
		this.srv_id = srv_id;
		this.player_id = player_id;
		this.player_name = player_name;
		this.player_lv = player_lv;
		this.create_time = create_time;
		this.iconUrl = iconUrl;
		this.iconFrame = iconFrame;
		this.fightingCapacity = fightingCapacity;
		this.vipLv = vipLv;
	}
	@Override
	public String toString() {
		return "AccountBindToStatic [id=" + id + ", account_id=" + account_id + ", srv_id=" + srv_id + ", player_id="
				+ player_id + ", player_name=" + player_name + ", player_lv=" + player_lv + ", create_time="
				+ create_time + ", iconUrl=" + iconUrl + ", iconFrame=" + iconFrame + ", fightingCapacity="
				+ fightingCapacity + ", vipLv=" + vipLv + "]";
	}
	
	
}
