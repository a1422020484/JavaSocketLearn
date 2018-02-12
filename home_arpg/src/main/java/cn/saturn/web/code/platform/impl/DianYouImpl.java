package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zzz
 */
@Component
public class DianYouImpl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	final String url = "http://cpa.idianyou.cn/cpa_center/user/check.do";

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "dianyou";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String uid = params.get("uid");
		String certificate = params.get("token");

		String resp = HttpUtils.create(url).addParam("userid", uid).addParam("userCertificate", certificate).post();
		log.error("dianyou|{}", resp);

		if (resp == null || resp == "") {
			return LoginResponse.Timeout;
		}

		LoginResponse lr = null;
		if (resp.equals("true")) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = uid;
		} else {
			log.error("dianyou|{}", resp);
			lr = new LoginResponse(1, resp);
		}
		return lr;
	}
}
