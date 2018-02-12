package cn.saturn.web.controllers.mail.dao;


public class PresetBugle {
	protected String msg;
	protected int intervalTime;
	protected int loopCount;
	protected int resetCount;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}

	public int getLoopCount() {
		return loopCount;
	}

	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}

	public int getResetCount() {
		return resetCount;
	}

	public void setResetCount(int resetCount) {
		this.resetCount = resetCount;
	}

}
