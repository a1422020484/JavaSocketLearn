package cn.saturn.web.code.platform.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import sun.misc.BASE64Encoder;

/**
 * 多玩我的世界盒子
 *
 * @author zhuangyuetao
 */
@Component
public class HeziImpl implements PlatformInterface {
	public static final String flag = "login";
	static final Logger log = LoggerFactory.getLogger(flag);

	// 口袋2安卓
	final String HeziGameId = PTConfig.val("HeziGameId");
	final String HeziGameKey = PTConfig.val("HeziGameKey");
	final String HeziPackageName = PTConfig.val("HeziPackageName");
	final String HeziVerifyUrl = PTConfig.val("HeziVerifyUrl");

	// String[] params = new String[] { "gameid", "uid", "token" };
	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "mcbox";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String subPlatform = params.get("subPt");
		String uid = params.get("uid");
		String token = params.get("token");

		// 请求
		String resp = HttpUtils.create(HeziVerifyUrl).addParam("userKey", token)// 登录获取的token
				.addParam("packageName", HeziPackageName).post();
		log.debug("mcbox|resp:{}|uid:{}", resp, uid);
		try {
			Resp rs = JSON.parseObject(resp, Resp.class);
			if (rs == null)
				return LoginResponse.Timeout;
			LoginResponse lr = null;
			if (rs.status == 200) {
				lr = new LoginResponse();
				lr.userInfo = new UserInfo();
				lr.userInfo.account = uid;

			} else {
				log.error("mcbox|{}|{}", rs.status, rs.getMsg());
				lr = new LoginResponse(1, rs.getMsg());
			}
			return lr;
		} catch (Exception e) {
			log.error("盒子  解析登录返回消息异常");
			e.printStackTrace();
		}
		return null;
	}

	public static String encodeBASE64(byte[] key) {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	public static byte[] digestMD5(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		return md5.digest();
	}

	static class Resp {
		int status;
		String msg;
		Data data;

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
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
		User user;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

	}

	static class User {

		String userId;
		String gameId;
		String nickName;
		String avatarUrl;

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getGameId() {
			return gameId;
		}

		public void setGameId(String gameId) {
			this.gameId = gameId;
		}

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public String getAvatarUrl() {
			return avatarUrl;
		}

		public void setAvatarUrl(String avatarUrl) {
			this.avatarUrl = avatarUrl;
		}

	}
}