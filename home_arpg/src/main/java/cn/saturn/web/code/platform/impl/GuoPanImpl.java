package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class GuoPanImpl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	final String GuoPanAppId = PTConfig.val("GuoPanAppId");
	final String GuoPanSecurtKey = PTConfig.val("GuoPanSecurtKey");
	final String GuoPanVerifyUrl = PTConfig.val("GuoPanVerifyUrl");

	final String GuoPanIosAppId = PTConfig.val("GuoPanIosAppId");
	final String GuoPanIosSecurtKey = PTConfig.val("GuoPanIosSecurtKey");

	final String GuoPanPaPaAppId = PTConfig.val("GuoPanPaPaAppId");
	final String GuoPanPaPaSecurtKey = PTConfig.val("GuoPanPaPaSecurtKey");
	
	//口袋2果盘安卓10001
	final String GuoPanAppId10001 = PTConfig.val("GuoPanAppId10001");
	final String GuoPanSecurtKey10001 = PTConfig.val("GuoPanSecurtKey10001");
	
	//口袋2果盘Ios1401
	final String GuoPanAppId1401 = PTConfig.val("GuoPanAppId1401");
	final String GuoPanSecurtKey1401 = PTConfig.val("GuoPanSecurtKey1401");
	
	//果盘掌机数码世界z10070
	final String GuoPanAppIdZ10070 = PTConfig.val("GuoPanAppIdZ10070");
	final String GuoPanSecurtKeyZ10070 = PTConfig.val("GuoPanSecurtKeyZ10070");
	
	//果盘掌机数码世界Ios-zj011guopan
	final String GuoPanAppIdZj011guopan = PTConfig.val("GuoPanAppIdZj011guopan");
	final String GuoPanSecurtKeyZj011guopan = PTConfig.val("GuoPanSecurtKeyZj011guopan");

	String[] params = new String[] { "game_uin", "token", "subPt" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "guopan";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {
		if (log.isDebugEnabled()) {
			log.debug("params: {}", params);
		}

		String guoPanAppIdTemp = GuoPanAppId;
		String guoPanSecurtKeyTemp = GuoPanSecurtKey;

		String gameUin = params.get("game_uin");
		String token = params.get("token");
		String subPlatform = params.get("subPt");
		if (subPlatform.equals("7001")) {
			guoPanAppIdTemp = GuoPanIosAppId;
			guoPanSecurtKeyTemp = GuoPanIosSecurtKey;

		} else if (subPlatform.equals("1025")) {
			guoPanAppIdTemp = GuoPanPaPaAppId;
			guoPanSecurtKeyTemp = GuoPanPaPaSecurtKey;

		} else if (subPlatform.equals("10001")) {
			guoPanAppIdTemp = GuoPanAppId10001;
			guoPanSecurtKeyTemp = GuoPanSecurtKey10001;

		} else if (subPlatform.equals("1401")) {
			guoPanAppIdTemp = GuoPanAppId1401;
			guoPanSecurtKeyTemp = GuoPanSecurtKey1401;

		} else if (subPlatform.equals("z10070")) {
			guoPanAppIdTemp = GuoPanAppIdZ10070;
			guoPanSecurtKeyTemp = GuoPanSecurtKeyZ10070;

		} else if (subPlatform.equals("zj011guopan")) {
			guoPanAppIdTemp = GuoPanAppIdZj011guopan;
			guoPanSecurtKeyTemp = GuoPanSecurtKeyZj011guopan;

		}
		String now = String.valueOf(System.currentTimeMillis());
		StringBuilder signSource = new StringBuilder();
		signSource.append(gameUin).append(guoPanAppIdTemp).append(now).append(guoPanSecurtKeyTemp);
		String sign = MD5.encode(signSource.toString());
		log.debug("guopan|gameUin:{}|guoPanAppId:{}|token:{}", gameUin, guoPanAppIdTemp, token);

		String resp = HttpUtils.create(GuoPanVerifyUrl).addParam("game_uin", gameUin).addParam("appid", guoPanAppIdTemp)
				.addParam("token", token).addParam("t", now).addParam("sign", sign).get();
		if (log.isDebugEnabled()) {
			log.debug("resp: {}", resp);
		}

		LoginResponse lr = null;
		if ("true".equals(resp)) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = gameUin;
		} else {
			lr = new LoginResponse(1, resp);
		}
		return lr;
	}

}
