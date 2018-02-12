package cn.saturn.web.controllers.power.dao;

import java.util.Date;

import cn.saturn.operation.TimeUtils;

public class ChannelPayModel {
	protected int id;
	protected String platform;
	protected String sub_platform;
	protected Date  creattime;
	protected String remark;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getSub_platform() {
		return sub_platform;
	}
	public void setSub_platform(String sub_platform) {
		this.sub_platform = sub_platform;
	}
	public Date getCreattime() {
		return creattime;
	}
	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public ChannelPayModel() {
		super();
	}
	
	public ChannelPayModel(int id, String platform, String sub_platform, Date creattime, String remark) {
		this.id = id;
		this.platform = platform;
		this.sub_platform = sub_platform;
		this.creattime = creattime;
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "ChannelPayModel [id=" + id + ", platform=" + platform + ", sub_platform=" + sub_platform
				+ ", creattime=" + creattime + ", remark=" + remark + "]";
	}
	

}
