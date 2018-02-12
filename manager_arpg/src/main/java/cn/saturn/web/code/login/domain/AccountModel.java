package cn.saturn.web.code.login.domain;

import java.util.Date;

public class AccountModel {
	private long id;
    private String account; // 账号
    private String password; // 密码
    private String platform; // 平台
    private String platformExt; // 平台扩展数据
    private int prevSrvId; // 上次登陆的服务器ID
    private int loginKey; // 登陆key
    private Date createTime;
    private boolean vindicator;
    protected String systemInfo;
    private Date loginTime;
    protected String lastip; // 最后一次登录使用的ip
    protected String version;
    private boolean accountActived;
    private String subPlatform; // 子版本
    private String thirdUserId; // 第三方渠道用户id,例,易接的子渠道
    private String cdkTypes; // cdk 类型保存
    private int special; //特权信息 0无特权,1有特权;
    private String specialcode; //特权码信息;
    public String games; //推广广告的游戏标识
    public String adPlatform;//推广广告的推广平台标识
    public String adSubPlatform;//推广广告的推广子平台标识
    public String adVersion;//推广广告的广告版本
    public String idfa;//IDFA
    public int allowChangePwd;//是否允许修改密码;默认0不准修改
    
    public AccountModel() {
        this("", "");
    }

    public AccountModel(String account, String password) {
        this.account = account;
        this.password = (password != null) ? password : "";
        createTime = new Date(System.currentTimeMillis());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatformExt() {
        return platformExt;
    }

    public void setPlatformExt(String platformExt) {
        this.platformExt = platformExt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getPrevSrvId() {
        return prevSrvId;
    }

    public void setPrevSrvId(int prevSrvId) {
        this.prevSrvId = prevSrvId;
    }

    public int getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(int loginKey) {
        this.loginKey = loginKey;
    }

    public boolean isVindicator() {
        return vindicator;
    }

    public void setVindicator(boolean vindicator) {
        this.vindicator = vindicator;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(String systemInfo) {
        this.systemInfo = systemInfo;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLastip() {
        return lastip;
    }

    public void setLastip(String lastip) {
        this.lastip = lastip;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isAccountActived() {
        return accountActived;
    }

    public void setAccountActived(boolean accountActived) {
        this.accountActived = accountActived;
    }

    public String getSubPlatform() {
        return subPlatform;
    }

    public void setSubPlatform(String subPlatform) {
        this.subPlatform = subPlatform;
    }

    public String getThirdUserId() {
        return thirdUserId;
    }

    public void setThirdUserId(String thirdUserId) {
        this.thirdUserId = thirdUserId;
    }

	public String getCdkTypes() {
		return cdkTypes;
	}

	public void setCdkTypes(String cdkTypes) {
		this.cdkTypes = cdkTypes;
	}

	public int getSpecial() {
		return special;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public String getSpecialcode() {
		return specialcode;
	}

	public void setSpecialcode(String specialcode) {
		this.specialcode = specialcode;
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

	public int getAllowChangePwd() {
		return allowChangePwd;
	}

	public void setAllowChangePwd(int allowChangePwd) {
		this.allowChangePwd = allowChangePwd;
	}

	public AccountModel(long id, String account, String password, String platform, String platformExt, int prevSrvId,
			int loginKey, Date createTime, boolean vindicator, String systemInfo, Date loginTime, String lastip,
			String version, boolean accountActived, String subPlatform, String thirdUserId, String cdkTypes,
			int special, String specialcode, String games, String adPlatform, String adSubPlatform, String adVersion,
			String idfa, int allowChangePwd) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.platform = platform;
		this.platformExt = platformExt;
		this.prevSrvId = prevSrvId;
		this.loginKey = loginKey;
		this.createTime = createTime;
		this.vindicator = vindicator;
		this.systemInfo = systemInfo;
		this.loginTime = loginTime;
		this.lastip = lastip;
		this.version = version;
		this.accountActived = accountActived;
		this.subPlatform = subPlatform;
		this.thirdUserId = thirdUserId;
		this.cdkTypes = cdkTypes;
		this.special = special;
		this.specialcode = specialcode;
		this.games = games;
		this.adPlatform = adPlatform;
		this.adSubPlatform = adSubPlatform;
		this.adVersion = adVersion;
		this.idfa = idfa;
		this.allowChangePwd = allowChangePwd;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", account=" + account + ", password=" + password + ", platform=" + platform
				+ ", platformExt=" + platformExt + ", prevSrvId=" + prevSrvId + ", loginKey=" + loginKey
				+ ", createTime=" + createTime + ", vindicator=" + vindicator + ", systemInfo=" + systemInfo
				+ ", loginTime=" + loginTime + ", lastip=" + lastip + ", version=" + version + ", accountActived="
				+ accountActived + ", subPlatform=" + subPlatform + ", thirdUserId=" + thirdUserId + ", cdkTypes="
				+ cdkTypes + ", special=" + special + ", specialcode=" + specialcode + ", games=" + games
				+ ", adPlatform=" + adPlatform + ", adSubPlatform=" + adSubPlatform + ", adVersion=" + adVersion
				+ ", idfa=" + idfa + ", allowChangePwd=" + allowChangePwd + "]";
	}
	
}
