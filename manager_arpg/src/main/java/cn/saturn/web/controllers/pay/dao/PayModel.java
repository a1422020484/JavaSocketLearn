package cn.saturn.web.controllers.pay.dao;

public class PayModel {
	
	private int id;
	private String payaddr;
	private  String  ext;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPayaddr() {
		return payaddr;
	}
	public void setPayaddr(String payaddr) {
		this.payaddr = payaddr;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	
	public PayModel() {
		super();
	}
	public PayModel(int id, String payaddr, String ext) {
		super();
		this.id = id;
		this.payaddr = payaddr;
		this.ext = ext;
	}

}
