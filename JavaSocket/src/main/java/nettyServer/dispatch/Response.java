package nettyServer.dispatch;

/**
 * 服务器返回给客户端
 *
 * @author zuojie.x
 * @see {@link ResponseFactory}
 */
public interface Response {
	/**
	 * @return 返回码
	 * @see ResponseCode
	 */
	int getCode();

	/**
	 * @return 请求的控制器ID
	 */
	int getActionId();

	/**
	 * @return 响应结果
	 */
	Object getResultDTO();
	
//	/**
//	 * @return 是否对数据体加密
//	 */
//	boolean isEncrypted();
}
