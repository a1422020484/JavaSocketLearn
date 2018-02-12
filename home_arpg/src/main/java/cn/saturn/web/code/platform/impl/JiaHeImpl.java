package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.impl.TtImpl.Resp;
import cn.saturn.web.code.platform.impl.UCImpl.State;
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
public class JiaHeImpl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	final String jiaHeAppId = PTConfig.val("JiaHeAppId");;
	final String jiaHeLoginVerifyUrl = PTConfig.val("JiaHeLoginVerifyUrl");;

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "jiahe";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String uid = params.get("uid");
		String token = params.get("token");

		String resp = HttpUtils.create(jiaHeLoginVerifyUrl).addParam("v", "2").addParam("gact", "118")
				.addParam("userid", uid).addParam("usertoken", token).addParam("appid", jiaHeAppId).get();
		log.error("jiahe|{}", resp);

		Resp rs = JSON.parseObject(resp, Resp.class);
		if (rs == null) {
			return LoginResponse.Timeout;
		}

		LoginResponse lr = null;
		if (rs.getResult().equals("1")) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = uid;
		} else {
			log.error("jiahe|{}", rs.getDesc());
			lr = new LoginResponse(1, rs.getDesc());
		}
		return lr;
	}

	static class Resp {
		String result;
		String desc;

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
}
