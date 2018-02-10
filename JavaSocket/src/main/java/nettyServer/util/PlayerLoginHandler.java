package nettyServer.util;

/**
 * 玩家登陆事, 用于处理下发消息.
 */
public interface PlayerLoginHandler {

	/**
	 * 登陆处理接口
	 * 
	 * @param playerId
	 * @return 消息数据, Response, Response[], List<Response>都可以
	 */
	Object login(int playerId);
}
