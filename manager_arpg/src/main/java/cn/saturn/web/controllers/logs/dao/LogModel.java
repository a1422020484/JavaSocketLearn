package cn.saturn.web.controllers.logs.dao;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.saturn.web.controllers.login.dao.UserModel;

public class LogModel {
	public static final int email_type = 1; // 邮件
	public static final int gm_type = 2; // gm
	public static final int vpay_type = 3; // 虚拟支付
	public static final int chest_type = 4;
	public static final int gmall_type = 5; // 全服指令

	private long id;
	private long user_id;
	private String user_name;
	private int type;
	private String log_time;
	private String content;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLog_time() {
		return log_time;
	}

	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public <T> T getValue(Class<T> clazz) {
		try {
			String value = this.getContent();
			if (StringUtils.isEmpty(value))
				return null;

			return JSON.parseObject(value, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建 logModel <br>
	 * 
	 * @param model
	 * @param logType
	 * @param json
	 * @return
	 */
	public static LogModel create(UserModel model, int logType, String json) {
		LogModel log = null;
		if (model != null) {
			log = new LogModel();
			log.user_id = model.getId();
			log.user_name = model.getUsername();
			log.type = logType;
			log.content = json;
		}
		return log;
	}
}
