package cn.saturn.web.controllers.device.dao;

public class DeviceModel {
    private String deviceUI;
    private String OS;
    private String deviceModel;
    private String systemInfo;
    public String reg_time;
    public String platform;
    public String subPlatform;
    public String games;
    public String adPlatform;
    public String adSubPlatform;
    public String adVersion;
    public String idfa;
    public int advType;//广告返回成功类型

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

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

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

	public String getSubPlatform() {
		return subPlatform;
	}

	public void setSubPlatform(String subPlatform) {
		this.subPlatform = subPlatform;
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

	public String getAdSubPlatform() {
		return adSubPlatform;
	}

	public void setAdSubPlatform(String adSubPlatform) {
		this.adSubPlatform = adSubPlatform;
	}

	public String getAdVersion() {
		return adVersion;
	}

	public void setAdVersion(String adVersion) {
		this.adVersion = adVersion;
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}
	
	public int getAdvType() {
		return advType;
	}

	public void setAdvType(int advType) {
		this.advType = advType;
	}

	public DeviceModel() {
		super();
	}

	public DeviceModel(String deviceUI, String oS, String deviceModel, String systemInfo, String reg_time,
			String platform, String subPlatform, String games, String adPlatform, String adSubPlatform,
			String adVersion, String idfa, int advType) {
		super();
		this.deviceUI = deviceUI;
		OS = oS;
		this.deviceModel = deviceModel;
		this.systemInfo = systemInfo;
		this.reg_time = reg_time;
		this.platform = platform;
		this.subPlatform = subPlatform;
		this.games = games;
		this.adPlatform = adPlatform;
		this.adSubPlatform = adSubPlatform;
		this.adVersion = adVersion;
		this.idfa = idfa;
		this.advType = advType;
	}

	@Override
	public String toString() {
		return "DeviceModel [deviceUI=" + deviceUI + ", OS=" + OS + ", deviceModel=" + deviceModel + ", systemInfo="
				+ systemInfo + ", reg_time=" + reg_time + ", platform=" + platform + ", subPlatform=" + subPlatform
				+ ", games=" + games + ", adPlatform=" + adPlatform + ", adSubPlatform=" + adSubPlatform
				+ ", adVersion=" + adVersion + ", idfa=" + idfa + ", advType=" + advType + "]";
	}

	
}
