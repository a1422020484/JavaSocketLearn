package nettyServer.util;

import nettyServer.dispatch.Request;

/**
 * @author yangxp
 */
public final class Sign {
	static final String SecretKey = CoreConfig.stringValue("SystemSecretKey");

	/**
	 * SIGN计算方法:md5(源串+密钥)<br/>
	 * 源串=request.getActionId()+secretKey<br/>
	 *
	 * @param request
	 * @return sign
	 */
	public static String createSign(Request request) {
		StringBuilder builder = new StringBuilder(64);
		builder.append(request.getActionId());
		builder.append(SecretKey);
		return MD5.encode(builder.toString());
	}

	/**
	 * @param actionId
	 * @return sign
	 */
	public static String createSign(int actionId) {
		StringBuilder builder = new StringBuilder(64);
		builder.append(actionId);
		builder.append(SecretKey);
		return MD5.encode(builder.toString());
	}
}
