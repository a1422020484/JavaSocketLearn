package cn.saturn.web.controllers.poke.dao;

public class PresetPokeHunter {

	protected int id; // 热点精灵id
	protected String openTime; // 开启时间
	protected int firstId; // 初级热点精灵
	protected int secondId; // 中级热点精灵
	protected int thirdId; // 高级热点精灵
	protected int first_min_bd;// 初级热点精灵最小保底数
	protected int first_max_bd;// 初级热点精灵最大保底数
	protected int second_min_bd;// 中级热点精灵最小保底数
	protected int second_max_bd;// 中级热点精灵最大保底数
	protected int third_min_bd; // 高级热点精灵最小保底数
	protected int third_max_bd; // 高级热点精灵最大保底数
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

	public int getFirst_min_bd() {
		return first_min_bd;
	}

	public void setFirst_min_bd(int first_min_bd) {
		this.first_min_bd = first_min_bd;
	}

	public int getFirst_max_bd() {
		return first_max_bd;
	}

	public void setFirst_max_bd(int first_max_bd) {
		this.first_max_bd = first_max_bd;
	}

	public int getSecond_min_bd() {
		return second_min_bd;
	}

	public void setSecond_min_bd(int second_min_bd) {
		this.second_min_bd = second_min_bd;
	}

	public int getSecond_max_bd() {
		return second_max_bd;
	}

	public void setSecond_max_bd(int second_max_bd) {
		this.second_max_bd = second_max_bd;
	}

	public int getThird_min_bd() {
		return third_min_bd;
	}

	public void setThird_min_bd(int third_min_bd) {
		this.third_min_bd = third_min_bd;
	}

	public int getThird_max_bd() {
		return third_max_bd;
	}

	public void setThird_max_bd(int third_max_bd) {
		this.third_max_bd = third_max_bd;
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
