package nettyServer.dispatch;

import nettyServer.util.annotation.NotNull;

/**
 * 客户端的请求
 *
 * @author zuojie.x
 */
public interface Request {

	/**
	 * @return 用户的ID, 在没有登录时, 返回null
	 */
	Integer getPlayerId();

	/**
	 * @param session
	 */
	void setSession(Session session);

	/**
	 * @return {@link Session}
	 */
	Session getSession();
	
	/**
	 * @return 会话ID
	 */
	String getSessionId();

	/**
	 * @return 请求的控制器ID
	 */
	int getActionId();

	/**
	 * @return 客户端请求的参数列表
	 */
	@NotNull
	Object[] getParams();

	/**
	 * @return 客户端地址IP,无法正确获取到IP时返回null.
	 */
	String getHostAddress();
}
