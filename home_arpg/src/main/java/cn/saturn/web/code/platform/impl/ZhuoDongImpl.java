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
public class ZhuoDongImpl implements PlatformInterface {
	public static final String flag = "login";
	static final Logger log = LoggerFactory.getLogger(flag);

	String secretKey = PTConfig.val("ZhuoDongSecretKey");
	String cpId = PTConfig.val("ZhuoDongCpId");
	String url = PTConfig.val("ZhuoDongVerifyUrl");

	final String zhuoDongKouDai2AndGameId1 = PTConfig.val("ZhuoDongKouDai2AndGameId1");
	final String zhuoDongKouDai2AndChannelId1 = PTConfig.val("ZhuoDongKouDai2AndChannelId1");
	final String zhuoDongKouDai2AndPackageName1 = PTConfig.val("ZhuoDongKouDai2AndPackageName1");

	final String zhuoDongKouDai2AndPackageName1232 = PTConfig.val("ZhuoDongKouDai2AndPackageName1232");
	final String zhuoDongKouDai2AndPackageName1233 = PTConfig.val("ZhuoDongKouDai2AndPackageName1233");
	final String zhuoDongKouDai2AndPackageName1234 = PTConfig.val("ZhuoDongKouDai2AndPackageName1234");

	final String zhuoDongKouDai2AndPackageName1301 = PTConfig.val("ZhuoDongKouDai2AndPackageName1301");
	final String zhuoDongKouDai2AndPackageName1302 = PTConfig.val("ZhuoDongKouDai2AndPackageName1302");
	final String zhuoDongKouDai2AndPackageName1303 = PTConfig.val("ZhuoDongKouDai2AndPackageName1303");

	final String zhuoDongKouDai2IosGameId1 = PTConfig.val("ZhuoDongKouDai2IosGameId1");
	final String zhuoDongKouDai2IosChannelId1 = PTConfig.val("ZhuoDongKouDai2IosChannelId1");
	final String zhuoDongKouDai2IosPackageName1 = PTConfig.val("ZhuoDongKouDai2IosPackageName1");

	final String zhuoDongKouDai2IosPackageName1252 = PTConfig.val("ZhuoDongKouDai2IosPackageName1252");
	final String zhuoDongKouDai2IosPackageName1254 = PTConfig.val("ZhuoDongKouDai2IosPackageName1254");
	final String zhuoDongKouDai2IosPackageName1255 = PTConfig.val("ZhuoDongKouDai2IosPackageName1255");
	final String zhuoDongKouDai2IosPackageName1256 = PTConfig.val("ZhuoDongKouDai2IosPackageName1256");
	final String zhuoDongKouDai2IosPackageName1257 = PTConfig.val("ZhuoDongKouDai2IosPackageName1257");
	final String zhuoDongKouDai2IosPackageName1258 = PTConfig.val("ZhuoDongKouDai2IosPackageName1258");
	final String zhuoDongKouDai2IosPackageName1259 = PTConfig.val("ZhuoDongKouDai2IosPackageName1259");
	final String zhuoDongKouDai2IosPackageName1260 = PTConfig.val("ZhuoDongKouDai2IosPackageName1260");
	final String zhuoDongKouDai2IosPackageName1261 = PTConfig.val("ZhuoDongKouDai2IosPackageName1261");
	final String zhuoDongKouDai2IosPackageName1262 = PTConfig.val("ZhuoDongKouDai2IosPackageName1262");
	final String zhuoDongKouDai2IosPackageName1263 = PTConfig.val("ZhuoDongKouDai2IosPackageName1263");

	final String zhuoDongShuMaAndGameId1 = PTConfig.val("ZhuoDongShuMaAndGameId1");
	final String zhuoDongShuMaAndChannelId1 = PTConfig.val("ZhuoDongShuMaAndChannelId1");
	final String zhuoDongShuMaAndPackageName1 = PTConfig.val("ZhuoDongShuMaAndPackageName1");

	final String zhuoDongShuMaAndPackageName1271 = PTConfig.val("ZhuoDongShuMaAndPackageName1271");
	final String zhuoDongShuMaAndPackageName1272 = PTConfig.val("ZhuoDongShuMaAndPackageName1272");
	final String zhuoDongShuMaAndPackageName1273 = PTConfig.val("ZhuoDongShuMaAndPackageName1273");
	final String zhuoDongShuMaAndPackageName1274 = PTConfig.val("ZhuoDongShuMaAndPackageName1274");
	final String zhuoDongShuMaAndPackageName1275 = PTConfig.val("ZhuoDongShuMaAndPackageName1275");
	final String zhuoDongShuMaAndPackageName1276 = PTConfig.val("ZhuoDongShuMaAndPackageName1276");
	final String zhuoDongShuMaAndPackageName1277 = PTConfig.val("ZhuoDongShuMaAndPackageName1277");

