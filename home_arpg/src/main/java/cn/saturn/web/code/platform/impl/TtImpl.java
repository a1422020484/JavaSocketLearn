package cn.saturn.web.code.platform.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import cn.saturn.web.code.platform.utils.TtHttpUtil;
import sun.misc.BASE64Encoder;

/**
 * tt语音
 *
 * @author z
 */
@Component
public class TtImpl implements PlatformInterface {
	public static final String flag = "tt";
	static final Logger log = LoggerFactory.getLogger(flag);

	// 口袋2安卓
	final String ttGameId = PTConfig.val("TtGameId");
	final String ttSignSecretKey = PTConfig.val("TtSignSecretKey");
	final String ttPaySecretKey = PTConfig.val("TtPaySecretKey");
	final String ttLoginVerifyUrl = PTConfig.val("TtLoginVerifyUrl");

	// 口袋2ios
	final String ttIosGameId = PTConfig.val("TtIosGameId");
	final String ttIosSignSecretKey = PTConfig.val("TtIosSignSecretKey");
	final String ttIosPaySecretKey = PTConfig.val("TtIosPaySecretKey");
	final String ttIosLoginVerifyUrl = PTConfig.val("TtIosLoginVerifyUrl");

	// String[] params = new String[] { "gameid", "uid", "token" };
	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "tt";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String gameIdTemp = ttGameId;
		String signSecretKeyTemp = ttSignSecretKey;
		String paySecretKeyTemp = ttPaySecretKey;
		String loginVerifyUrlTemp = ttLoginVerifyUrl;

		String subPlatform = params.get("subPt");
		String uid = params.get("uid");
		String str = params.get("token");
		String[] strs = str.split("\\|");
		String gameId = strs[0];
		String token = strs[1];

		if (subPlatform != null && subPlatform.equals("10020")) {
			gameIdTemp = ttGameId;
			signSecretKeyTemp = ttSignSecretKey;
			paySecretKeyTemp = ttPaySecretKey;
			loginVerifyUrlTemp = ttLoginVerifyUrl;

		} else if (subPlatform != null && subPlatform.equals("1501")) {
			gameIdTemp = ttIosGameId;
			signSecretKeyTemp = ttIosSignSecretKey;
			paySecretKeyTemp = ttIosPaySecretKey;
			loginVerifyUrlTemp = ttIosLoginVerifyUrl;

		}

		if (!gameId.equals(gameIdTemp)) {
			// gameId不匹配
			return LoginResponse.ParamError;
		}
		Map<String, Object> urldata = new HashMap<String, Object>();
		urldata.put("gameId", Integer.parseInt(gameId));
		urldata.put("uid", Long.parseLong(uid));
		String jsonBody = JSONObject.toJSONString(urldata);
		// String jsonBody = "{\"gameId\":" + gameId + ",\"uid\":" + uid + "}";
		String key = signSecretKeyTemp; // 加密用的秘钥
		String src = jsonBody + key; // 密钥+数据组合源串
		// 加密后,生成的bytes字节数组
		byte[] enBytes;
		try {
			enBytes = digestMD5(src.getBytes("utf-8"));
			String sign = encodeBASE64(enBytes).trim();

			Map<String, Object> header = new HashMap<String, Object>();
			header.put("sid", token);
			header.put("sign", sign);

			String resp = "";
			try {
				resp = TtHttpUtil.doPost(loginVerifyUrlTemp, jsonBody, header);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			log.error(flag + "|{}", resp);
			Resp rs = JSON.parseObject(resp, Resp.class);
			if (rs == null) {
				return LoginResponse.Timeout;
			}

			LoginResponse lr = null;
			if (rs.head.result.equals("0")) {
				lr = new LoginResponse();
				lr.userInfo = new UserInfo();
				lr.userInfo.account = uid;
			} else {
				log.error(flag + "|{}|{}", rs.head.result, rs.head.message);
				lr = new LoginResponse(1, rs.head.message);
			}
			return lr;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
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

	static class Resp {
		Head head;

		public Head getHead() {
			return head;
		}

		public void setHead(Head head) {
			this.head = head;
		}

		public static class Head {
			String result;
			String message;

			public String getResult() {
				return result;
			}

			public void setResult(String result) {
				this.result = result;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}
		}

	}
}
