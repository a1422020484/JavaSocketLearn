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

import java.util.Map;

/**
 * 狐狸平台
 *
 * @author zhuangyuetao
 */
@Component
public class VivoKd2Impl implements PlatformInterface {
	public static final String flag = "vivo";
	public static final Logger log = LoggerFactory.getLogger("login");
	public static final String[] params = new String[] { "access_token" };

	public static final String VivoAppId = PTConfig.val("VivoAppId");
	public static final String VivoCpId = PTConfig.val("VivoCpId");
	public static final String VivoCpKey = PTConfig.val("VivoCpKey");
	public static final String VivoVerifyUrl = PTConfig.val("VivoVerifyUrl");

	/**
	 *
	 */
	@Override
	public LoginResponse login(Map<String, String> params) {
		String access_token = params.get("access_token");
		log.debug("vivo|access_token:{}|VivoAppId:{}|VivoCpId:{}|VivoCpKey:{}|VivoVerifyUrl:{}", access_token,
				VivoAppId, VivoCpId, VivoCpKey, VivoVerifyUrl);

		// 请求
		String resp = HttpUtils.create(VivoVerifyUrl).addParam("authtoken", access_token).post();
		log.debug("vivo|resp:{}", resp);
		Resp rs = null;
		try {
			rs = JSON.parseObject(resp, Resp.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rs == null) {
			return LoginResponse.Timeout;
		}

		LoginResponse lr = new LoginResponse();
		if (rs.retcode == 0) {
			// 返回数据
			lr.userInfo = new UserInfo();
			lr.userInfo.account = rs.data.openid;
		} else {
			return LoginResponse.ParamError;
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
		int retcode;
		Data data;

		public int getRetcode() {
			return retcode;
		}

		public void setRetcode(int retcode) {
			this.retcode = retcode;
		}

		public Data getData() {
			return data;
		}

		public void setData(Data data) {
			this.data = data;
		}

		protected static class Data {
			boolean success;
			String openid;

			public boolean isSuccess() {
				return success;
			}

			public void setSuccess(boolean success) {
				this.success = success;
			}

			public String getOpenid() {
				return openid;
			}

			public void setOpenid(String openid) {
				this.openid = openid;
			}
		}

	}

}
