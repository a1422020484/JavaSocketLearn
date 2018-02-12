package cn.saturn.web.controllers.activity.dao;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.saturn.web.model.auto.IModel;

public class ActivityModel implements IModel {
	private long id;
	private String name;
	private String condition_desc;
	private String info;
	private String tips;
	private int type;
	private String activitySpeArgs;
	private String icon;
	private int activityOrder;

	public String getActivitySpeArgs() {
		return activitySpeArgs;
	}

	public void setActivitySpeArgs(String activitySpeArgs) {
		this.activitySpeArgs = activitySpeArgs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCondition_desc() {
		return condition_desc;
	}

	public void setCondition_desc(String condition_desc) {
		this.condition_desc = condition_desc;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public long getId() {
		return id;
	}

	public int getActivityOrder() {
		return activityOrder;
	}

	public void setActivityOrder(int activityOrder) {
		this.activityOrder = activityOrder;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJson() {

		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		jsonMap.put("id", id);

		jsonMap.put("name", name);
		jsonMap.put("condition_desc", condition_desc);
		jsonMap.put("info", info);
		jsonMap.put("tips", tips);

		jsonMap.put("type", type);
		jsonMap.put("activitySpeArgs", activitySpeArgs);
		jsonMap.put("icon", icon);
		jsonMap.put("activityOrder", activityOrder);

		// 转成json
		return JSON.toJSONString(jsonMap);
	}

}