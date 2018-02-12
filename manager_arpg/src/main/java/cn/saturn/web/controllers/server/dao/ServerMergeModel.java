package cn.saturn.web.controllers.server.dao;

import java.util.Date;

public class ServerMergeModel {
	
	private  int id;
	private String name;
	private int pid;
	private int state;
	private Date mertime;
	private int invalid;
	
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
	public Date getMertime() {
		return mertime;
	}
	public void setMertime(Date mertime) {
		this.mertime = mertime;
	}
	

	public int getInvalid() {
		return invalid;
	}
	public void setInvalid(int invalid) {
		this.invalid = invalid;
	}
	public ServerMergeModel() {
		super();
	}
	public ServerMergeModel(int id, String name, int pid, int state, Date mertime, int invalid) {
		super();
		this.id = id;
		this.name = name;
		this.pid = pid;
		this.state = state;
		this.mertime = mertime;
		this.invalid = invalid;
	}
	@Override
	public String toString() {
		return "ServerMergeModel [id=" + id + ", name=" + name + ", pid=" + pid + ", state=" + state + ", mertime="
				+ mertime + ", invalid=" + invalid + "]";
	}
	
	
	
}
