package cn.saturn.web.center.dao;

import java.util.Date;

public class DaydateModel {
	
	private Date ttime;//时间
	private int devicereg; //设备新增
	private int newRegisterUser;//新增注册
	private int absNewUser;//新增注册 
	private double dau;//DAU
	private double acu;//ACU
	private double pcu;//PCU
	private double payUser1;//付费玩家数
	private double paySum;//付费金额
	private double arpu;//新增付费ARPU
	private double arppu1;//活跃ARPPU
	private double dauarp;//DAUARPU
	private double payRate;//付费率
	private double newPay;//新付费总额
	private int newPPayCount;//新付费玩家数
	private double rrppu;//注册ARPU
	private double rrate;//新增付费率%
	private double oldPaySum;//老用户付费金额
	private int oldPayCount;//老付费玩家数
	private double retained1;
	private double retained3;
	private double retained7;
	private double retained14;
	private double retained30;

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
	public double getDau() {
		return dau;
	}
	public void setDau(double dau) {
		this.dau = dau;
	}
	public double getAcu() {
		return acu;
	}
	public void setAcu(double acu) {
		this.acu = acu;
	}
	public double getPcu() {
		return pcu;
	}
	public void setPcu(double pcu) {
		this.pcu = pcu;
	}
	public double getPayUser1() {
		return payUser1;
	}
	public void setPayUser1(double payUser1) {
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
	
	public double getDauarp() {
		return dauarp;
	}
	public void setDauarp(double dauarp) {
		this.dauarp = dauarp;
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
	public double getRrppu() {
		return rrppu;
	}
	public void setRrppu(double rrppu) {
		this.rrppu = rrppu;
	}
	public double getRrate() {
		return rrate;
	}
	public void setRrate(double rrate) {
		this.rrate = rrate;
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
	public double getRetained3() {
		return retained3;
	}
	public void setRetained3(double retained3) {
		this.retained3 = retained3;
	}
	public double getRetained7() {
		return retained7;
	}
	public void setRetained7(double retained7) {
		this.retained7 = retained7;
	}
	public double getRetained14() {
		return retained14;
	}
	public void setRetained14(double retained14) {
		this.retained14 = retained14;
	}
	public double getRetained30() {
		return retained30;
	}
	public void setRetained30(double retained30) {
		this.retained30 = retained30;
	}
	public DaydateModel() {
		super();
	}
	
	@Override
	public String toString() {
		return "&ttime=" + ttime + "&devicereg=" + devicereg
				+ "&newRegisterUser=" + newRegisterUser + "&absNewUser=" + absNewUser + "&dau=" + dau + "&acu="
				+ acu + "&pcu=" + pcu + "&payUser1=" + payUser1 + "&paySum=" + paySum + "&arpu=" + arpu
				+ "&arppu1=" + arppu1 + "&dauarp=" + dauarp + "&payRate=" + payRate + "&newPay=" + newPay
				+ "&newPPayCount=" + newPPayCount + "&rrppu=" + rrppu + "&rrate=" + rrate + "&oldPaySum="
				+ oldPaySum + "&oldPayCount=" + oldPayCount + "&retained1=" + retained1 + "&retained3=" + retained3
				+ "&retained7=" + retained7 + "&retained14=" + retained14 + "&retained30=" + retained30;
	}
	
	
	
	
	
	
}
