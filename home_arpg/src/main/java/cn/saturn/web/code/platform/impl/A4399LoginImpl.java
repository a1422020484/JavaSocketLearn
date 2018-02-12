package cn.saturn.web.code.platform.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PlatformInterface;

/**
 * 4399登录
 *
 * @author z
 */
@Component
public class A4399LoginImpl implements PlatformInterface {
	public static final String flag = "4399-login";
	static final Logger log = LoggerFactory.getLogger(flag);

	String appkey = "114274";

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "4399-login";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {
		// String subPlatform = params.get("subPt");
		// String token = params.get("token");
		String uid = params.get("uid");

		LoginResponse lr = null;
		if (!uid.equals("")) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = uid;
		}
		return lr;
	}
}