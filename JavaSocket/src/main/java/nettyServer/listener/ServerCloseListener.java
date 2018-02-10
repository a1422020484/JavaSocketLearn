package nettyServer.listener;

/**
 * 服务器关闭监听器<br>
 * 在服务器收到关闭命令时调用,此时所有已在线的玩家已被清除,服务器停止接受新socket连接, 此监听器提供给其它功能实现各自的退出保存操作
 *
 * @author zuojie.x
 */
public interface ServerCloseListener {
	/**
	 * 此方法适用于处理需要在服务器关闭前做一些保存或清理的操作.<br>
	 * 此时所有在线的玩家已清除,并且不再接受新的连接,执行此方法时不用考虑玩家操作导致的并发问题.
	 */
	void close();
	
	/**
	 * 执行策略:
	 * <br>
	 * <li><b>1</b>: 在ActionTask任务处理器关闭前执行,适用于在执行invokeStrategy()中需要提交任务到任务队列,提交的任务能够在服务器关闭前正常执行.</li>
	 * <li><b>2</b>: 在ActionTask任务处理器关闭后执行,适用于必须在所有任务完成后的数据保存(此时不会再有因执行任务而导致的数据变化).</li>
	 * @return 执行策略,必须是1或2,默认是1
	 */
	default int invokeStrategy() {
		return 1;
	}
}
