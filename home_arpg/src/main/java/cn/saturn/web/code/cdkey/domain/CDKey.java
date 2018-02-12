package cn.saturn.web.code.cdkey.domain;

public class CDKey {
	protected String key;
	protected String award;
	protected boolean global;
	protected boolean enable;
	protected int serverid;
	protected int playerid;
	protected String overTime;
	protected String platformLimit;
	protected int type;
	protected int useCount;//使用次数
	protected int useLimit;//使用上限
	protected int usedNum;//已经使用次数

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getServerid() {
		return serverid;
	}

	public void setServerid(int serverid) {
		this.serverid = serverid;
	}

	public int getPlayerid() {
		return playerid;
	}

	public void setPlayerid(int playerid) {
		this.playerid = playerid;
	}

	public String getOverTime() {
		return overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public String getPlatformLimit() {
		return platformLimit;
	}

	public void setPlatformLimit(String platformLimit) {
		this.platformLimit = platformLimit;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}

	public int getUseLimit() {
		return useLimit;
	}

	public void setUseLimit(int useLimit) {
		this.useLimit = useLimit;
	}

	public int getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(int usedNum) {
		this.usedNum = usedNum;
	}

	@Override
	public String toString() {
		return "CDKey [key=" + key + ", award=" + award + ", global=" + global + ", enable=" + enable + ", serverid="
				+ serverid + ", playerid=" + playerid + ", overTime=" + overTime + ", platformLimit=" + platformLimit
				+ ", type=" + type + ", useCount=" + useCount + ", useLimit=" + useLimit + ", usedNum=" + usedNum + "]";
	}

}
