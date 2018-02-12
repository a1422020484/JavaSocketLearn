package cn.saturn.web.controllers.poke.dao;

import java.util.Arrays;

/**
 * 口袋王牌弹窗信息
 * @author Administrator
 *
 */
public class NoticeWp {
	
	protected String notice;  //信息
	protected int srvs[];  //需要发送的服务器
	
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public int[] getSrvs() {
		return srvs;
	}
	public void setSrvs(int[] srvs) {
		this.srvs = srvs;
	}
	public NoticeWp() {
		super();
	}
	public NoticeWp(String notice, int[] srvs) {
		super();
		this.notice = notice;
		this.srvs = srvs;
	}
	@Override
	public String toString() {
		return "NoticeWp [notice=" + notice + ", srvs=" + Arrays.toString(srvs) + "]";
	}
	
	
}
