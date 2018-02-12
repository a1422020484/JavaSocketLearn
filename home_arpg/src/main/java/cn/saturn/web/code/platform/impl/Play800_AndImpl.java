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
public class Play800_AndImpl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	final String play800AndVerifyUrl = PTConfig.val("play800AndVerifyUrl");

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "play800And";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String uid = params.get("uid");
		String data = params.get("token");
		String subPlatform = params.get("subPt");

		String play800SiteTemp = "";
		String play800KeyTemp = "";

		log.error("play800" + "|uid:{}|token:{}", uid, data);

		String resp = HttpUtils.create(play800AndVerifyUrl).addParam("data", data).post();

		log.error("play800" + "|verify resp:{}", resp);

		if (resp == null) {
			return LoginResponse.Timeout;
		}

		Resp rs = JSON.parseObject(resp, Resp.class);
		if (rs == null) {
			return LoginResponse.Timeout;
		}

		// 返回数据
		LoginResponse lr = null;
		if (rs.getResult()==0) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = uid;
		} else {
			log.error("play800" + "|{}|{}", 1, rs.result);
			lr = new LoginResponse(1, String.valueOf(rs.result));
		}
		return lr;
	}

	static class Resp {
		int result;
		Data data;

		public int getResult() {
			return result;
		}

		public void setResult(int result) {
			this.result = result;
		}

		public Data getData() {
			return data;
		}

		public void setData(Data data) {
			this.data = data;
		}
	}

	static class Data {
		int errorcode;
		String msg;
		
		public int isErrorcode() {
			return errorcode;
		}
		public void setErrorcode(int errorcode) {
			this.errorcode = errorcode;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}
