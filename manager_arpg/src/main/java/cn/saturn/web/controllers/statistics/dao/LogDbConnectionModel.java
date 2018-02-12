package cn.saturn.web.controllers.statistics.dao;

import cn.saturn.web.model.auto.IModel;

public class LogDbConnectionModel implements IModel {
	protected long id;
	protected String url;
	protected String root;
	protected String pwd;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
