package nettyServer.listener;

/**
 * 服务器启动监听器
 * 
 * @author zuojie.x
 */
public interface ServerStartupListener {
	/**
	 * 服务器加载完资源后,开始监听端口前调用
	 */
	void started();
}
