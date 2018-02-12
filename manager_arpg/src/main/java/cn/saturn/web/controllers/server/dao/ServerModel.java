package cn.saturn.web.controllers.server.dao;

import cn.saturn.web.model.auto.IModel;

import com.alibaba.fastjson.annotation.JSONField;

public class ServerModel implements IModel {
	public static final int srvState_Hide = 0; // 隐藏
	public static final int srvState_Normal = 1; // 正常
	public static final int srvState_Close = 2; // 维护
	public static final int srvState_Busy = 3; // 繁忙
	public static final int srvState_Full = 4; // 爆满
	public static final String[] srvStateStrs = new String[] { "隐藏", "正常", "维护", "繁忙", "爆满" };

	private long id;
	private String name; // 名称
	private String url; // url
	private int state; // 状态
	private String remark; // 备注
	private String maintainText; // 维护文本
	private int section; // 区
	private int recommend; // 是否推荐
	private int priority; // 优先级
	protected boolean operate; // 是否提供操作
	private String platforms; // 平台信息, 用于筛选平台
	private int openTime; // 定时开服时间
	private String open_time; // 服务器开服时间,用于查询

	/**
	 * 创建服务器标示(id-name)
	 * 
	 * @param model
	 * @return
	 */
	public static String getSrvStr(ServerModel model) {
		return model.getId() + "-" + model.getName();
	}

	@JSONField(serialize = false)
	public String getSrvStr0() {
		return getSrvStr(this);
	}

	/**
	 * 获取服务器id (从服务器标示中 id-name)
	 * 
	 * @param srvCode
	 * @return
	 */
	public static int getSrvId(String srvCode) {
		if (srvCode == null) {
			return 0;
		}
		// 拆分对象 srvId + "-" + srvName
		int index = srvCode.indexOf('-');
		if (index <= 0) {
			return 0; // 格式错误
		}

		// 解析id
		String idStr = srvCode.substring(0, index);
		Integer id = Integer.valueOf(idStr);
		if (id == null) {
			return 0; // 错误
		}
		return id;
	}

	/**
	 * 创建服务器客户端, 用户访问服务器
	 * 
	 * @return
	 */
	public IClient createClient() {
		if (this.url == null) {
			return null;
		}
		if(!url.startsWith("http") && !url.startsWith("ftp"))
			return null;
		try {
			SaturnClient client = new SaturnClient(this.url);
			return client;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@JSONField(serialize = false)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public String getOpen_time() {
		return open_time;
	}

	public void setOpen_time(String open_time) {
		this.open_time = open_time;
	}


	@JSONField(serialize = false)
	public String getStateStr() {
		// switch (state) {
		// case srvState_Normal:
		// return "正常";
		// case srvState_Close:
		// return "关闭";
		// }
		// return "未知";
		return srvStateStrs[state];
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	// @JSONField(serialize = false)
	public boolean isOperate() {
		return operate;
	}

	public void setOperate(boolean operate) {
		this.operate = operate;
	}

	public String getMaintainText() {
		return maintainText;
	}

	public void setMaintainText(String maintainText) {
		this.maintainText = maintainText;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getPlatforms() {
		return platforms;
	}

	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}

	/** 检测是否开放平台 **/
	public boolean filtrate(String platform, String version) {
		return SectionModel.filtrate(platforms, platform, version);
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ServerModel [id=" + id + ", name=" + name + ", url=" + url + ", state=" + state + ", remark=" + remark
				+ ", maintainText=" + maintainText + ", section=" + section + ", recommend=" + recommend + ", priority="
				+ priority + ", operate=" + operate + ", platforms=" + platforms + "]";
	}

	@JSONField(serialize = false)
	public int getOpenTime() {
		return openTime;
	}

	public void setOpenTime(int openTime) {
		this.openTime = openTime;
	}

}
