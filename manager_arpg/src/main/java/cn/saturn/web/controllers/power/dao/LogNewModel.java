package cn.saturn.web.controllers.power.dao;

import java.util.Date;

public class LogNewModel {
	private int id;
	private int player_id;
	private String name;
	private String type1;
	private String type2;
	private Date log_time;
	private int crystal;
	private int gold;
	private int action_power;
	private int level;
	private int vip_level;
	private String content;
	private long time_point;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public Date getLog_time() {
		return log_time;
	}
	public void setLog_time(Date log_time) {
		this.log_time = log_time;
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
	public int getAction_power() {
		return action_power;
	}
	public void setAction_power(int action_power) {
		this.action_power = action_power;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getVip_level() {
		return vip_level;
	}
	public void setVip_level(int vip_level) {
		this.vip_level = vip_level;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public long getTime_point() {
		return time_point;
	}
	public void setTime_point(long time_point) {
		this.time_point = time_point;
	}
	public LogNewModel() {
		super();
	}
	public LogNewModel(int id, int player_id, String name, String type1, String type2, Date log_time, int crystal,
			int gold, int action_power, int level, int vip_level, String content, long time_point) {
		super();
		this.id = id;
		this.player_id = player_id;
		this.name = name;
		this.type1 = type1;
		this.type2 = type2;
		this.log_time = log_time;
		this.crystal = crystal;
		this.gold = gold;
		this.action_power = action_power;
		this.level = level;
		this.vip_level = vip_level;
		this.content = content;
		this.time_point = time_point;
	}
	@Override
	public String toString() {
		return "LogNewModel [id=" + id + ", player_id=" + player_id + ", name=" + name + ", type1=" + type1 + ", type2="
				+ type2 + ", log_time=" + log_time + ", crystal=" + crystal + ", gold=" + gold + ", action_power="
				+ action_power + ", level=" + level + ", vip_level=" + vip_level + ", content=" + content
				+ ", time_point=" + time_point + "]";
	}
	
	
	
	

}
