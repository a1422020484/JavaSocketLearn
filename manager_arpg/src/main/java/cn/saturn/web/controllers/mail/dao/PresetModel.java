package cn.saturn.web.controllers.mail.dao;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.saturn.web.model.auto.IModel;

public class PresetModel implements IModel {
	protected long id;
	protected int type;
	protected String info;
	protected String remark;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

	public long resetId() {
		this.id = PresetManager.getInstance().newId();
		return id;
	}

	public <T> T getValue(Class<T> clazz) {
		try {
			String value = this.getInfo();
			if (StringUtils.isEmpty(value)) {
				return null;
			}

			return JSON.parseObject(value, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getValue() {
		return info;
	}

	public boolean setValue(Object obj) {
		try {
			this.info = JSON.toJSONString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

}
