package cn.saturn.web.controllers.yybapi.dao;

public class OpRecord {
	private long timestamp;
	
	private String appid;
	
	private String area;
	
	private String openid;
	
	private String partition;
	
	private String billno;
	
	private String roleid;
	
	private String midas_billno;
	
	private String money;
	
	private int gold;

	
	public OpRecord() {
		super();
	}

	public OpRecord(long timestamp, String appid, String area, String openid, String partition, String billno,
			String roleid, String midas_billno, String money, int gold) {
		super();
		this.timestamp = timestamp;
		this.appid = appid;
		this.area = area;
		this.openid = openid;
		this.partition = partition;
		this.billno = billno;
		this.roleid = roleid;
		this.midas_billno = midas_billno;
		this.money = money;
		this.gold = gold;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPartition() {
		return partition;
	}

	public void setPartition(String partition) {
		this.partition = partition;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getMidas_billno() {
		return midas_billno;
	}

	public void setMidas_billno(String midas_billno) {
		this.midas_billno = midas_billno;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	@Override
	public String toString() {
		return "OpRecord [timestamp=" + timestamp + ", appid=" + appid + ", area=" + area + ", openid=" + openid
				+ ", partition=" + partition + ", billno=" + billno + ", roleid=" + roleid + ", midas_billno="
				+ midas_billno + ", money=" + money + ", gold=" + gold + "]";
	}
	
}