	final String zhuoDongShuMaIosGameId1 = PTConfig.val("ZhuoDongShuMaIosGameId1");
	final String zhuoDongShuMaIosChannelId1 = PTConfig.val("ZhuoDongShuMaIosChannelId1");
	final String zhuoDongShuMaIosPackageName1 = PTConfig.val("ZhuoDongShuMaIosPackageName1");

	final String zhuoDongShuMaIosPackageName2 = PTConfig.val("ZhuoDongShuMaIosPackageName2");
	final String zhuoDongShuMaIosPackageName3 = PTConfig.val("ZhuoDongShuMaIosPackageName3");
	final String zhuoDongShuMaIosPackageName4 = PTConfig.val("ZhuoDongShuMaIosPackageName4");
	final String zhuoDongShuMaIosPackageName5 = PTConfig.val("ZhuoDongShuMaIosPackageName5");
	final String zhuoDongShuMaIosPackageName6 = PTConfig.val("ZhuoDongShuMaIosPackageName6");
	final String zhuoDongShuMaIosPackageName7 = PTConfig.val("ZhuoDongShuMaIosPackageName7");

	// 口袋2英文安卓1235
	final String zhuoDongKouDai2EnAndGameId1235 = PTConfig.val("ZhuoDongKouDai2EnAndGameId1235");
	final String zhuoDongKouDai2EnAndChannelId1235 = PTConfig.val("ZhuoDongKouDai2EnAndChannelId1235");
	final String zhuoDongKouDai2EnAndPackageName1235 = PTConfig.val("ZhuoDongKouDai2EnAndPackageName1235");
	// 口袋2英文安卓1236
	final String zhuoDongKouDai2EnAndPackageName1236 = PTConfig.val("ZhuoDongKouDai2EnAndPackageName1236");
	// 口袋2英文安卓1237
	final String zhuoDongKouDai2EnAndPackageName1237 = PTConfig.val("ZhuoDongKouDai2EnAndPackageName1237");
	// 口袋2英文安卓1238
	final String zhuoDongKouDai2EnAndPackageName1238 = PTConfig.val("ZhuoDongKouDai2EnAndPackageName1238");
	// 口袋2英文安卓1239
	final String zhuoDongKouDai2EnAndPackageName1239 = PTConfig.val("ZhuoDongKouDai2EnAndPackageName1239");
	// 口袋2英文安卓1240
	final String zhuoDongKouDai2EnAndPackageName1240 = PTConfig.val("ZhuoDongKouDai2EnAndPackageName1240");
	// 口袋2英文安卓1241
	final String zhuoDongKouDai2EnAndPackageName1241 = PTConfig.val("ZhuoDongKouDai2EnAndPackageName1241");
	// ryen01
	final String zhuoDongKouDai2EnAndPackageNameRyen01 = PTConfig.val("ZhuoDongKouDai2EnAndPackageNameRyen01");

	// 口袋2英文ios1010
	final String zhuoDongKouDai2EnIosGameId1010 = PTConfig.val("ZhuoDongKouDai2EnIosGameId1010");
	final String zhuoDongKouDai2EnIosChannelId1010 = PTConfig.val("ZhuoDongKouDai2EnIosChannelId1010");
	final String zhuoDongKouDai2EnIosPackageName1010 = PTConfig.val("ZhuoDongKouDai2EnIosPackageName1010");
	// 口袋2英文ios1011
	final String zhuoDongKouDai2EnIosPackageName1011 = PTConfig.val("ZhuoDongKouDai2EnIosPackageName1011");
	// 口袋2英文ios1012
	final String zhuoDongKouDai2EnIosPackageName1012 = PTConfig.val("ZhuoDongKouDai2EnIosPackageName1012");
	// 口袋2英文ios1013
	final String zhuoDongKouDai2EnIosPackageName1013 = PTConfig.val("ZhuoDongKouDai2EnIosPackageName1013");
	// 口袋2英文ios1014
	final String zhuoDongKouDai2EnIosPackageName1014 = PTConfig.val("ZhuoDongKouDai2EnIosPackageName1014");
	// 口袋2英文ios1015
	final String zhuoDongKouDai2EnIosPackageName1015 = PTConfig.val("ZhuoDongKouDai2EnIosPackageName1015");
	// ryen10
	final String zhuoDongKouDai2EnIosPackageNameRyen10 = PTConfig.val("ZhuoDongKouDai2EnIosPackageNameRyen10");

