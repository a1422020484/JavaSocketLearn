package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.PlatformUtils.StringUtil;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.util.Map;

@Component
public class KaiXuanIosImpl implements PlatformInterface {

	static final Logger log = LoggerFactory.getLogger("kaixuan_appstore");

	final String appId = PTConfig.val("KaixuanIosAppId");
	final String appKey = PTConfig.val("KaixuanIosAppKey");
	final String appSecret = PTConfig.val("KaixuanIosAppSecret");
	final String verifyUrl = PTConfig.val("KaixuanIosVerifyUrl");

	final String appId0 = PTConfig.val("KaixuanAppId");
	final String appKey0 = PTConfig.val("KaixuanAppKey");
	final String appSecret0 = PTConfig.val("KaixuanAppSecret");
	final String verifyUrl0 = PTConfig.val("KaixuanVerifyUrl");

	final String appId1 = PTConfig.val("KaixuanIosAppId1");
	final String appKey1 = PTConfig.val("KaixuanIosAppKey1");
	final String appSecret1 = PTConfig.val("KaixuanIosAppSecret1");
	final String verifyUrl1 = PTConfig.val("KaixuanIosVerifyUrl1");

	final String appId_10035 = PTConfig.val("KaixuanAppId_10035");
	final String appKey_10035 = PTConfig.val("KaixuanAppKey_10035");
	final String appSecret_10035 = PTConfig.val("KaixuanAppSecret_10035");
	final String verifyUrl_10035 = PTConfig.val("KaixuanIosVerifyUrl");

	final String appId_10034 = PTConfig.val("KaixuanAppId_10034");
	final String appKey_10034 = PTConfig.val("KaixuanAppKey_10034");
	final String appSecret_10034 = PTConfig.val("KaixuanAppSecret_10034");
	final String verifyUrl_10034 = PTConfig.val("KaixuanIosVerifyUrl");

	final String appId_10037 = PTConfig.val("KaixuanAppId_10037");
	final String appKey_10037 = PTConfig.val("KaixuanAppKey_10037");
	final String appSecret_10037 = PTConfig.val("KaixuanAppSecret_10037");
	final String verifyUrl_10037 = PTConfig.val("KaixuanIosVerifyUrl");

	final String appId_10038 = PTConfig.val("KaixuanAppId_10038");
	final String appKey_10038 = PTConfig.val("KaixuanAppKey_10038");
	final String appSecret_10038 = PTConfig.val("KaixuanAppSecret_10038");
	final String verifyUrl_10038 = PTConfig.val("KaixuanIosVerifyUrl");

	final String appId_100402 = PTConfig.val("KaixuanAppId_100402");
	final String appKey_100402 = PTConfig.val("KaixuanAppKey_100402");
	final String appSecret_100402 = PTConfig.val("KaixuanAppSecret_100402");
	final String verifyUrl_100402 = PTConfig.val("KaixuanIosVerifyUrl");

	final String appId_10045 = PTConfig.val("KaixuanAppId_10045");
	final String appKey_10045 = PTConfig.val("KaixuanAppKey_10045");
	final String appSecret_10045 = PTConfig.val("KaixuanAppSecret_10045");

	final String appId_10046 = PTConfig.val("KaixuanAppId_10046");
	final String appKey_10046 = PTConfig.val("KaixuanAppKey_10046");
	final String appSecret_10046 = PTConfig.val("KaixuanAppSecret_10046");

	final String appId_10047 = PTConfig.val("KaixuanAppId_10047");
	final String appKey_10047 = PTConfig.val("KaixuanAppKey_10047");
	final String appSecret_10047 = PTConfig.val("KaixuanAppSecret_10047");

	final String appId_10048 = PTConfig.val("KaixuanAppId_10048");
	final String appKey_10048 = PTConfig.val("KaixuanAppKey_10048");
	final String appSecret_10048 = PTConfig.val("KaixuanAppSecret_10048");

	final String appId_10049 = PTConfig.val("KaixuanAppId_10049");
	final String appKey_10049 = PTConfig.val("KaixuanAppKey_10049");
	final String appSecret_10049 = PTConfig.val("KaixuanAppSecret_10049");

	String[] params = new String[] { "token" };

