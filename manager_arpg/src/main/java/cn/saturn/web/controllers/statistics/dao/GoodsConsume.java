package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;

public class GoodsConsume {
	private String  id;
	private int goodsid;
	private int  quantity;
	private Date  createtime;
	private int serverid;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(int goodsid) {
		this.goodsid = goodsid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public int getServerid() {
		return serverid;
	}
	public void setServerid(int serverid) {
		this.serverid = serverid;
	}
	
	public GoodsConsume() {
		super();
	}
	public GoodsConsume(String id, int goodsid, int quantity, Date createtime, int serverid) {
		super();
		this.id = id;
		this.goodsid = goodsid;
		this.quantity = quantity;
		this.createtime = createtime;
		this.serverid = serverid;
	}
	@Override
	public String toString() {
		return "GoodsConsume [id=" + id + ", goodsid=" + goodsid + ", quantity=" + quantity + ", createtime="
				+ createtime + ", serverid=" + serverid + "]";
	}

}
