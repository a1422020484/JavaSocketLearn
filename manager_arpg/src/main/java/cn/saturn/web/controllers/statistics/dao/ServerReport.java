package cn.saturn.web.controllers.statistics.dao;

/**
 * 分服统计接收单服统计，和服的结果
 * @author 
 *
 */

public class ServerReport {
	private int id;
	private String servername;
	private int newRegisterUser;
	private int absNewUser;
	private int dau;
	private int acu;
	private int pcu;
	private int payUser1;
	private double paySum;
	private double newPay;
	private double newPPayCount;
	private double retained1;
	private double	retained3;
	private double	retained7;
	private double	retained14;
	private double	retained30;
	private int notInGame;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
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
	public int getAcu() {
		return acu;
	}
	public void setAcu(int acu) {
		this.acu = acu;
	}
	public int getPcu() {
		return pcu;
	}
	public void setPcu(int pcu) {
		this.pcu = pcu;
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
	public double getNewPay() {
		return newPay;
	}
	public void setNewPay(double newPay) {
		this.newPay = newPay;
	}
	public double getNewPPayCount() {
		return newPPayCount;
	}
	public void setNewPPayCount(double newPPayCount) {
		this.newPPayCount = newPPayCount;
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
	public int getNotInGame() {
		return notInGame;
	}
	public void setNotInGame(int notInGame) {
		this.notInGame = notInGame;
	}
	
	public ServerReport() {
		super();
	}
	public ServerReport(int id, String servername, int newRegisterUser, int absNewUser, int dau, int acu, int pcu,
			int payUser1, double paySum, double newPay, double newPPayCount, double retained1, double retained3,
			double retained7, double retained14, double retained30, int notInGame) {
		super();
		this.id = id;
		this.servername = servername;
		this.newRegisterUser = newRegisterUser;
		this.absNewUser = absNewUser;
		this.dau = dau;
		this.acu = acu;
		this.pcu = pcu;
		this.payUser1 = payUser1;
		this.paySum = paySum;
		this.newPay = newPay;
		this.newPPayCount = newPPayCount;
		this.retained1 = retained1;
		this.retained3 = retained3;
		this.retained7 = retained7;
		this.retained14 = retained14;
		this.retained30 = retained30;
		this.notInGame = notInGame;
	}
	@Override
	public String toString() {
		return "ServerReport [id=" + id + ", servername=" + servername + ", newRegisterUser=" + newRegisterUser
				+ ", absNewUser=" + absNewUser + ", dau=" + dau + ", acu=" + acu + ", pcu=" + pcu + ", payUser1="
				+ payUser1 + ", paySum=" + paySum + ", newPay=" + newPay + ", newPPayCount=" + newPPayCount
				+ ", retained1=" + retained1 + ", retained3=" + retained3 + ", retained7=" + retained7 + ", retained14="
				+ retained14 + ", retained30=" + retained30 + ", notInGame=" + notInGame + "]";
	}
	
	
	

	
}
