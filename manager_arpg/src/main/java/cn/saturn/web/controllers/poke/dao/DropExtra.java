package cn.saturn.web.controllers.poke.dao;

import java.util.Arrays;

public class DropExtra {
	
	protected int id; // id
	protected String dropname;
	protected int open_state; // 时间类型 0 普通时间 1开服时间
	protected String openTime;; // 开服时间
	protected String endTime; // 结束时间
	protected int openDate; // 开服天数
	protected int endDate;
	protected int srvs[];  //需要发送的服务器
	protected String extr;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDropname() {
		return dropname;
	}
	public void setDropname(String dropname) {
		this.dropname = dropname;
	}
	public int getOpen_state() {
		return open_state;
	}
	public void setOpen_state(int open_state) {
		this.open_state = open_state;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getExtr() {
		return extr;
	}
	public void setExtr(String extr) {
		this.extr = extr;
	}
	public int getOpenDate() {
		return openDate;
	}
	public void setOpenDate(int openDate) {
		this.openDate = openDate;
	}
	public int getEndDate() {
		return endDate;
	}
	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}
	public int[] getSrvs() {
		return srvs;
	}
	public void setSrvs(int[] srvs) {
		this.srvs = srvs;
	}
	@Override
	public String toString() {
		return "DropExtra [id=" + id + ", dropname=" + dropname + ", open_state=" + open_state + ", openTime="
				+ openTime + ", endTime=" + endTime + ", openDate=" + openDate + ", endDate=" + endDate + ", srvs="
				+ Arrays.toString(srvs) + ", extr=" + extr + "]";
	}
	
	
	
}
