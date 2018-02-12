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
public class Play800Impl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	final String play800Site6001 = PTConfig.val("play800Site6001");
	final String play800Key6001 = PTConfig.val("play800Key6001");
	
	final String play800Site6002 = PTConfig.val("play800Site6002");
	final String play800Key6002 = PTConfig.val("play800Key6002");
	
	final String play800Site6003 = PTConfig.val("play800Site6003");
	final String play800Key6003 = PTConfig.val("play800Key6003");

	final String play800Site7010 = PTConfig.val("play800Site7010");
	final String play800Key7010 = PTConfig.val("play800Key7010");
	final String play800VerifyUrl = PTConfig.val("play800VerifyUrl");

	final String play800Site7011 = PTConfig.val("play800Site7011");
	final String play800Key7011 = PTConfig.val("play800Key7011");

	final String play800Site7012 = PTConfig.val("play800Site7012");
	final String play800Key7012 = PTConfig.val("play800Key7012");

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "wan20x40";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String uid = params.get("uid");
		String sessionId = params.get("token");
		String subPlatform = params.get("subPt");

		String play800SiteTemp = "";
		String play800KeyTemp = "";

		if (subPlatform != null && subPlatform.equals("6001")) {
			play800SiteTemp = play800Site6001;
			play800KeyTemp = play800Key6001;

		} else if (subPlatform != null && subPlatform.equals("6002")) {
			play800SiteTemp = play800Site6002;
			play800KeyTemp = play800Key6002;

		} else if (subPlatform != null && subPlatform.equals("6003")) {
			play800SiteTemp = play800Site6003;
			play800KeyTemp = play800Key6003;

		} else if (subPlatform != null && subPlatform.equals("7010")) {
			play800SiteTemp = play800Site7010;
			play800KeyTemp = play800Key7010;

		} else if (subPlatform != null && subPlatform.equals("7011")) {
			play800SiteTemp = play800Site7011;
			play800KeyTemp = play800Key7011;

		} else if (subPlatform != null && subPlatform.equals("7012")) {
			play800SiteTemp = play800Site7012;
			play800KeyTemp = play800Key7012;

		}

		String time = String.valueOf(System.currentTimeMillis());
		String sig = MD5.encode(play800KeyTemp + "WX" + play800SiteTemp + "WX" + time + time);

		log.error("play800" + "|uid:{}|sessionId:{}|time:{}|sig:{}|site:{}|key:{}", uid, sessionId, time, sig, play800SiteTemp, play800KeyTemp);

		String resp = HttpUtils.create(play800VerifyUrl).addParam("site", play800SiteTemp).addParam("time", time)
				.addParam("uid", uid).addParam("sessionid", sessionId).addParam("sign", sig).post();

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
		if (rs.data.isStatus()) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = rs.data.getUid();
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
		boolean status;
		String uid;

		public boolean isStatus() {
			return status;
		}

		public void setStatus(boolean status) {
			this.status = status;
		}

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}
	}
}
