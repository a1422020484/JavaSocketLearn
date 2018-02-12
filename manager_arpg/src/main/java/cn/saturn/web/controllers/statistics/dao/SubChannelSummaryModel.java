package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;

public class SubChannelSummaryModel {
	private String channelId;
	
	private String subChannelId;
	
	private String channelName;
	
	private String subChannelName;
	
	private int type;
	
	private int RU;
	
	private int AU;
	
	private int PU;
	
	private float amount;
	
	private float ARPU;
	
	private float ARPPU;
	
	private Date statDate;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getSubChannelId() {
		return subChannelId;
	}

	public void setSubChannelId(String subChannelId) {
		this.subChannelId = subChannelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getSubChannelName() {
		return subChannelName;
	}

	public void setSubChannelName(String subChannelName) {
		this.subChannelName = subChannelName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRU() {
		return RU;
	}

	public void setRU(int rU) {
		RU = rU;
	}

	public int getAU() {
		return AU;
	}

	public void setAU(int aU) {
		AU = aU;
	}

	public int getPU() {
		return PU;
	}

	public void setPU(int pU) {
		PU = pU;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getARPU() {
		return ARPU;
	}

	public void setARPU(float aRPU) {
		ARPU = aRPU;
	}

	public float getARPPU() {
		return ARPPU;
	}

	public void setARPPU(float aRPPU) {
		ARPPU = aRPPU;
	}

	public Date getStatDate() {
		return statDate;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}
	
}