	@Override
	public LoginResponse login(Map<String, String> params) {
		// TODO Auto-generated method stub
		String token = params.get("token");

		String appIdTemp = appId;
		String appKeyTemp = appKey;
		String appSecretTemp = appSecret;
		String verifyUrlTemp = verifyUrl;
		String subPlatform = params.get("subPt");
		String appId = params.get("appId");

		// log.debug("rodking_ios (1) {} {} {} {}
		// {}",appIdTemp,appKeyTemp,appSecretTemp,verifyUrlTemp,subPlatform);
		// log.debug("rodking_ios (2) {}", JSON.toJSONString(params));

		if (subPlatform != null && (subPlatform.equals("5005") || subPlatform.equals("5015"))) {
			appIdTemp = appId0;
			appKeyTemp = appKey0;
			appSecretTemp = appSecret0;
			verifyUrlTemp = verifyUrl0;
		} else if (!StringUtil.isNullOrEmpty(appId) && appId.equals("10026")) {
			appIdTemp = appId1;
			appKeyTemp = appKey1;
			appSecretTemp = appSecret1;
			verifyUrlTemp = verifyUrl1;
		} else if (!StringUtil.isNullOrEmpty(appId) && appId.equals("10035")) {
			appIdTemp = appId_10035;
			appKeyTemp = appKey_10035;
			appSecretTemp = appSecret_10035;
			verifyUrlTemp = verifyUrl_10035;
		} else if (subPlatform != null && subPlatform.equals("5013001")) {
			appIdTemp = appId_10035;
			appKeyTemp = appKey_10035;
			appSecretTemp = appSecret_10035;
			verifyUrlTemp = verifyUrl_10035;
		} else if (subPlatform != null && subPlatform.equals("5013002")) {
			appIdTemp = appId_10034;
			appKeyTemp = appKey_10034;
			appSecretTemp = appSecret_10034;
			verifyUrlTemp = verifyUrl_10034;
		} else if (subPlatform != null && subPlatform.equals("5013003")) {
			appIdTemp = appId_10037;
			appKeyTemp = appKey_10037;
			appSecretTemp = appSecret_10037;
			verifyUrlTemp = verifyUrl_10037;
		} else if (subPlatform != null && subPlatform.equals("5013004")) {
			appIdTemp = appId_10038;
			appKeyTemp = appKey_10038;
			appSecretTemp = appSecret_10038;
			verifyUrlTemp = verifyUrl_10038;
		} else if (subPlatform != null && subPlatform.equals("5013005")) {
			appIdTemp = appId_100402;
			appKeyTemp = appKey_100402;
			appSecretTemp = appSecret_100402;
			verifyUrlTemp = verifyUrl_100402;
		} else if (subPlatform != null && subPlatform.equals("5013006")) {
			appIdTemp = appId_10045;
			appKeyTemp = appKey_10045;
			appSecretTemp = appSecret_10045;
		} else if (subPlatform != null && subPlatform.equals("5013007")) {
			appIdTemp = appId_10046;
			appKeyTemp = appKey_10046;
			appSecretTemp = appSecret_10046;
		} else if (subPlatform != null && subPlatform.equals("5013008")) {
			appIdTemp = appId_10047;
			appKeyTemp = appKey_10047;
			appSecretTemp = appSecret_10047;
		} else if (subPlatform != null && subPlatform.equals("5013009")) {
			appIdTemp = appId_10048;
			appKeyTemp = appKey_10048;
			appSecretTemp = appSecret_10048;
		} else if (subPlatform != null && subPlatform.equals("5013010")) {
			appIdTemp = appId_10049;
			appKeyTemp = appKey_10049;
			appSecretTemp = appSecret_10049;
		}

		// log.debug("rodking_ios (3) {} {} {} {} {}",
		// appIdTemp,appKeyTemp,appSecretTemp,verifyUrlTemp,subPlatform);
		String sign = MD5.encode(token + appKeyTemp + appSecretTemp);
		String resp = HttpUtils.create(verifyUrlTemp).addParam("appKey", appKeyTemp).addParam("token", token)
				.addParam("sign", sign).post();
		// log.debug("rodking_ios (4) {} {} {} {}",
		// sign,token,appKeyTemp,appSecretTemp);

		// resp = resp.replace("\"data\":[]", "\"data\":{}"); // 空字符处理
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
		return "kaixuan_appstore";
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
