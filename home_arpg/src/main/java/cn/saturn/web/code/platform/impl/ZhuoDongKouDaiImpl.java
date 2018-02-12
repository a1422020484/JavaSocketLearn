package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 卓动
 *
 * @author z
 */
@Component
public class ZhuoDongKouDaiImpl implements PlatformInterface {
	public static final String flag = "login";
	static final Logger log = LoggerFactory.getLogger(flag);

	String secretKey = PTConfig.val("ZhuoDongSecretKey");
	String cpId = PTConfig.val("ZhuoDongCpId");
	String url = PTConfig.val("ZhuoDongVerifyUrl");
	
	final String gameId = PTConfig.val("ZhuoDongGameId");
	final String channelId = PTConfig.val("ZhuoDongChannelId");
	final String packageName = PTConfig.val("ZhuoDongPackageName");

	String[] params = new String[] { "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "zhuodong";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {
		String uid = params.get("uid");
		String token = params.get("token");

		String sign = sha256(secretKey + cpId + gameId + channelId + packageName + token + uid);

		String resp = HttpUtils.create(url).addParam("cpid", cpId).addParam("gameid", gameId)
				.addParam("channelid", channelId).addParam("pn", packageName).addParam("sessiontoken", token)
				.addParam("gameuid", uid).addParam("psw", sign).get();
		Resp rs = JSON.parseObject(resp, Resp.class);
		if (rs == null) {
			return LoginResponse.Timeout;
		}

		LoginResponse lr = null;
		if (rs.state.code.equals("0")) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = uid;
		} else {
			log.error(flag + "|{}|{}", rs.state.code, rs.state.msg);
			lr = new LoginResponse(1, rs.state.msg);
		}
		return lr;
	}

	public String sha256(String orignalStr) {
		String resultStr = "";
		try {
			MessageDigest md256 = MessageDigest.getInstance("SHA-256");
			byte[] md256Encode = md256.digest(orignalStr.getBytes());
			resultStr = convertByteToHexString(md256Encode);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStr;
	}

	private static String convertByteToHexString(byte[] bytes) {
		String result = "";
		for (int i = 0; i < bytes.length; i++) {
			int temp = bytes[i] & 0xff;
			String tempHex = Integer.toHexString(temp);
			if (tempHex.length() < 2) {
				result += "0" + tempHex;
			} else {
				result += tempHex;
			}
		}
		return result;
	}

	static class Resp {
		State state;

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

		public static class State {
			String code;

			public String getCode() {
				return code;
			}

			public void setCode(String code) {
				this.code = code;
			}

			public String getMsg() {
				return msg;
			}

			public void setMsg(String msg) {
				this.msg = msg;
			}

			String msg;

		}

	}
}
