package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import sun.misc.BASE64Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 益玩平台
 *
 * @author liyanmeng
 */
@Component
public class YiwanImpl implements PlatformInterface {
    public static final String flag = "yiwan";
    static final Logger log = LoggerFactory.getLogger("login");

	// 口袋2安卓
	final String EwanAppID = PTConfig.val("EwanAppID");
	final String EwanPacketID = PTConfig.val("EwanPacketID");
	final String EwanSignKey = PTConfig.val("EwanSignKey");
	final String EwanAppKey = PTConfig.val("EwanAppKey");

	// String[] params = new String[] { "gameid", "uid", "token" };
	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return flag;
	}

	@Override
	public LoginResponse login(Map<String, String> params) {
		String subPlatform = params.get("subPt");
		String uid = params.get("uid");
		String[] uids = uid.split("\\|");
		String gameId = uids[0];
		String sign = uids[1];
		String token = params.get("token");
//		log.error(flag + "3|{}|{}|{}", gameId,sign,token);
		// 签名生成:openid 的值加上“|”加上token 的值加上“|”加上appkey 的值进行MD5 运算，取32 位小写的MD5 值后与sign 的值进行匹配，如匹配一致则数据验证成功，否则失败。
        String auth = token + "|" + EwanAppKey;
        String authMD5 = MD5.encode(auth);
		try {

			if (sign == null||"".equals(sign)) {
				log.error(flag + "|{}", "sign为空");
				return LoginResponse.ParamError;
			}
			
			LoginResponse lr = null;
			if (authMD5.equals(sign)) {
				lr = new LoginResponse();
				lr.userInfo = new UserInfo();
				lr.userInfo.account = gameId;
			} else {
				log.error(flag + "|{}|{}", "authMD5不等于sign"+authMD5, sign);
				lr = new LoginResponse(1, "验签失败");
			}
			return lr;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	public static String encodeBASE64(byte[] key) {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	public static byte[] digestMD5(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		return md5.digest();
	}
}