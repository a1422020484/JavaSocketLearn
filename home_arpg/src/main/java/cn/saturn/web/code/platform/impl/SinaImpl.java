package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.PlatformUtils;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 新浪
 *
 * @author zhuangyuetao
 */
@Component
public class SinaImpl implements PlatformInterface {
	public static final String flag = "sina";
	public static final Logger log = LoggerFactory.getLogger("login");
	public static final String[] params = new String[] { "subPt", "uid", "token" };

	public static final String appId = PTConfig.val("SinaAppId"); // client_Id
	public static final String appKey = PTConfig.val("SinaAppKey"); // client_secret
	public static final String signatureKey = PTConfig.val("SinaSignatureKey");
	public static final String url = PTConfig.val("SinaVerifyUrl");

	@Override
	public LoginResponse login(Map<String, String> params) {

		String suid = params.get("uid");
		String[] strs = params.get("token").split("\\|");
		String deviceid = strs[1];
		String token = strs[0];

		// 生成签名
		Map<String, String> signParams = new HashMap<>();
		signParams.put("token", token);
		signParams.put("appkey", appKey);
		signParams.put("suid", suid);
		signParams.put("deviceid", deviceid);

		String sign = null;
		if (signParams != null) {
			StringBuilder sValue = new StringBuilder();
			Object[] keys = signParams.keySet().toArray();
			Arrays.sort(keys);
			String temp = null;
			for (Object key : keys) {
				sValue.append(key).append("=");
				temp = signParams.get(key);
				if (temp == null) {
					sValue.append("").append("&");
				} else {
					sValue.append(temp).append("&");
				}
			}
			if (sValue.length() > 0) {
				sValue.deleteCharAt(sValue.length() - 1);
				sValue.append("|").append(signatureKey);
			}
			sign = MD5.encode(sValue.toString());
			log.debug(flag + "|signStr:{}|sign:{}", sValue.toString(), sign);
		}

		// String signStr = "appkey=" + appKey + "&" + "deviceid=" + deviceid +
		// "&" + "suid=" + suid + "&" + "token="
		// + token + "|" + signatureKey;
		// String sign = MD5.encode(signStr);
		// log.error(flag + "|sign:{}", sign);

		// 请求
		String resp = HttpUtils.create(url).addParam("suid", suid).addParam("deviceid", deviceid)
				.addParam("appkey", appKey).addParam("token", token).addParam("signature", sign).post();
		Resp rs = JSON.parseObject(resp, Resp.class);
		if (rs == null) {
			return LoginResponse.Timeout;
		}

		// 返回数据
		LoginResponse lr = null;
		if (StringUtils.isBlank(rs.error_code)) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = rs.suid;
		} else {
			log.error(flag + "|{}|{}", 0, resp);
			lr = new LoginResponse(1, resp);
		}
		return lr;
	}

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return flag;
	}

	public static class Resp {
		protected String token;
		protected String suid;
		protected String usertype;
		protected String request;
		protected String error_code;
		protected String error;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getSuid() {
			return suid;
		}

		public void setSuid(String suid) {
			this.suid = suid;
		}

		public String getUsertype() {
			return usertype;
		}

		public void setUsertype(String usertype) {
			this.usertype = usertype;
		}

		public String getRequest() {
			return request;
		}

		public void setRequest(String request) {
			this.request = request;
		}

		public String getError_code() {
			return error_code;
		}

		public void setError_code(String error_code) {
			this.error_code = error_code;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

	}

	// public static void main(String[] args) {
	// String tokenKey = "a43cd7a510fc3b06792a0cb509b58415";
	// String sign = MD5.encode(appKey + tokenKey);
	//
	// System.out.println(sign);
	//
	// }

}