	String[] params = new String[] { "subPt", "uid", "token" };

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

		String gameId = "";
		String channelId = "";
		String packageName = "";

		String uid = params.get("uid");
		String token = params.get("token");
		String subPlatform = params.get("subPt");

		if (subPlatform != null && subPlatform.equals("1231")) {

			gameId = zhuoDongKouDai2AndGameId1;
			channelId = zhuoDongKouDai2AndChannelId1;
			packageName = zhuoDongKouDai2AndPackageName1;

		} else if (subPlatform != null && subPlatform.equals("1232")) {

			gameId = zhuoDongKouDai2AndGameId1;
			channelId = zhuoDongKouDai2AndChannelId1;
			packageName = zhuoDongKouDai2AndPackageName1232;

		} else if (subPlatform != null && subPlatform.equals("1233")) {

			gameId = zhuoDongKouDai2AndGameId1;
			channelId = zhuoDongKouDai2AndChannelId1;
			packageName = zhuoDongKouDai2AndPackageName1233;

		} else if (subPlatform != null && subPlatform.equals("1234")) {

			gameId = zhuoDongKouDai2AndGameId1;
			channelId = zhuoDongKouDai2AndChannelId1;
			packageName = zhuoDongKouDai2AndPackageName1234;

		} else if (subPlatform != null && subPlatform.equals("1301")) {

			gameId = zhuoDongKouDai2AndGameId1;
			channelId = zhuoDongKouDai2AndChannelId1;
			packageName = zhuoDongKouDai2AndPackageName1301;

		} else if (subPlatform != null && subPlatform.equals("1302")) {

			gameId = zhuoDongKouDai2AndGameId1;
			channelId = zhuoDongKouDai2AndChannelId1;
			packageName = zhuoDongKouDai2AndPackageName1302;

		} else if (subPlatform != null && subPlatform.equals("1303")) {

			gameId = zhuoDongKouDai2AndGameId1;
			channelId = zhuoDongKouDai2AndChannelId1;
			packageName = zhuoDongKouDai2AndPackageName1303;

		} else if (subPlatform != null && subPlatform.equals("1251")) {

			gameId = zhuoDongKouDai2IosGameId1;
			channelId = zhuoDongKouDai2IosChannelId1;
			packageName = zhuoDongKouDai2IosPackageName1;

		} else if (subPlatform != null && subPlatform.equals("1252")) {

			gameId = zhuoDongKouDai2IosGameId1;
			channelId = zhuoDongKouDai2IosChannelId1;
			packageName = zhuoDongKouDai2IosPackageName1252;

		} else if (subPlatform != null && subPlatform.equals("1254")) {

			gameId = zhuoDongKouDai2IosGameId1;
			channelId = zhuoDongKouDai2IosChannelId1;
			packageName = zhuoDongKouDai2IosPackageName1254;

		} else if (subPlatform != null && subPlatform.equals("1255")) {

			gameId = zhuoDongKouDai2IosGameId1;
			channelId = zhuoDongKouDai2IosChannelId1;
			packageName = zhuoDongKouDai2IosPackageName1255;

		} else if (subPlatform != null && subPlatform.equals("1256")) {

			gameId = zhuoDongKouDai2IosGameId1;
			channelId = zhuoDongKouDai2IosChannelId1;
			packageName = zhuoDongKouDai2IosPackageName1256;

		} else if (subPlatform != null && subPlatform.equals("1257")) {

			gameId = zhuoDongKouDai2IosGameId1;
			channelId = zhuoDongKouDai2IosChannelId1;
			packageName = zhuoDongKouDai2IosPackageName1257;

		} else if (subPlatform != null && subPlatform.equals("1258")) {

			gameId = zhuoDongKouDai2IosGameId1;
			channelId = zhuoDongKouDai2IosChannelId1;
			packageName = zhuoDongKouDai2IosPackageName1258;

		} else if (subPlatform != null && subPlatform.equals("1259")) {

			gameId = zhuoDongKouDai2IosGameId1;
			channelId = zhuoDongKouDai2IosChannelId1;
			packageName = zhuoDongKouDai2IosPackageName1259;

		} else if (subPlatform != null && subPlatform.equals("1260")) {

			gameId = zhuoDongKouDai2IosGameId1;
			channelId = zhuoDongKouDai2IosChannelId1;
			packageName = zhuoDongKouDai2IosPackageName1260;

		} else if (subPlatform != null && subPlatform.equals("1261")) {

			gameId = zhuoDongKouDai2IosGameId1;
			channelId = zhuoDongKouDai2IosChannelId1;
			packageName = zhuoDongKouDai2IosPackageName1261;

		} else if (subPlatform != null && subPlatform.equals("1262")) {

			gameId = zhuoDongKouDai2IosGameId1;
			channelId = zhuoDongKouDai2IosChannelId1;
			packageName = zhuoDongKouDai2IosPackageName1262;

		} else if (subPlatform != null && subPlatform.equals("1263")) {

			gameId = zhuoDongKouDai2IosGameId1;
			channelId = zhuoDongKouDai2IosChannelId1;
			packageName = zhuoDongKouDai2IosPackageName1263;

		} else if (subPlatform != null && subPlatform.equals("1270")) {

			gameId = zhuoDongShuMaAndGameId1;
			channelId = zhuoDongShuMaAndChannelId1;
			packageName = zhuoDongShuMaAndPackageName1;

		} else if (subPlatform != null && subPlatform.equals("1271")) {

			gameId = zhuoDongShuMaAndGameId1;
			channelId = zhuoDongShuMaAndChannelId1;
			packageName = zhuoDongShuMaAndPackageName1271;

		} else if (subPlatform != null && subPlatform.equals("1272")) {

			gameId = zhuoDongShuMaAndGameId1;
			channelId = zhuoDongShuMaAndChannelId1;
			packageName = zhuoDongShuMaAndPackageName1272;

		} else if (subPlatform != null && subPlatform.equals("1273")) {

			gameId = zhuoDongShuMaAndGameId1;
			channelId = zhuoDongShuMaAndChannelId1;
			packageName = zhuoDongShuMaAndPackageName1273;

		} else if (subPlatform != null && subPlatform.equals("1274")) {

			gameId = zhuoDongShuMaAndGameId1;
			channelId = zhuoDongShuMaAndChannelId1;
			packageName = zhuoDongShuMaAndPackageName1274;

		} else if (subPlatform != null && subPlatform.equals("1275")) {

			gameId = zhuoDongShuMaAndGameId1;
			channelId = zhuoDongShuMaAndChannelId1;
			packageName = zhuoDongShuMaAndPackageName1275;

		} else if (subPlatform != null && subPlatform.equals("1276")) {

			gameId = zhuoDongShuMaAndGameId1;
			channelId = zhuoDongShuMaAndChannelId1;
			packageName = zhuoDongShuMaAndPackageName1276;

		} else if (subPlatform != null && subPlatform.equals("1277")) {

			gameId = zhuoDongShuMaAndGameId1;
			channelId = zhuoDongShuMaAndChannelId1;
			packageName = zhuoDongShuMaAndPackageName1277;

		} else if (subPlatform != null && subPlatform.equals("1290")) {

			gameId = zhuoDongShuMaIosGameId1;
			channelId = zhuoDongShuMaIosChannelId1;
			packageName = zhuoDongShuMaIosPackageName1;

		} else if (subPlatform != null && subPlatform.equals("1291")) {

			gameId = zhuoDongShuMaIosGameId1;
			channelId = zhuoDongShuMaIosChannelId1;
			packageName = zhuoDongShuMaIosPackageName2;

		} else if (subPlatform != null && subPlatform.equals("1292")) {

			gameId = zhuoDongShuMaIosGameId1;
			channelId = zhuoDongShuMaIosChannelId1;
			packageName = zhuoDongShuMaIosPackageName3;

		} else if (subPlatform != null && subPlatform.equals("1293")) {

			gameId = zhuoDongShuMaIosGameId1;
			channelId = zhuoDongShuMaIosChannelId1;
			packageName = zhuoDongShuMaIosPackageName4;

		} else if (subPlatform != null && subPlatform.equals("1294")) {

			gameId = zhuoDongShuMaIosGameId1;
			channelId = zhuoDongShuMaIosChannelId1;
			packageName = zhuoDongShuMaIosPackageName5;

		} else if (subPlatform != null && subPlatform.equals("1295")) {

			gameId = zhuoDongShuMaIosGameId1;
			channelId = zhuoDongShuMaIosChannelId1;
			packageName = zhuoDongShuMaIosPackageName6;

		} else if (subPlatform != null && subPlatform.equals("1296")) {

			gameId = zhuoDongShuMaIosGameId1;
			channelId = zhuoDongShuMaIosChannelId1;
			packageName = zhuoDongShuMaIosPackageName7;

		} else if (subPlatform != null && subPlatform.equals("1010")) {

			gameId = zhuoDongKouDai2EnIosGameId1010;
			channelId = zhuoDongKouDai2EnIosChannelId1010;
			packageName = zhuoDongKouDai2EnIosPackageName1010;

		} else if (subPlatform != null && subPlatform.equals("1011")) {

			gameId = zhuoDongKouDai2EnIosGameId1010;
			channelId = zhuoDongKouDai2EnIosChannelId1010;
			packageName = zhuoDongKouDai2EnIosPackageName1011;

		} else if (subPlatform != null && subPlatform.equals("1012")) {

			gameId = zhuoDongKouDai2EnIosGameId1010;
			channelId = zhuoDongKouDai2EnIosChannelId1010;
			packageName = zhuoDongKouDai2EnIosPackageName1012;

		} else if (subPlatform != null && subPlatform.equals("1013")) {

			gameId = zhuoDongKouDai2EnIosGameId1010;
			channelId = zhuoDongKouDai2EnIosChannelId1010;
			packageName = zhuoDongKouDai2EnIosPackageName1013;

		} else if (subPlatform != null && subPlatform.equals("1014")) {

			gameId = zhuoDongKouDai2EnIosGameId1010;
			channelId = zhuoDongKouDai2EnIosChannelId1010;
			packageName = zhuoDongKouDai2EnIosPackageName1014;

		} else if (subPlatform != null && subPlatform.equals("1015")) {

			gameId = zhuoDongKouDai2EnIosGameId1010;
			channelId = zhuoDongKouDai2EnIosChannelId1010;
			packageName = zhuoDongKouDai2EnIosPackageName1015;

		} else if (subPlatform != null && subPlatform.equals("ryen10")) {

			gameId = zhuoDongKouDai2EnIosGameId1010;
			channelId = zhuoDongKouDai2EnIosChannelId1010;
			packageName = zhuoDongKouDai2EnIosPackageNameRyen10;

		} else if (subPlatform != null && subPlatform.equals("1235")) {

			gameId = zhuoDongKouDai2EnAndGameId1235;
			channelId = zhuoDongKouDai2EnAndChannelId1235;
			packageName = zhuoDongKouDai2EnAndPackageName1235;

		} else if (subPlatform != null && subPlatform.equals("1236")) {

			gameId = zhuoDongKouDai2EnAndGameId1235;
			channelId = zhuoDongKouDai2EnAndChannelId1235;
			packageName = zhuoDongKouDai2EnAndPackageName1236;

		} else if (subPlatform != null && subPlatform.equals("1237")) {

			gameId = zhuoDongKouDai2EnAndGameId1235;
			channelId = zhuoDongKouDai2EnAndChannelId1235;
			packageName = zhuoDongKouDai2EnAndPackageName1237;

		} else if (subPlatform != null && subPlatform.equals("1238")) {

			gameId = zhuoDongKouDai2EnAndGameId1235;
			channelId = zhuoDongKouDai2EnAndChannelId1235;
			packageName = zhuoDongKouDai2EnAndPackageName1238;

		} else if (subPlatform != null && subPlatform.equals("1239")) {

			gameId = zhuoDongKouDai2EnAndGameId1235;
			channelId = zhuoDongKouDai2EnAndChannelId1235;
			packageName = zhuoDongKouDai2EnAndPackageName1239;

		} else if (subPlatform != null && subPlatform.equals("1240")) {

			gameId = zhuoDongKouDai2EnAndGameId1235;
			channelId = zhuoDongKouDai2EnAndChannelId1235;
			packageName = zhuoDongKouDai2EnAndPackageName1240;

		} else if (subPlatform != null && subPlatform.equals("1241")) {

			gameId = zhuoDongKouDai2EnAndGameId1235;
			channelId = zhuoDongKouDai2EnAndChannelId1235;
			packageName = zhuoDongKouDai2EnAndPackageName1241;

		} else if (subPlatform != null && subPlatform.equals("ryen01")) {

			gameId = zhuoDongKouDai2EnAndGameId1235;
			channelId = zhuoDongKouDai2EnAndChannelId1235;
			packageName = zhuoDongKouDai2EnAndPackageNameRyen01;
		}

		String sign = sha256(secretKey + cpId + gameId + channelId + packageName + token + uid);

		// log.error(flag +
		// "|subPlatform:{}|gameId:{}|channelId:{}|packageName:{}", subPlatform,
		// zhuoDongShuMaAndGameId1,
		// zhuoDongShuMaAndChannelId1, zhuoDongShuMaAndPackageName1);
		// log.error(flag + "|url:{}|cpId:{}|token:{}|uid:{}|sign:{}", url,
		// cpId, token, uid, sign);

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
