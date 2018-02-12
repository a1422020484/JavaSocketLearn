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

/**
 * @author zzz
 */
@Component
public class GanPu_ShuMaImpl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	final String ganPuAppId = PTConfig.val("GanPuAppId");
	final String ganPuAppKey = PTConfig.val("GanPuAppKey");
	final String ganPuVerifyUrl = PTConfig.val("GanPuVerifyUrl");

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "ganpu";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String userId = params.get("uid");
		String sessionKey = params.get("token");

		String resp = HttpUtils.create(ganPuVerifyUrl).addParam("appId", ganPuAppId).addParam("appkey", ganPuAppKey)
				.addParam("userId", userId).addParam("sessionKey", sessionKey).post();

		log.debug("ganpu" + "|verify resp:{}", resp);

		Resp rs = JSON.parseObject(resp, Resp.class);
		if (rs == null) {
			return LoginResponse.Timeout;
		}

		// 返回数据
		LoginResponse lr = null;
		if (rs.code == 100000) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = userId;
		} else {
			log.error("ganpu" + "|{}|{}", 1, rs.msg);
			lr = new LoginResponse(1, rs.msg);
		}
		return lr;
	}

	static class Resp {

		int code;
		String msg;
		Data data;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public Data getData() {
			return data;
		}

		public void setData(Data data) {
			this.data = data;
		}
	}

	static class Data {

		int userId;
		String sessionKey;

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getSessionKey() {
			return sessionKey;
		}

		public void setSessionKey(String sessionKey) {
			this.sessionKey = sessionKey;
		}
	}
}
