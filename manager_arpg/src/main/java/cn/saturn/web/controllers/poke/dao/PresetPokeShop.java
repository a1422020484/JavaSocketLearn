package cn.saturn.web.controllers.poke.dao;

public class PresetPokeShop {
	
	protected int id; // id
	protected int open_state; // 时间类型 0 普通时间 1开服时间
	protected int openDate; // 开服天数
	protected int endDate;
	protected String openTime; // 开启时间
	protected String endTime; // 结束时间
	protected int srvs[]; // 需要发送的服务器
	protected int firstId; // 热卖物品1ID
	protected int secondId; // 热卖物品2
	protected int thirdId; // 热卖物品3
	protected int first_count;// 热卖物品1数量
	protected int first_limit;// 热卖物品1限购次数
	protected int first_price;// 热卖物品1售卖积分
	protected int second_count;// 
	protected int second_limit;// 
	protected int second_price;// 
	protected int third_count;// 
	protected int third_limit;// 
	protected int third_price;// 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public int getEndDate() {
		return endDate;
	}
	public void setEndDate(int endDate) {
		this.endDate = endDate;
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
	public int[] getSrvs() {
		return srvs;
	}
	public void setSrvs(int[] srvs) {
		this.srvs = srvs;
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
	public int getFirst_count() {
		return first_count;
	}
	public void setFirst_count(int first_count) {
		this.first_count = first_count;
	}
	public int getFirst_limit() {
		return first_limit;
	}
	public void setFirst_limit(int first_limit) {
		this.first_limit = first_limit;
	}
	public int getFirst_price() {
		return first_price;
	}
	public void setFirst_price(int first_price) {
		this.first_price = first_price;
	}
	public int getSecond_count() {
		return second_count;
	}
	public void setSecond_count(int second_count) {
		this.second_count = second_count;
	}
	public int getSecond_limit() {
		return second_limit;
	}
	public void setSecond_limit(int second_limit) {
		this.second_limit = second_limit;
	}
	public int getSecond_price() {
		return second_price;
	}
	public void setSecond_price(int second_price) {
		this.second_price = second_price;
	}
	public int getThird_count() {
		return third_count;
	}
	public void setThird_count(int third_count) {
		this.third_count = third_count;
	}
	public int getThird_limit() {
		return third_limit;
	}
	public void setThird_limit(int third_limit) {
		this.third_limit = third_limit;
	}
	public int getThird_price() {
		return third_price;
	}
	public void setThird_price(int third_price) {
		this.third_price = third_price;
	}
	
	
}
