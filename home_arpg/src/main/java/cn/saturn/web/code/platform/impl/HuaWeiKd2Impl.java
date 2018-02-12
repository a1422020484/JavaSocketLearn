package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class HuaWeiKd2Impl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	String HuaWeiAppId = PTConfig.val("HuaWeiAppId");
	String HuaWeiPublicKey = PTConfig.val("HuaWeiPublicKey");
	String HuaWeiAccessTokenSvc = PTConfig.val("HuaWeiAccessTokenSvc");
	String HuaWeiAPIUrl = PTConfig.val("HuaWeiAPIUrl");

	String[] params = new String[] { "access_token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "huawei";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		// access_token内容：playerId+"|"+gameAuthSign+"|"+ts。
		String data = params.get("access_token");
		log.info("huawei|pass data:{}", data);

		String[] datas = data.split("\\|");
		String playerId = datas[0];
		String authSign = datas[1];
		String ts = datas[2];

		try {
			// authSign = URLEncoder.encode(authSign, "utf-8");
			authSign = authSign.replaceAll(" ", "+");
			log.info("huawei|authSign:{}", authSign);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("huawei|replace authsign \" \" with \"+\" error|e:{}", e);
		}

		String content = HuaWeiAppId + ts + playerId;
		boolean signVerify = false;
		try {
			signVerify = verify(content.getBytes("UTF-8"), HuaWeiPublicKey, authSign);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// String signStr = "appId=" + HuaWeiAppId + "&ts=" + ts + "&playerId="
		// + playerId;
		// String mySign = sha256(signStr);
		// boolean compareSign = Rsa.doCheck(mySign, authSign, HuaWeiPublicKey);

		log.info("huawei|content:{}|signVerify:{}", content, signVerify);

		LoginResponse lr = null;
		if (signVerify) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = playerId;
		}

		return lr;
	}

	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		byte[] keyBytes = decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance("SHA256WithRSA");
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(decode(sign));
	}

	static public String encode(byte[] data) {
		return encode(data, data.length);
	}

	static public String encode(byte[] data, int length) {
		char[] out = new char[((length + 2) / 3) * 4];

		for (int i = 0, index = 0; i < length; i += 3, index += 4) {
			boolean quad = false;
			boolean trip = false;

			int val = (0xFF & (int) data[i]);
			val <<= 8;
			if ((i + 1) < length) {
				val |= (0xFF & (int) data[i + 1]);
				trip = true;
			}
			val <<= 8;
			if ((i + 2) < length) {
				val |= (0xFF & (int) data[i + 2]);
				quad = true;
			}
			out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 1] = alphabet[val & 0x3F];
			val >>= 6;
			out[index + 0] = alphabet[val & 0x3F];
		}

		return new String(out);
	}

	public static byte[] decode(String data) {

		int tempLen = data.length();
		for (int ix = 0; ix < data.length(); ix++) {
			if ((data.charAt(ix) > 255) || codes[data.charAt(ix)] < 0)
				--tempLen;

		}

		int len = (tempLen / 4) * 3;
		if ((tempLen % 4) == 3)
			len += 2;
		if ((tempLen % 4) == 2)
			len += 1;

		byte[] out = new byte[len];

		int shift = 0;
		int accum = 0;
		int index = 0;

		for (int ix = 0; ix < data.length(); ix++) {
			int value = (data.charAt(ix) > 255) ? -1 : codes[data.charAt(ix)];

			if (value >= 0) {
				accum <<= 6;
				shift += 6;
				accum |= value;
				if (shift >= 8) {
					shift -= 8;
					out[index++] = (byte) ((accum >> shift) & 0xff);
				}
			}

		}

		if (index != out.length) {
			return new byte[0];
		}

		return out;
	}

	static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();

	static private byte[] codes = new byte[256];
	static {
		for (int i = 0; i < 256; i++)
			codes[i] = -1;
		for (int i = 'A'; i <= 'Z'; i++)
			codes[i] = (byte) (i - 'A');
		for (int i = 'a'; i <= 'z'; i++)
			codes[i] = (byte) (26 + i - 'a');
		for (int i = '0'; i <= '9'; i++)
			codes[i] = (byte) (52 + i - '0');
		codes['+'] = 62;
		codes['/'] = 63;
	}
}
