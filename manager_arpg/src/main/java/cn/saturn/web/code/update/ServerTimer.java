package cn.saturn.web.code.update;

public abstract class ServerTimer extends zyt.component.service.timer.Timer {

	public ServerTimer(int id, String name, int intervalTime) {
		super(id, name, intervalTime);
	}

	public ServerTimer(int id, String name, long startTime, int intervalTime, int resetCount) {
		super(id, name, startTime, intervalTime, resetCount);
	}

	protected abstract void update(int count);

}
