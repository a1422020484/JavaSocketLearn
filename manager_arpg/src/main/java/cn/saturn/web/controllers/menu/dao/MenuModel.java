package cn.saturn.web.controllers.menu.dao;

import cn.saturn.web.model.auto.IModel;

public class MenuModel implements IModel {
	private long id;
	private String text;
	private String url;
	private int pid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
}