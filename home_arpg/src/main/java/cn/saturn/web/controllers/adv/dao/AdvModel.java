package cn.saturn.web.controllers.adv.dao;

import java.util.Date;

public class AdvModel {
	
	private long id;
	private String games;
	private String adPlatform;
	private String subPlatform;
	private String version;
	private String device;  //设备标识符 imel idfa
	private Date ts;
	private String callback_url;
	private String callback_param;
	private String aid;
	private String cid;
	private String mac_sum;
	private String mac_sum1;
	private String androidid_sum;
	private String ua;
	private String os;
	private String lbs;
	private String ip;
	private Date createtime;
	private int advType; //0默认没返回到广告厂商服务器，1已经发送返回
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGames() {
		return games;
	}
	public void setGames(String games) {
		this.games = games;
	}
	public String getAdPlatform() {
		return adPlatform;
	}
	public void setAdPlatform(String adPlatform) {
		this.adPlatform = adPlatform;
	}
	public String getSubPlatform() {
		return subPlatform;
	}
	public void setSubPlatform(String subPlatform) {
		this.subPlatform = subPlatform;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public String getCallback_url() {
		return callback_url;
	}
	public void setCallback_url(String callback_url) {
		this.callback_url = callback_url;
	}
	public String getCallback_param() {
		return callback_param;
	}
	public void setCallback_param(String callback_param) {
		this.callback_param = callback_param;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getMac_sum() {
		return mac_sum;
	}
	public void setMac_sum(String mac_sum) {
		this.mac_sum = mac_sum;
	}
	public String getMac_sum1() {
		return mac_sum1;
	}
	public void setMac_sum1(String mac_sum1) {
		this.mac_sum1 = mac_sum1;
	}
	public String getAndroidid_sum() {
		return androidid_sum;
	}
	public void setAndroidid_sum(String androidid_sum) {
		this.androidid_sum = androidid_sum;
	}
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		this.ua = ua;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getLbs() {
		return lbs;
	}
	public void setLbs(String lbs) {
		this.lbs = lbs;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public int getAdvType() {
		return advType;
	}
	public void setAdvType(int advType) {
		this.advType = advType;
	}
	
	public AdvModel() {
		super();
	}
	public AdvModel(long id, String games, String adPlatform, String subPlatform, String version, String device,
			Date ts, String callback_url, String callback_param, String aid, String cid, String mac_sum,
			String mac_sum1, String androidid_sum, String ua, String os, String lbs, String ip, Date createtime,
			int advType) {
		super();
		this.id = id;
		this.games = games;
		this.adPlatform = adPlatform;
		this.subPlatform = subPlatform;
		this.version = version;
		this.device = device;
		this.ts = ts;
		this.callback_url = callback_url;
		this.callback_param = callback_param;
		this.aid = aid;
		this.cid = cid;
		this.mac_sum = mac_sum;
		this.mac_sum1 = mac_sum1;
		this.androidid_sum = androidid_sum;
		this.ua = ua;
		this.os = os;
		this.lbs = lbs;
		this.ip = ip;
		this.createtime = createtime;
		this.advType = advType;
	}
	@Override
	public String toString() {
		return "AdvModel [id=" + id + ", games=" + games + ", adPlatform=" + adPlatform + ", subPlatform=" + subPlatform
				+ ", version=" + version + ", device=" + device + ", ts=" + ts + ", callback_url=" + callback_url
				+ ", callback_param=" + callback_param + ", aid=" + aid + ", cid=" + cid + ", mac_sum=" + mac_sum
				+ ", mac_sum1=" + mac_sum1 + ", androidid_sum=" + androidid_sum + ", ua=" + ua + ", os=" + os + ", lbs="
				+ lbs + ", ip=" + ip + ", createtime=" + createtime + ", advType=" + advType + "]";
	}
	
}
