package cn.saturn.web.center.dao;

import java.util.Date;

public class DaylineModel {
	private int id;
	private String gamever;//土星游戏平台唯一标识标识后台信息
	private Date ttime;//时间
	private int devicereg;//设备新增
	private int newRegisterUser;//新增注册
	private int absNewUser;//绝对新增
	private int dau;//DAU
	private int payUser1;//付费玩家数
	private double paySum;//付费金额
	private double arpu;//ARPU
	private double arppu1;//arppu
	private double dauarpu;//DAUARPU
	private double payRate;//付费率
	private double newPay;//新付费总额
	private int newPPayCount;//新付费玩家数
	private double oldPaySum;//老用户付费金额
	private int oldPayCount;//老付费玩家数
	private double retained1;//次日留存
	private Date sendtime;
	private Date rectime;//接受时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGamever() {
		return gamever;
	}
	public void setGamever(String gamever) {
		this.gamever = gamever;
	}
	public Date getTtime() {
		return ttime;
	}
	public void setTtime(Date ttime) {
		this.ttime = ttime;
	}
	public int getDevicereg() {
		return devicereg;
	}
	public void setDevicereg(int devicereg) {
		this.devicereg = devicereg;
	}
	public int getNewRegisterUser() {
		return newRegisterUser;
	}
	public void setNewRegisterUser(int newRegisterUser) {
		this.newRegisterUser = newRegisterUser;
	}
	public int getAbsNewUser() {
		return absNewUser;
	}
	public void setAbsNewUser(int absNewUser) {
		this.absNewUser = absNewUser;
	}
	public int getDau() {
		return dau;
	}
	public void setDau(int dau) {
		this.dau = dau;
	}
	public int getPayUser1() {
		return payUser1;
	}
	public void setPayUser1(int payUser1) {
		this.payUser1 = payUser1;
	}
	public double getPaySum() {
		return paySum;
	}
	public void setPaySum(double paySum) {
		this.paySum = paySum;
	}
	public double getArpu() {
		return arpu;
	}
	public void setArpu(double arpu) {
		this.arpu = arpu;
	}
	public double getArppu1() {
		return arppu1;
	}
	public void setArppu1(double arppu1) {
		this.arppu1 = arppu1;
	}
	public double getDauarpu() {
		return dauarpu;
	}
	public void setDauarpu(double dauarpu) {
		this.dauarpu = dauarpu;
	}
	public double getPayRate() {
		return payRate;
	}
	public void setPayRate(double payRate) {
		this.payRate = payRate;
	}
	public double getNewPay() {
		return newPay;
	}
	public void setNewPay(double newPay) {
		this.newPay = newPay;
	}
	public int getNewPPayCount() {
		return newPPayCount;
	}
	public void setNewPPayCount(int newPPayCount) {
		this.newPPayCount = newPPayCount;
	}
	public double getOldPaySum() {
		return oldPaySum;
	}
	public void setOldPaySum(double oldPaySum) {
		this.oldPaySum = oldPaySum;
	}
	public int getOldPayCount() {
		return oldPayCount;
	}
	public void setOldPayCount(int oldPayCount) {
		this.oldPayCount = oldPayCount;
	}
	public double getRetained1() {
		return retained1;
	}
	public void setRetained1(double retained1) {
		this.retained1 = retained1;
	}
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	public Date getRectime() {
		return rectime;
	}
	public void setRectime(Date rectime) {
		this.rectime = rectime;
	}
	public DaylineModel() {
		super();
	}
	public DaylineModel(int id, String gamever, Date ttime, int devicereg, int newRegisterUser, int absNewUser,
			int dau, int payUser1, double paySum, double arpu, double arppu1, double dauarpu, double payRate,
			double newPay, int newPPayCount, double oldPaySum, int oldPayCount, double retained1, Date sendtime,
			Date rectime) {
		super();
		this.id = id;
		this.gamever = gamever;
		this.ttime = ttime;
		this.devicereg = devicereg;
		this.newRegisterUser = newRegisterUser;
		this.absNewUser = absNewUser;
		this.dau = dau;
		this.payUser1 = payUser1;
		this.paySum = paySum;
		this.arpu = arpu;
		this.arppu1 = arppu1;
		this.dauarpu = dauarpu;
		this.payRate = payRate;
		this.newPay = newPay;
		this.newPPayCount = newPPayCount;
		this.oldPaySum = oldPaySum;
		this.oldPayCount = oldPayCount;
		this.retained1 = retained1;
		this.sendtime = sendtime;
		this.rectime = rectime;
	}
	@Override
	public String toString() {
		return "DaydatelineModel [id=" + id + ", gamever=" + gamever + ", ttime=" + ttime + ", devicereg=" + devicereg
				+ ", newRegisterUser=" + newRegisterUser + ", absNewUser=" + absNewUser + ", dau=" + dau + ", payUser1="
				+ payUser1 + ", paySum=" + paySum + ", arpu=" + arpu + ", arppu1=" + arppu1 + ", dauarpu=" + dauarpu
				+ ", payRate=" + payRate + ", newPay=" + newPay + ", newPPayCount=" + newPPayCount + ", oldPaySum="
				+ oldPaySum + ", oldPayCount=" + oldPayCount + ", retained1=" + retained1 + ", sendtime=" + sendtime
				+ ", rectime=" + rectime + "]";
	}
	
	
	
}
