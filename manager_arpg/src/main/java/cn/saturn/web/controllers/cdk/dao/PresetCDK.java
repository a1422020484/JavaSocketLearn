package cn.saturn.web.controllers.cdk.dao;

public class PresetCDK {
	protected int id; // cdk id
	protected long num; // 礼包数量
	protected String name; // 礼包名称
	protected String fontStr; // 礼包前缀
	protected int createTime; // 创建时间
	protected int overTime; // 过期时间
	protected String platfromLimit; // 渠道过滤
	protected String[] awards; //奖励
	protected int type; // cdk 类型
	protected int useCount;//使用次数
	protected int useLimit;//使用上限

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFontStr() {
		return fontStr;
	}

	public void setFontStr(String fontStr) {
		this.fontStr = fontStr;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	public int getOverTime() {
		return overTime;
	}

	public void setOverTime(int overTime) {
		this.overTime = overTime;
	}

	public String getPlatfromLimit() {
		return platfromLimit;
	}

	public void setPlatfromLimit(String platfromLimit) {
		this.platfromLimit = platfromLimit;
	}

	public String[] getAwards() {
		return awards;
	}

	public void setAwards(String[] awards) {
		this.awards = awards;
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

	public String toFileName() {
		return fontStr+"_"+ name +"_"+ createTime + ".csv";
	}
}
