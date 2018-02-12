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
import xzj.core.util.MD5;

import java.util.Map;

@Component
public class KaiXuanAppstoreJuHeImpl implements PlatformInterface {

	static final Logger log = LoggerFactory.getLogger("login");

	final String kaiXuanAppstoreJuHeAppId10073 = PTConfig.val("KaiXuanAppstoreJuHeAppId10073");
	final String kaiXuanAppstoreJuHeAppKey10073 = PTConfig.val("KaiXuanAppstoreJuHeAppKey10073");
	final String kaiXuanAppstoreJuHeAppSecret10073 = PTConfig.val("KaiXuanAppstoreJuHeAppSecret10073");

	final String kaiXuanAppstoreJuHeAppId10074 = PTConfig.val("KaiXuanAppstoreJuHeAppId10074");
	final String kaiXuanAppstoreJuHeAppKey10074 = PTConfig.val("KaiXuanAppstoreJuHeAppKey10074");
	final String kaiXuanAppstoreJuHeAppSecret10074 = PTConfig.val("KaiXuanAppstoreJuHeAppSecret10074");

	final String kaiXuanAppstoreJuHeAppId10075 = PTConfig.val("KaiXuanAppstoreJuHeAppId10075");
	final String kaiXuanAppstoreJuHeAppKey10075 = PTConfig.val("KaiXuanAppstoreJuHeAppKey10075");
	final String kaiXuanAppstoreJuHeAppSecret10075 = PTConfig.val("KaiXuanAppstoreJuHeAppSecret10075");

	final String verifyUrl = PTConfig.val("KaiXuanAppstoreJuHeVerifyUrl");

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public LoginResponse login(Map<String, String> params) {

		String appId = kaiXuanAppstoreJuHeAppId10073;
		String appKey = kaiXuanAppstoreJuHeAppKey10073;
		String appSecret = kaiXuanAppstoreJuHeAppSecret10073;

		String token = params.get("token");
		String subPlatform = params.get("subPt");

		if (subPlatform != null && subPlatform.equals("10073")) {
			appId = kaiXuanAppstoreJuHeAppId10073;
			appKey = kaiXuanAppstoreJuHeAppKey10073;
			appSecret = kaiXuanAppstoreJuHeAppSecret10073;

		} else if (subPlatform != null && subPlatform.equals("10074")) {
			appId = kaiXuanAppstoreJuHeAppId10074;
			appKey = kaiXuanAppstoreJuHeAppKey10074;
			appSecret = kaiXuanAppstoreJuHeAppSecret10074;

		} else if (subPlatform != null && subPlatform.equals("10075")) {
			appId = kaiXuanAppstoreJuHeAppId10075;
			appKey = kaiXuanAppstoreJuHeAppKey10075;
			appSecret = kaiXuanAppstoreJuHeAppSecret10075;
		}

		String sign = MD5.encode(token + appKey + appSecret);
		String resp = HttpUtils.create(verifyUrl).addParam("appKey", appKey).addParam("token", token)
				.addParam("sign", sign).post();
		log.error("resp:{} ", resp);
		Resp rs = JSON.parseObject(resp, Resp.class);

		if (rs == null) {
			return LoginResponse.Timeout;
		}
		LoginResponse lr = null;
		if ("200".equals(rs.code)) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = rs.data.getUserId();
			lr.userInfo.thirdUserId = rs.data.thirdId;
		} else {
			log.error("凯旋|{}|{}", rs.code, rs.error_msg);
			lr = new LoginResponse(Integer.parseInt(rs.code), rs.error_msg);
		}
		return lr;
	}

	@Override
	public String ptFlag() {
		// TODO Auto-generated method stub
		return "kaixuan_appstore_juhe";
	}

	@Override
	public String[] requireParams() {
		// TODO Auto-generated method stub
		return params;
	}

	static class Resp {
		String code;
		Data data;
		String error_msg;

		public static class Data {
			String userId;
			String thirdId;

			public String getUserId() {
				return userId;
			}

			public void setUserId(String userId) {
				this.userId = userId;
			}

			public String getThirdId() {
				return thirdId;
			}

			public void setThirdId(String thirdId) {
				this.thirdId = thirdId;
			}
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public Data getData() {
			return data;
		}

		public void setData(Data data) {
			this.data = data;
		}

		public String getError_msg() {
			return error_msg;
		}

		public void setError_msg(String error_msg) {
			this.error_msg = error_msg;
		}

	}

}
