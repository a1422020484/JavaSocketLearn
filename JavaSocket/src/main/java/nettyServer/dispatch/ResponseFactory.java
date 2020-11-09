package nettyServer.dispatch;

import com.google.protobuf.MessageLite;

import nettyServer.util.CoreConfig;
import proto.ErrSC;

/**
 * Response工厂
 *
 * @author yangxp
 */
public final class ResponseFactory {

	/**
	 * 用户未登录!
	 */
	public static final Response USER_NOT_LOGIN = ok(CoreConfig.intValue("IDUserNotLogin"), null);
	/**
	 * 用户未登录!
	 */
	public static final Response USER_PLAYER_NOT_CREATED = ok(CoreConfig.intValue("IDUserPlayerNotCreated"), null);
	/**
	 * 服务器异常
	 */
	public static final Response SERVER_ERROR = failure(CoreConfig.stringValue("ServerErrorMsg"));

	/**
	 * 创建没有数据体的Response,等同于ResponseFactory.ok(null). <br>
	 * <br>
	 * <b>不能用于推送</b>
	 *
	 * @return {@link Response}
	 */
	public static Response ok() {
		return new DefaultResponse(ResponseCode.OK, -1, null);
	}

	/**
	 * 创建Response,默认对数据体加密<br>
	 * <br>
	 * <b>不能用于推送</b>
	 *
	 * @param result
	 * @return {@link Response}
	 */
	public static Response ok(MessageLite result) {
		return new DefaultResponse(ResponseCode.OK, -1, result);
	}

//	/**
//	 * 创建Response<br>
//	 * <br>
//	 * <b>不能用于推送</b>
//	 *
//	 * @param result
//	 *            返回结果
//	 * @param encrypted
//	 *            是否对数据体加密
//	 * @return {@link Response}
//	 */
//	public static Response ok(MessageLite result, boolean encrypted) {
//		return new DefaultResponse(ResponseCode.OK, -1, result, encrypted);
//	}

	/**
	 * 创建可用于推送的Response,默认对数据体加密
	 *
	 * @param headID
	 *            协议头ID
	 * @param result
	 *            返回结果
	 * @return {@link Response}
	 */
	public static Response ok(int headID, MessageLite result) {
		return new DefaultResponse(ResponseCode.OK, headID, result);
	}

//	/**
//	 * 创建可用于推送的Response,默认对数据体加密
//	 *
//	 * @param headID
//	 *            协议头ID
//	 * @param result
//	 *            返回结果
//	 * @param encrypted
//	 *            是否对数据体加密
//	 * @return {@link Response}
//	 */
//	public static Response ok(int headID, MessageLite result, boolean encrypted) {
//		return new DefaultResponse(ResponseCode.OK, headID, result, encrypted);
//	}

	/**
	 * 一次返回多个协议数据
	 * 
	 * @param responses
	 * @return {@link MultiResponse}
	 */
	public static MultiResponse ok(Response... responses) {
		DefaultMultiResponse mr = new DefaultMultiResponse();
		if (responses != null) {
			for (Response r : responses) {
				mr.responses.add(r);
			}
		}
		return mr;
	}

	/**
	 * 默认ActionCode为0
	 *
	 * @param msg
	 *            失败描述
	 * @return {@link Response}
	 * @see {@link ResponseFactory#failure(String, errorCode)}
	 */
	public static Response failure(String msg) {
		return failure(-1, msg, 0);
	}

	/**
	 * 创建表示错误描述的Response
	 *
	 * @param msg
	 *            失败描述
	 * @param errorCode
	 *            表示失败的代码
	 * @return {@link Response}
	 */
	public static Response failure(String msg, int errorCode) {
		return failure(-1, msg, errorCode);
	}

//	/**
//	 * 创建表示错误描述的Response
//	 *
//	 * @param headID
//	 *            协议头ID
//	 * @param msg
//	 *            失败描述
//	 * @param errorCode
//	 *            表示失败的代码
//	 * @param encrypted
//	 *            是否对数据体加密
//	 * @return {@link Response}
//	 */
//	public static Response failure(String msg, short errorCode, boolean encrypted) {
//		ErrSC errsc = ErrSC.newBuilder().setMsg(msg).setCode(errorCode).build();
//		return new DefaultResponse(ResponseCode.FAILURE, -1, errsc, encrypted);
//	}

	/**
	 * 创建表示错误描述的Response
	 *
	 * @param headID
	 *            协议头ID
	 * @param msg
	 *            失败描述
	 * @param errorCode
	 *            表示失败的代码
	 * @return {@link Response}
	 */
	public static Response failure(int headID, String msg, int errorCode) {
		ErrSC errsc = ErrSC.newBuilder().setMsg(msg).setCode(errorCode).build();
		return new DefaultResponse(ResponseCode.FAILURE, headID, errsc);
	}
	
//	/**
//	 * 创建表示错误描述的Response
//	 *
//	 * @param headID
//	 *            协议头ID
//	 * @param msg
//	 *            失败描述
//	 * @param errorCode
//	 *            表示失败的代码
//	 * @param encrypted
//	 *            是否对数据体加密
//	 * @return {@link Response}
//	 */
//	public static Response failure(int headID, String msg, short errorCode, boolean encrypted) {
//		ErrSC errsc = ErrSC.newBuilder().setMsg(msg).setCode(errorCode).build();
//		return new DefaultResponse(ResponseCode.FAILURE, headID, errsc, encrypted);
//	}

}
