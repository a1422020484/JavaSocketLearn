package nettyServer.dispatch;

import java.util.List;


/**
 * 一次连接会话
 *
 * @author yangxp
 */
public interface Session {

	/**
	 *
	 * @return 用户的ID
	 */
	int getPlayerId();
	
	/**
	 * @return 在用户登录成功后将由服务器分配一个唯一的字符串作为会话ID
	 */
	String getSessionId();

//	/**
//	 * 通过判断{@link #getSessionId()}是否等于null来决定是否已登陆
//	 *
//	 * @return 是否登陆成功
//	 */
//	boolean isLogin();
	
	/**
	 * @return 是否创建了角色数据
	 */
	boolean isPlayerCreated();
	
	/**
	 * 设置是否创建了角色数据
	 * @param isCreated
	 */
	void setPlayerCreated(boolean isCreated);

	/**
	 * 设置键和值关联并保存在会话中
	 *
	 * @param key
	 * @param value
	 * @return 如果之前存在与key关联的值, 那么返回之前与指定key关联的值, 否则返回null
	 */
	Object setAttribute(Object key, Object value);

	/**
	 * 获取与指定key关联的值
	 *
	 * @param key
	 * @return 与指定key关联的值
	 */
	Object getAttribute(Object key);

	/**
	 * 删除与指定key关联的值
	 *
	 * @param key
	 * @return 被删除的值
	 */
	Object removeAttribute(Object key);

	/**
	 * 删除所有已存在的键和值
	 */
	void removeAllAttribute();
	
	/**
	 * @return 会话是否过期
	 */
	boolean isTimeout();
	
	/**
	 * 读取并且清空所有未读的消息
	 * @return 未读的消息列表
	 */
	List<Response> takeMsg();
	
//	/**
//	 * 读取并且清空所有未读的延迟消息
//	 * 
//	 * @return 未读的消息列表
//	 */
//	List<Response> takeLazyMsg();

}
