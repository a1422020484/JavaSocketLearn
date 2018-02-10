package nettyServer.listener;

/**
 * 客户端断开连接监听器
 *
 * @author zuojie.x
 */
public interface SessionCloseListener {
	/**
	 * 当客户端连接断开时调用此方法,此处不应该执行较耗时的操作!
	 *
	 * @param playerId
	 *            玩家游戏内ID
	 */
	void close(int playerId);
}
