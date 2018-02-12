package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class QuickImpl implements PlatformInterface {

	static final Logger log = LoggerFactory.getLogger("login");

	String productCode = PTConfig.val("QuickProductCode");
	String productKey = PTConfig.val("QuickProductKey");
	String verifyUrl = PTConfig.val("QuickVerifyUrl");

	String[] params = new String[] { "token", "uid", "sdkChannelId" };

	@Override
	public LoginResponse login(Map<String, String> params) {

		String token = params.get("token");
		String uid = params.get("uid");
		String channelId = params.get("sdkChannelId");

		String resp = HttpUtils.create(verifyUrl).addParam("token", token).addParam("uid", uid)
				.addParam("product_code", productCode).post();
		log.debug("quick|resp:{}", resp);

		if (resp == null) {
			return LoginResponse.Timeout;
		}
		LoginResponse lr = null;
		if ("1".equals(resp)) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = channelId + uid;
            lr.userInfo.thirdUserId = uid;
		} else {
			log.error("quick|{}|{}", "失败", resp);
			lr = new LoginResponse(-1, resp);
		}
		return lr;
	}

	@Override
	public String ptFlag() {
		// TODO Auto-generated method stub
		return "quick";
	}

	@Override
	public String[] requireParams() {
		// TODO Auto-generated method stub
		return params;
	}

}
