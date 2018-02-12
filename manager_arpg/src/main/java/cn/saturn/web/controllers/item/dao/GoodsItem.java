package cn.saturn.web.controllers.item.dao;

public class GoodsItem {
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public GoodsItem() {
		super();
	}
	public GoodsItem(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}
