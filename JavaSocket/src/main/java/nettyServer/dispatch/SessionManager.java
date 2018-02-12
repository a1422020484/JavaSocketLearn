package nettyServer.dispatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import io.netty.channel.Channel;
import nettyServer.listener.SessionCloseListener;
import nettyServer.util.GameThreadFactory;
import nettyServer.util.LogKey;
import nettyServer.util.SessionIdGenerator;
import nettyServer.util.annotation.NotNull;

/**
 * Session管理器<br/>
 * 管理在线所有玩家,可获取在线玩家的ID列表,在线玩家总人数,踢玩家下线,发送消息给玩家,管理玩家组.
 *
 * @author yangxp
 */
public final class SessionManager implements ApplicationContextAware {

	private static final Logger log = LoggerFactory.getLogger(LogKey.CORE);
	
	private static ReentrantReadWriteLock sessionLock = new ReentrantReadWriteLock();
	private static Lock rlock = sessionLock.readLock();
	private static Lock wlock = sessionLock.writeLock();

	private static ConcurrentHashMap<String, Session> sessionids = new ConcurrentHashMap<>(5096);
	private static ConcurrentHashMap<Integer, Session> sessions = new ConcurrentHashMap<>(5096);	// remove的closeListener以这个为准

	/**
	 * 获取与本次连接对应的{@link Session}<br/>
	 * 如果没有获取到,那么实现应该创建一个默认的{@link Session},保存并返回,以供下一次获取.<br/>
	 *
	 * @param playerId
	 * @return 本次连接的{@link Session}
	 */
	@NotNull
	public static Session createSession(int playerId) {
		wlock.lock();
		try {
			for (;;) {
				String sessionId = SessionIdGenerator.generate();
				if (!sessionids.contains(sessionId)) {
					Session session = new DefaultSession(sessionId, playerId);
					sessionids.put(sessionId, session);
					sessions.put(playerId, session);
					return session;
				}
			}
		} finally {
			wlock.unlock();
		}
	}

