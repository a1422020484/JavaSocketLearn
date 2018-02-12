package cn.saturn.web.controllers.device.dao;

import java.util.Date;

public class AccountDeviceModel {
	
	private String deviceUI;
    private String OS;
    private String deviceModel;
    private String systemInfo;
    private Date reg_time;
    private String platform;
    private String subplatform;
	public String getDeviceUI() {
		return deviceUI;
	}
	public void setDeviceUI(String deviceUI) {
		this.deviceUI = deviceUI;
	}
	public String getOS() {
		return OS;
	}
	public void setOS(String oS) {
		OS = oS;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getSystemInfo() {
		return systemInfo;
	}
	public void setSystemInfo(String systemInfo) {
		this.systemInfo = systemInfo;
	}
	
	public Date getReg_time() {
		return reg_time;
	}
	public void setReg_time(Date reg_time) {
		this.reg_time = reg_time;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getSubplatform() {
		return subplatform;
	}
	public void setSubplatform(String subplatform) {
		this.subplatform = subplatform;
	}
	public AccountDeviceModel() {
		super();
	}
	public AccountDeviceModel(String deviceUI, String oS, String deviceModel, String systemInfo, Date reg_time,
			String platform, String subplatform) {
		super();
		this.deviceUI = deviceUI;
		OS = oS;
		this.deviceModel = deviceModel;
		this.systemInfo = systemInfo;
		this.reg_time = reg_time;
		this.platform = platform;
		this.subplatform = subplatform;
	}
	@Override
	public String toString() {
		return "AccountDeviceModel [deviceUI=" + deviceUI + ", OS=" + OS + ", deviceModel=" + deviceModel
				+ ", systemInfo=" + systemInfo + ", reg_time=" + reg_time + ", platform=" + platform + ", subplatform="
				+ subplatform + "]";
	}
	
	
    
}
