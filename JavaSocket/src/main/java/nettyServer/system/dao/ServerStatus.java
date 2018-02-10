package nettyServer.system.dao;

public class ServerStatus {

	// 服务器实际状态
	// 已关闭
	public final static byte CLOSED = 0;
	// 关闭中
	public final static byte CLOSING = 1;
	// 已启动
	public final static byte LAUNCHED = 2;
	// 启动中
	public final static byte LAUNCHING = 3;

	// 服务器预发布状态(0:关闭,1:开启)
	private volatile byte preAnnouncement = 0;

	private static ServerStatus instance = new ServerStatus();

	public static ServerStatus getInstance() {
		return instance;
	}

	public byte getPreAnnouncement() {
		return preAnnouncement;
	}

	public void setPreAnnouncement(byte preAnnouncement) {
		this.preAnnouncement = preAnnouncement;
	}

}