	/**
	 * 检查是否有会话超时,将超时的会话删除并返回
	 * 
	 * @return 超时的会话列表
	 */
	static List<Session> checkTimeout() {
		List<Session> overdue = null;
		rlock.lock();
		try {
			for (Session session : sessionids.values()) {
				if (session.isTimeout()) {
					if (overdue == null) {
						overdue = new ArrayList<>();
					}
					overdue.add(session);
				}
			}
		} finally {
			rlock.unlock();
		}
		if (overdue != null && overdue.size() > 0) {
			Iterator<Session> iter = overdue.iterator();
			while (iter.hasNext()) {
				Session s = iter.next();
				wlock.lock();
				try {
					if (s.isTimeout()) {
						sessionids.remove(s.getSessionId());
						s = sessions.remove(s.getPlayerId());
						log.debug("Session removed! SessionId:{}, PlayerId:{}", s.getSessionId(), s.getPlayerId());
						/*
						 * 会话移除的玩家很长一段时间没有新操作了,所有操作肯定是已经完成了的,并且此处已加锁,
						 * 所以这里执行SessionCloseListener是安全的
						 */
						if (s != null) {
							for (SessionCloseListener l : sessionCloseListeners) {
								try {
									l.close(s.getPlayerId());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							// TODO 是否需要删除?其它玩家的操作可能会再增加
							ActionTaskService.remove(s.getPlayerId());
						}
					} else {
						iter.remove();
					}
				} finally {
					wlock.unlock();
				}
			}
		}
		return overdue;
	}

	// /**
	// * 在登陆成功后设置{@link Session},必须保证{@link Session#getPlayerId()}不能为null.
	// *
	// * @param session
	// * 会话
	// * @return 如果之前已存在与{@link Session#getPlayerId()}关联的session,那么返回之前的session.
	// */
	// static Session setSession(Session session) {
	// int pId = session.getPlayerId();
	// return sessions.put(pId, session);
	// }

	/**
	 * 获取与sessionId关联的Session,并更新活跃时间
	 * 
	 * @param sessionId
	 * @return 本次连接的{@link Session}
	 */
	public static Session getSession(String sessionId) {
		rlock.lock();
		try {
			Session s = sessionids.get(sessionId);
			if (s != null) {
				// 与清理操作互斥
				((DefaultSession) s).active();
			}
			return s;
		} finally {
			rlock.unlock();
		}
	}

	/**
	 * 登陆成功后,可通过玩家ID获取本次连接的{@link Session}
	 *
	 * @param playerId
	 *            玩家ID
	 * @return 本次连接的{@link Session},玩家没有连接时返回null.
	 */
	public static Session getSession(int playerId) {
		rlock.lock();
		try {
			return sessions.get(playerId);
		} finally {
			rlock.unlock();
		}
	}

	/**
	 * @param sessionId
	 * @return
	 */
	public static Session removeSession(String sessionId) {
		if (sessionId == null)
			return null;
		wlock.lock();
		try {
			Session session = sessionids.remove(sessionId);
			if (session != null) {
				int playerId = session.getPlayerId();
				sessions.remove(playerId);
				for (SessionCloseListener l : sessionCloseListeners) {
					try {
						l.close(playerId);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return session;
		} finally {
			wlock.unlock();
		}
	}

	/**
	 * 删除与本次连接相对应的{@link Session},在线的玩家将会断线
	 *
	 * @param playerId
	 *            玩家ID
	 * @return 被删除的{@link Session},在没有与playerID匹配的值时返回null.
	 */
	public static Session removeSession(Integer playerId) {
		if (playerId == null)
			return null;
		wlock.lock();
		try {
			Session session = sessions.remove(playerId);
			if (session != null) {
				sessionids.remove(session.getSessionId());
				for (SessionCloseListener l : sessionCloseListeners) {
					try {
						l.close(playerId);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return session;
		} finally {
			wlock.unlock();
		}
	}

	/**
	 * 移除所有在线玩家,断开socket.
	 */
	public static void removeAll() {
		wlock.lock();
		try {
			sessionids.clear();
			sessions.clear();
		} finally {
			wlock.unlock();
		}
	}

	/**
	 * @param playerId
	 *            玩家ID
	 * @return 指定玩家是否在线
	 */
	public static boolean online(Integer playerId) {
		return sessions.containsKey(playerId);
	}

	/**
	 * @return 在线玩家ID集合, 此集合不可修改.
	 */
	public static Set<Integer> onlines() {
		return Collections.unmodifiableSet(sessions.keySet());
	}

	/**
	 * @return 当前会话总数量
	 */
	public static int count() {
		return sessions.size();
	}

	/**
	 * 如果不在线,那么执行回调
	 * 
	 * @param playerId
	 * @param callback
	 */
	static void doIfNotOnline(int playerId, DoIfNotOnline callback) {
		rlock.lock();
		try {
			if (!sessions.containsKey(playerId)) {
				callback.doit();
			}
		} finally {
			rlock.unlock();
		}
	}

	/**
	 * 发送消息给在线的指定玩家,消息如果有重复(ActionId相同),那么只保存后者
	 *
	 * @param playerId
	 *            指定玩家ID
	 * @param msg
	 * @see {@link Session#write(Response)}
	 */
	public static void addMsg(int playerId, Response msg) {
		Session session = sessions.get(playerId);
		if (session == null) {
			return;
		}
		((DefaultSession) session).addMsg(msg);
	}
	
	public static void addMsgBySessionId(String sessionId, Response msg) {
		if (sessionId == null) {
			return;
		}
		Session session = sessionids.get(sessionId);
		if (session == null) {
			return;
		}
		((DefaultSession) session).addMsg(msg);
	}

	/**
	 * 发送消息给在线所有人
	 *
	 * @param msg
	 * @see {@link Channel#write(Object)}
	 */
	public static void addMsgToAll(Response msg) {
		// 发消息
		for (Session session : sessions.values()) {
			((DefaultSession) session).addMsg(msg);
		}
	}

	/**
	 * 发送消息给在线的玩家,可以添加一个过滤列表.
	 * 
	 * @param msg
	 * @param excludePlayerIds
	 *            过滤掉在此列表中的playerId
	 */
	public static void addMsgToAll(Response msg, List<Long> excludePlayerIds) {
		if (excludePlayerIds == null || excludePlayerIds.size() == 0) {
			addMsgToAll(msg);
			return;
		}
		outLoop: for (Session session : sessions.values()) {
			for (long eid : excludePlayerIds) {
				if (session.getPlayerId() == eid)
					continue outLoop;
			}
			((DefaultSession) session).addMsg(msg);
		}
	}

//	/**
//	 * 发送消息给在线的指定玩家,消息将延迟发送(在心跳时取出并发送),消息如果有重复(ActionId相同),那么只保存后者
//	 *
//	 * @param playerId
//	 *            指定玩家ID
//	 * @param msg
//	 * @see {@link Session#write(Response)}
//	 */
//	public static void addLazyMsg(int playerId, Response msg) {
//		Session session = sessions.get(playerId);
//		if (session == null) {
//			return;
//		}
//		((DefaultSession) session).addLazyMsg(msg);
//	}
//
//	/**
//	 * 发送消息给在线所有人,消息将延迟发送(在心跳时取出并发送)
//	 *
//	 * @param msg
//	 * @see {@link Channel#write(Object)}
//	 */
//	public static void addLazyMsg(Response msg) {
//		// 发消息
//		for (Session session : sessions.values()) {
//			((DefaultSession) session).addLazyMsg(msg);
//		}
//	}
//
//	/**
//	 * 发送消息给在线的玩家,可以添加一个过滤列表.消息将延迟发送(在心跳时取出并发送)
//	 * 
//	 * @param msg
//	 * @param excludePlayerIds
//	 *            过滤掉在此列表中的playerId
//	 */
//	public static void addLazyMsg(Response msg, int... excludePlayerIds) {
//		if (excludePlayerIds == null || excludePlayerIds.length == 0) {
//			addLazyMsg(msg);
//			return;
//		}
//		outLoop: 
//		for (Session session : sessions.values()) {
//			for (int eid : excludePlayerIds) {
//				if (session.getPlayerId() == eid) {
//					continue outLoop;
//				}
//			}
//			((DefaultSession) session).addLazyMsg(msg);
//		}
//	}
//
//	/**
//	 * 发送消息给在线的玩家,可以添加一个过滤列表.消息将延迟发送(在心跳时取出并发送)
//	 * 
//	 * @param msg
//	 * @param excludePlayerIds
//	 *            过滤掉在此列表中的playerId
//	 */
//	public static void addLazyMsg(Response msg, List<Long> excludePlayerIds) {
//		if (excludePlayerIds == null || excludePlayerIds.size() == 0) {
//			addLazyMsg(msg);
//			return;
//		}
//		outLoop: for (Session session : sessions.values()) {
//			for (long eid : excludePlayerIds) {
//				if (session.getPlayerId() == eid)
//					continue outLoop;
//			}
//			((DefaultSession) session).addLazyMsg(msg);
//		}
//	}
//
//	/**
//	 * 发送消息给在线的玩家,可以定制一个过滤器,自定义过滤规则.
//	 * 
//	 * @param msg
//	 * @param filter
//	 *            写出过滤器
//	 */
//	public static void addMsg(Response msg, WriterFilter filter) {
//		for (Session session : sessions.values()) {
//			if (!filter.accept(session.getPlayerId()))
//				continue;
//			((DefaultSession) session).addMsg(msg);
//		}
//	}

	private static Collection<SessionCloseListener> sessionCloseListeners;

	private static ScheduledExecutorService scheduleLoop = Executors.newSingleThreadScheduledExecutor(new GameThreadFactory("session monitor"));

	private static Runnable monitorRunnable = new Runnable() {
		@Override
		public void run() {
			SessionManager.checkTimeout();
		}
	};

	public static void stopMonitor() {
		scheduleLoop.shutdownNow();
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		sessionCloseListeners = ctx.getBeansOfType(SessionCloseListener.class).values();
		scheduleLoop.scheduleAtFixedRate(monitorRunnable, 0L, 1L, TimeUnit.MINUTES);
		log.info("session monitor executor started.");
	}
}
