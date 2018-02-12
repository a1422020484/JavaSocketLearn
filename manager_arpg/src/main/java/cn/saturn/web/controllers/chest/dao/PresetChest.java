package cn.saturn.web.controllers.chest.dao;

public class PresetChest {
	protected int id; // 活动ID 
	protected String chestName; // 名字
	protected String chestDesc; // 描述
	protected int icon; // 图标
	protected int quility; // 品阶
	protected int price;// 宝箱价格
	protected int max_count; // 最大数量
	protected int type; // 类型
	protected int item_num; // 物品数量
	protected int[] srvIds; // 激活的服务器列表
	
	protected int[] items; 			// 物品ids
	protected int[] weights; 		// 权重列表
	protected int[] nums;			// 数量
	protected int[] randomListIds;	// 随机物品列表
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getQuility() {
		return quility;
	}

	public void setQuility(int quility) {
		this.quility = quility;
	}

	public int getMax_count() {
		return max_count;
	}

	public void setMax_count(int max_count) {
		this.max_count = max_count;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getItem_num() {
		return item_num;
	}

	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}

	public int[] getSrvIds() {
		return srvIds;
	}

	public void setSrvIds(int[] srvIds) {
		this.srvIds = srvIds;
	}

	public String getChestName() {
		return chestName;
	}

	public void setChestName(String chestName) {
		this.chestName = chestName;
	}

	public String getChestDesc() {
		return chestDesc;
	}

	public void setChestDesc(String chestDesc) {
		this.chestDesc = chestDesc;
	}

	public int[] getItems() {
		return items;
	}

	public void setItems(int[] items) {
		this.items = items;
	}

	public int[] getWeights() {
		return weights;
	}

	public void setWeights(int[] weights) {
		this.weights = weights;
	}

	public int[] getNums() {
		return nums;
	}

	public void setNums(int[] nums) {
		this.nums = nums;
	}

	public int[] getRandomListIds() {
		return randomListIds;
	}

	public void setRandomListIds(int[] randomListIds) {
		this.randomListIds = randomListIds;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
