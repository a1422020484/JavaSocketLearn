package cn.saturn.web.controllers.cdk.dao;

public class PresetUCCDK {
	protected int id; // cdk id
	protected long num; // 礼包数量
	protected String name; // 礼包名称
	protected int createTime; // 创建时间
	protected String[] awards; // 奖励
	protected int type; // cdk 类型
	protected String gift_id;

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

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
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

	public String getGift_id() {
		return gift_id;
	}

	public void setGift_id(String gift_id) {
		this.gift_id = gift_id;
	}
}
