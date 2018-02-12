package cn.saturn.web.controllers.mail.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.saturn.web.model.auto.IModel;

public class ParamModel implements IModel {
	public static final String theme = "theme";
	public static final String csInfo = "csInfo";

	private long id;
	private String type;
	private String info;

	private transient final Map<String, Object> map = new HashMap<>();

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public Object get(String key) {
		return map.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<T> clazz) {
		Object obj = map.get(key);
		if (obj == null) {
			return null;
		}
		try {
			return (T) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object set(String key, Object value) {
		if (value == null) {
			return null;
		}
		return map.put(key, value);
	}

	public void setInfo(String info) {
		this.info = info;

		// 重设
		loadFromInfo();
	}

	protected boolean loadFromInfo() {
		// 清除数据
		map.clear();
		if (StringUtils.isBlank(info)) {
			return true; // 为空
		}

		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map0 = (Map<String, Object>) JSON.parseObject(info, Map.class);

			// 插入数据
			map.putAll(map0);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	protected boolean saveToInfo() {
		String jsonStr = JSON.toJSONString(map);
		if (this.info != null && this.info.equals(jsonStr)) {
			return false; // 相同
		}
		this.info = jsonStr;
		return true;
	}
}
