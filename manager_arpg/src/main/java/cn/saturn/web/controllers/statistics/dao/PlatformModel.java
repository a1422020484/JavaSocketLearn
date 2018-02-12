package cn.saturn.web.controllers.statistics.dao;

import cn.saturn.web.model.auto.IModel;

public class PlatformModel implements IModel {
	protected long id;
	protected String platform;
	protected String subPlatform_code;
	protected String subPlatform_name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSubPlatform_code() {
		return subPlatform_code;
	}

	public void setSubPlatform_code(String subPlatform_code) {
		this.subPlatform_code = subPlatform_code;
	}

	public String getSubPlatform_name() {
		return subPlatform_name;
	}

	public void setSubPlatform_name(String subPlatform_name) {
		this.subPlatform_name = subPlatform_name;
	}
}
