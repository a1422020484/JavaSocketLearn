package cn.saturn.web.controllers.poke.dao;

public class PresetExchange {

	protected int id; // 热点精灵id
	protected String openTime; // 开启时间
	protected int firstId; // 闪光精灵1
	protected int secondId; // 闪光精灵2
	protected int thirdId; // 闪光精灵3
	protected int open_state; // 时间类型 0 普通时间 1开服时间
	protected int openDate; // 开服天数
	protected int srvs[]; // 需要发送的服务器

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public int getFirstId() {
		return firstId;
	}

	public void setFirstId(int firstId) {
		this.firstId = firstId;
	}

	public int getSecondId() {
		return secondId;
	}

	public void setSecondId(int secondId) {
		this.secondId = secondId;
	}

	public int getThirdId() {
		return thirdId;
	}

	public void setThirdId(int thirdId) {
		this.thirdId = thirdId;
	}

	public int getOpen_state() {
		return open_state;
	}

	public void setOpen_state(int open_state) {
		this.open_state = open_state;
	}

	public int getOpenDate() {
		return openDate;
	}

	public void setOpenDate(int openDate) {
		this.openDate = openDate;
	}

	public int[] getSrvs() {
		return srvs;
	}

	public void setSrvs(int[] srvs) {
		this.srvs = srvs;
	}
}
