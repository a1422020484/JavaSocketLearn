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
 * 值尚
 *
 * @author zzz
 */
@Component
public class ZhiShangImpl implements PlatformInterface {
	public static final String flag = "login";
	public static final Logger log = LoggerFactory.getLogger(flag);

	public static final String[] params = new String[] { "subPt", "uid", "token" };

	public static final String zhiShangGameId = PTConfig.val("ZhiShangGameId");
	public static final String zhiShangGameKey = PTConfig.val("ZhiShangGameKey");
	public static final String zhiShangGameSecret = PTConfig.val("ZhiShangGameSecret");
	public static final String url = PTConfig.val("ZhiShangLoginVerifyUrl");

	@Override
	public LoginResponse login(Map<String, String> params) {

		String accountId = params.get("uid");
		String sessionId = params.get("token");

		// 签名
		String sign0 = "accountid=" + accountId + "&gameid=" + zhiShangGameId + "&sessionid=" + sessionId
				+ zhiShangGameSecret;
		String sign = MD5.encode(sign0);

		// 请求
		String resp = HttpUtils.create(url).addParam("accountid", accountId).addParam("gameid", zhiShangGameId)
				.addParam("sessionid", sessionId).addParam("sign", sign).post();

		// log.error("zhishang|resp:{}", resp);

		Resp rs = JSON.parseObject(resp, Resp.class);
		if (rs == null) {
			return LoginResponse.Timeout;
		}

		// 返回数据
		LoginResponse lr = null;
		if (rs.code.equals("0")) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = accountId;
		} else {
			log.error(flag + "|{}|{}", rs.code, rs.msg);
			lr = new LoginResponse(1, rs.msg);
		}
		return lr;
	}

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "zhishang";
	}

	public static class Resp {
		protected String code;
		protected String msg;

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

	}
}
