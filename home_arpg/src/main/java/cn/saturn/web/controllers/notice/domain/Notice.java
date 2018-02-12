package cn.saturn.web.controllers.notice.domain;

public class Notice {
	private long id;
	private String s_id;
	private String notice;
	private boolean enable;
	private String imgs;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getS_id() {
		return s_id;
	}

	public void setS_id(String s_id) {
		this.s_id = s_id;
	}
	
	public String[] getSrvs(){
		if(s_id==null)return new String[0];
		return s_id.split(",");
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	
}
