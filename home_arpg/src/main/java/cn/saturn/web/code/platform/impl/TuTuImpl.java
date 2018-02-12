package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.impl.TapfunsImpl.Resp;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import xzj.core.util.MD5;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class TuTuImpl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	final String TutuAppKey = PTConfig.val("TutuAppKey");
	final String TutuVerifyUrl = PTConfig.val("TutuVerifyUrl");

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "tutu";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		log.debug("params: {}", params);

		String subPlatform = params.get("subPt");
		String openId = params.get("uid");
		String openKey = params.get("token");

		String keyMd5 = MD5.encode(TutuAppKey);
		String signStr = "www.feng.com" + openId + openKey + keyMd5;
		String sign = MD5.encode(signStr);

		String resp = HttpUtils.create(TutuVerifyUrl).addParam("r", "sk/user/verfyUserLogin")
				.addParam("open_id", openId).addParam("open_key", openKey).addParam("mapp_key", keyMd5)
				.addParam("verfy", sign).get();
		log.debug("tutu|resp: {}", resp);

		Resp rs = JSON.parseObject(resp, Resp.class);
		if (rs == null) {
			return LoginResponse.Timeout;
		}

		LoginResponse lr = null;
		if ("1".equals(rs.success)) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = openId;
		} else {
			lr = new LoginResponse(1, rs.info);
		}
		return lr;
	}

	static class Resp {
		String success;
		String info;

		public String getSuccess() {
			return success;
		}

		public void setSuccess(String success) {
			this.success = success;
		}

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}

	}

}
