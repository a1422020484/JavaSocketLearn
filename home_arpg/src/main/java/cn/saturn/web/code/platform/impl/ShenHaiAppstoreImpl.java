package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.impl.UCImpl.State;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zzz
 */
@Component
public class ShenHaiAppstoreImpl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	// final String tapfunsAndTFGameCode = PTConfig.val("TapfunsAndTFGameCode");

	final String url = "http://sdk.diaigame.com/user/validate";

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "shenhai-appstore";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String uid = params.get("uid");
		String tokenId = params.get("token");

		String resp = HttpUtils.create(url).addParam("tokenid", tokenId).post();
		log.debug("shenhai-appstore|{}", resp);

		Resp rs = JSON.parseObject(resp, Resp.class);
		if (rs == null) {
			return LoginResponse.Timeout;
		}

		LoginResponse lr = null;
		if (rs.ret == 1) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = rs.data.uid;
		} else {
			log.error("shenhai-appstore|{}|{}", rs.ret, rs.msg);
			lr = new LoginResponse(1, rs.msg);
		}
		return lr;
	}

	static class Resp {
		int ret;
		String msg;
		Data data;

		public int getRet() {
			return ret;
		}

		public void setRet(int ret) {
			this.ret = ret;
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
		String uid;

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}
	}
}
