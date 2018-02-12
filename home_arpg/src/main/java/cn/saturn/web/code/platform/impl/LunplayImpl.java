package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.util.Map;

/**
 * @author zzz
 */
@Component
public class LunplayImpl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	final String lunplayAndKey1 = PTConfig.val("LunplayAndKey1");
	final String lunplayIosKey1 = PTConfig.val("LunplayIosKey1");

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "lunplay";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String lunplayKey = "";

		String passport = params.get("uid");
		String str = params.get("token");
		String[] strs = str.split("\\|");
		log.debug("lunplay|{}", str);
		String t = strs[0];
		String sitecode = strs[1];
		String ck = strs[2];

		String subPlatform = params.get("subPt");
		if (subPlatform != null && subPlatform.equals("7051")) {
			lunplayKey = lunplayAndKey1;
		} else if (subPlatform != null && subPlatform.equals("7071")) {
			lunplayKey = lunplayIosKey1;
		}

		String mySign = MD5.encode(sitecode + t + lunplayKey + passport);

		log.debug("lunplay|mySign:{}", mySign);

		LoginResponse lr = null;
		if (mySign.equalsIgnoreCase(ck)) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = passport;
		} else {
			log.error("lunplay|{}", "sign error");
			lr = new LoginResponse(1, "sign error");
		}

		return lr;
	}
}
