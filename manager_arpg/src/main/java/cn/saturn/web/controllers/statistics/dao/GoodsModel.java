package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;

public class GoodsModel {
	
	private int id;
	private int playerid;
	private int goodsid;
	private int quantity;
	private Date createtime;
	private int serverid;
	private int type;
	private String ext;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPlayerid() {
		return playerid;
	}
	public void setPlayerid(int playerid) {
		this.playerid = playerid;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public GoodsModel() {
		super();
	}
	public GoodsModel(int id, int playerid, int goodsid, int quantity, Date createtime, int serverid, int type,
			String ext) {
		super();
		this.id = id;
		this.playerid = playerid;
		this.goodsid = goodsid;
		this.quantity = quantity;
		this.createtime = createtime;
		this.serverid = serverid;
		this.type = type;
		this.ext = ext;
	}
	@Override
	public String toString() {
		return "GoodsModel [id=" + id + ", playerid=" + playerid + ", goodsid=" + goodsid + ", quantity=" + quantity
				+ ", createtime=" + createtime + ", serverid=" + serverid + ", type=" + type + ", ext=" + ext + "]";
	}
	
}
