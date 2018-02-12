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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pp助手
 */
@Component
public class PPImpl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	String pPGameId = PTConfig.val("PPGameId");
	String pPAppKey = PTConfig.val("PPAppKey");
	String pPLoginVerifyUrl = PTConfig.val("PPLoginVerifyUrl");

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "pp";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {
		String sid = params.get("token");

		Map<String, String> reqdata = new HashMap<>();
		reqdata.put("sid", sid);

		Map<String, Object> gamedata = new HashMap<>();
		gamedata.put("gameId", Integer.parseInt(pPGameId));

		String sig = MD5.encode("sid=" + sid + pPAppKey);

		Map<String, Object> p = new HashMap<>();
		p.put("id", System.currentTimeMillis());
		p.put("service", "account.verifySession");
		p.put("data", reqdata);
		p.put("game", gamedata);
		p.put("encrypt", "MD5");
		p.put("sign", sig);

		String content = null;
		// log.info("pp|gameId:{}|verifyUrl:{}|param:{}", pPGameId,
		// pPLoginVerifyUrl, JSON.toJSONString(p));
		try {
			content = HttpUtils.create(pPLoginVerifyUrl).post(JSON.toJSONString(p).getBytes("utf-8"));
			log.info("pp|content:{}", content);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if (content == null) {
			return LoginResponse.Timeout;
		}
		Resp resp = null;
		try {
			resp = JSON.parseObject(content, Resp.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp == null) {
			return LoginResponse.Timeout;
		}

		LoginResponse lr = null;
		if (resp.state.code == 1) {
			// Data data = JSON.parseObject(resp.data, Data.class);
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = resp.data.creator + resp.data.accountId;
		} else {
			lr = new LoginResponse(1, resp.state.msg);
		}
		return lr;
	}

	static class Resp {
		String id;
		State state;
		Data data;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

		public Data getData() {
			return data;
		}

		public void setData(Data data) {
			this.data = data;
		}

	}

	static class Data {
		String accountId;
		String nickName;
		String creator;

		public String getAccountId() {
			return accountId;
		}

		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}

		public String getCreator() {
			return creator;
		}

		public void setCreator(String creator) {
			this.creator = creator;
		}

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
	}

	static class State {
		int code;
		String msg;

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
	}
}
