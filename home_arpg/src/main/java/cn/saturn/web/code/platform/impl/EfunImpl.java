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
public class EfunImpl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	final String efunAppKey = PTConfig.val("EfunAppKey");

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "efun";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String uid = params.get("uid");
		String str = params.get("token");
		String[] strs = str.split("\\|");
		log.debug("efun|{}", str);
		String timeStamp = strs[0];
		String sign = strs[1];

		String mySign = MD5.encode(efunAppKey + uid + timeStamp);

		log.debug("efun|{}", mySign);

		LoginResponse lr = null;
		if (mySign.equalsIgnoreCase(sign)) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = uid;
		} else {
			log.error("efun|{}", "sign error");
			lr = new LoginResponse(1, "sign error");
		}

		return lr;
	}
}
