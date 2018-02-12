package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import net.paoding.rose.web.Invocation;
import xzj.core.util.MD5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class XYKaiYingIOSImpl implements PlatformInterface {

	static final Logger log = LoggerFactory.getLogger("login");

	static final String flag = "xyios";

	static final String XYIOSAppId = PTConfig.val("XYIOSAppId");
	static final String XYIOSAppKey = PTConfig.val("XYIOSAppKey");
	static final String XYIOSVerifyUrl = PTConfig.val("XYIOSVerifyUrl");

	@Autowired
	Invocation inv;

	static final String[] params = new String[] { "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return flag;
	}

	@Override
	public LoginResponse login(Map<String, String> params) {
		String uid = params.get("uid");
		String token = params.get("token");

		String signStr = "appid=" + XYIOSAppId + "&token=" + token + "&uid=" + uid;
		String sign = MD5.encode(XYIOSAppKey + signStr);

        String resp = HttpUtils.create(XYIOSVerifyUrl)
                .addParam("uid", uid)
                .addParam("appid", XYIOSAppId)
                .addParam("token", token)
                .addParam("sign", sign)
                .post();
        
		log.info("xyios|uid:{}|token:{}|appid:{}|resp:{}", uid, token, XYIOSAppId, resp);

		Resp rs = null;
		try {
			rs = JSON.parseObject(resp, Resp.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rs == null) {
			return LoginResponse.Timeout;
		}

		if (!"200".equals(rs.code)) {
			return new LoginResponse(1, rs.msg);
		}

		LoginResponse lr = new LoginResponse();
		lr.userInfo = new UserInfo();
		lr.userInfo.account = uid;

		return lr;
	}

	static class Resp {
		String code;
		String msg;
		String data;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

	}

}
