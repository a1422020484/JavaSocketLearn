package nettyServer.dispatch;

/**
 * 在写出时过滤掉指定玩家
 *
 * @author xiezuojie
 */
public interface WriterFilter {
	/**
	 * @param playerId
	 * @return 是否同意写出
	 */
	boolean accept(long playerId);
}
