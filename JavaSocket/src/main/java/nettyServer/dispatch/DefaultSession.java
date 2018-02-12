package nettyServer.dispatch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nettyServer.util.CoreConfig;

/**
 * 默认Session实现
 *
 * @author yangxp
 */
class DefaultSession implements Session {
	protected String sessionId;
	protected int playerId;
	protected boolean playerCreated;
	private Map<Object, Object> attributeMap;

	private LinkedList<Response> msgQueue;

	private volatile long lastActiveTime;

	/** 会话过期时间(毫秒) */
	static int maxInactiveInterval = CoreConfig.intValue("SessionTimeout") * 1000;
	/** 会话消息队列最大值 */
	static int SessionMsgQueueMaxSize = CoreConfig.intValue("SessionMsgQueueMaxSize");

	DefaultSession(String sessionId, int playerId) {
		if (playerId <= 0L) {
			throw new RuntimeException("playerId不能小于等于0.");
		}
		this.sessionId = sessionId;
		this.playerId = playerId;
		this.attributeMap = new ConcurrentHashMap<>();
		this.lastActiveTime = System.currentTimeMillis();
		msgQueue = new LinkedList<>();
	}

	@Override
	public int getPlayerId() {
		return playerId;
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public boolean isPlayerCreated() {
		return playerCreated;
	}

	@Override
	public void setPlayerCreated(boolean isCreated) {
		playerCreated = isCreated;
	}

	// @Override
	// public boolean isLogin() {
	// return sessionId != null;
	// }

	@Override
	public Object setAttribute(Object key, Object value) {
		return attributeMap.put(key, value);
	}

	@Override
	public Object getAttribute(Object key) {
		return attributeMap.get(key);
	}

	@Override
	public Object removeAttribute(Object key) {
		return attributeMap.remove(key);
	}

	@Override
	public void removeAllAttribute() {
		attributeMap.clear();
	}

	@Override
	public boolean isTimeout() {
		return System.currentTimeMillis() > lastActiveTime + maxInactiveInterval;
	}

	void active() {
		lastActiveTime = System.currentTimeMillis();
	}

	@Override
	public List<Response> takeMsg() {
		synchronized (msgQueue) {
			List<Response> ret = new ArrayList<>(msgQueue);
			msgQueue.clear();
			return ret;
		}
	}

	void addMsg(Response msg) {
		synchronized (msgQueue) {
			msgQueue.addLast(msg);
			if (msgQueue.size() > SessionMsgQueueMaxSize) {
				msgQueue.removeFirst();
			}
		}
	}

}
