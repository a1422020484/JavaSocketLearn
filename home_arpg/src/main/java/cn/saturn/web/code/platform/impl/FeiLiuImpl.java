package cn.saturn.web.code.platform.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PlatformInterface;

/**
 * 飞流
 *
 * @author z
 */
@Component
public class FeiLiuImpl implements PlatformInterface {
	public static final String flag = "login";
	static final Logger log = LoggerFactory.getLogger(flag);

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "feiliu";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {
		// String subPlatform = params.get("subPt");
		// String token = params.get("token");
		String uid = params.get("uid");
		
		log.debug("params: {}| uid:{}", params, uid);

		LoginResponse lr = null;
		if (!uid.equals("")) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = uid;
		}
		return lr;
	}
}