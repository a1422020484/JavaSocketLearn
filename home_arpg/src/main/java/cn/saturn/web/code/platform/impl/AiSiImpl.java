package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.impl.AnzhiImpl.Resp;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @author zzz
 */
@Component
public class AiSiImpl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	final String url = "https://pay.i4.cn/member_third.action";

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "aisi";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String uid = params.get("uid");
		String token = params.get("token");

		String resp = HttpUtils.create(url).addParam("token", token).post();
		log.error("aisi|{}", resp);

		if (resp == null || resp == "") {
			return LoginResponse.Timeout;
		}

		Resp rs = null;
		try {
			rs = JSON.parseObject(resp, Resp.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rs == null) {
			return LoginResponse.Timeout;
		}

		LoginResponse lr = null;
		if (rs.status == 0) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = String.valueOf(rs.userid);
		} else {
			log.error("aisi|{}", rs.status);
			lr = new LoginResponse(1, String.valueOf(rs.status));
		}
		return lr;
	}

	static class Resp {
		int status;
		String username;
		long userid;

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public long getUserid() {
			return userid;
		}

		public void setUserid(long userid) {
			this.userid = userid;
		}
	}
}
