package cn.saturn.web.controllers.poke.dao;

import java.util.Arrays;

public class TimerCard {
	protected int id; // id
	protected String timeCardName;
	protected int open_state; // 时间类型 0 普通时间 1开服时间
	protected String openTime;; // 开服时间
	protected String endTime; // 结束时间
	protected int openDate; // 开服天数
	protected int endDate;
	protected String openTimeShow;; // 开启展示时间
	protected String endTimeShow; // 结束展示时间
	protected int openDateShow; // 开服天数
	protected int endDateShow;//结束展示时间
	protected int srvs[];  //需要发送的服务器
	protected String timeCardId;  //卡牌ID
	protected String extr;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTimeCardName() {
		return timeCardName;
	}
	public void setTimeCardName(String timeCardName) {
		this.timeCardName = timeCardName;
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
	public String getExtr() {
		return extr;
	}
	public void setExtr(String extr) {
		this.extr = extr;
	}
	public String getOpenTimeShow() {
		return openTimeShow;
	}
	public void setOpenTimeShow(String openTimeShow) {
		this.openTimeShow = openTimeShow;
	}
	public String getEndTimeShow() {
		return endTimeShow;
	}
	public void setEndTimeShow(String endTimeShow) {
		this.endTimeShow = endTimeShow;
	}
	public int getOpenDateShow() {
		return openDateShow;
	}
	public void setOpenDateShow(int openDateShow) {
		this.openDateShow = openDateShow;
	}
	public int getEndDateShow() {
		return endDateShow;
	}
	public void setEndDateShow(int endDateShow) {
		this.endDateShow = endDateShow;
	}
	public String getTimeCardId() {
		return timeCardId;
	}
	public void setTimeCardId(String timeCardId) {
		this.timeCardId = timeCardId;
	}
	public TimerCard() {
		super();
	}
	public TimerCard(int id, String timeCardName, int open_state, String openTime, String endTime, int openDate,
			int endDate, String openTimeShow, String endTimeShow, int openDateShow, int endDateShow, int[] srvs,
			String timeCardId, String extr) {
		super();
		this.id = id;
		this.timeCardName = timeCardName;
		this.open_state = open_state;
		this.openTime = openTime;
		this.endTime = endTime;
		this.openDate = openDate;
		this.endDate = endDate;
		this.openTimeShow = openTimeShow;
		this.endTimeShow = endTimeShow;
		this.openDateShow = openDateShow;
		this.endDateShow = endDateShow;
		this.srvs = srvs;
		this.timeCardId = timeCardId;
		this.extr = extr;
	}
	@Override
	public String toString() {
		return "TimerCard [id=" + id + ", timeCardName=" + timeCardName + ", open_state=" + open_state + ", openTime="
				+ openTime + ", endTime=" + endTime + ", openDate=" + openDate + ", endDate=" + endDate
				+ ", openTimeShow=" + openTimeShow + ", endTimeShow=" + endTimeShow + ", openDateShow=" + openDateShow
				+ ", endDateShow=" + endDateShow + ", srvs=" + Arrays.toString(srvs) + ", timeCardId=" + timeCardId
				+ ", extr=" + extr + "]";
	}
	
}
