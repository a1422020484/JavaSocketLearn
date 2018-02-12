package cn.saturn.web.code.login.domain;

public class DeviceSeal {
	private int id;
	private String del;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public DeviceSeal() {
		super();
	}
	public DeviceSeal(int id, String del) {
		super();
		this.id = id;
		this.del = del;
	}
	@Override
	public String toString() {
		return "DeviceSeal [id=" + id + ", del=" + del + "]";
	}
	
}
