package cn.saturn.web.controllers.mail.dao;

/**
 * 虚拟支付配置表
 * @author Administrator
 *
 */
public class VpaycfgModel {
	private int goodId;
	
	private float price;
	
	private String name;
	
	private String discription;

	public int getGoodId() {
		return goodId;
	}

	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	@Override
	public String toString() {
		return "VpaycfgModel [goodId=" + goodId + ", price=" + price + ", name=" + name + ", discription=" + discription
				+ "]";
	}

}
