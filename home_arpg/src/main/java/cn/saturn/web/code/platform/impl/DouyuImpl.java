package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.Base64;
import cn.saturn.web.code.platform.utils.HttpUtils;
import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 益玩平台
 *
 * @author liyanmeng
 */
@Component
public class DouyuImpl implements PlatformInterface {
	public static final String flag = "douyu";
	static final Logger log = LoggerFactory.getLogger("login");

	// 口袋2安卓
	final String DouyuOauth2id = PTConfig.val("DouyuOauth2id");
	final String DouyuSecret = PTConfig.val("DouyuSecret");
	final String DouyuSdkVersion = PTConfig.val("DouyuSdkVersion");
	final String DouyuVerifyUrl = PTConfig.val("DouyuVerifyUrl");

	// String[] params = new String[] { "gameid", "uid", "token" };
	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "douyu";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String subPlatform = params.get("subPt");
		String sid = params.get("uid");

		// 签名生成:openid 的值加上“|”加上token 的值加上“|”加上appkey 的值进行MD5 运算，取32 位小写的MD5
		// 值后与sign 的值进行匹配，如匹配一致则数据验证成功，否则失败。
		// String auth =
		// "oauth2id="+DouyuOauth2id+"&secret="+DouyuSecret+"&sdk_version="+DouyuSdkVersion+"&sid="+sid;
		// 请求
		String resp = HttpUtils.create(DouyuVerifyUrl).addParam("oauth2id", DouyuOauth2id)
				.addParam("secret", DouyuSecret).addParam("sdk_version", DouyuSdkVersion).addParam("sid", sid).post();
		log.info("douyu|login resp:{}", resp);
		try {
			Resp rs = JSON.parseObject(resp, Resp.class);
			if (rs == null)
				return LoginResponse.Timeout;
			LoginResponse lr = null;
			if (rs.Code == 0) {
				lr = new LoginResponse();
				lr.userInfo = new UserInfo();
				String openid = null;
				// try {
				// String s = Base64.decodeAsString(URLDecoder.decode(rs.Data,
				// "utf-8"));
				Data c = JSON.parseObject(rs.Data, Data.class);
				openid = String.valueOf(c.openid);
				// } catch (UnsupportedEncodingException e) {
				// log.error("斗鱼 获取openid异常|{}|{}|{}", rs.Code,
				// rs.getMsg(),rs.Data);
				// e.printStackTrace();
				// }

				lr.userInfo.account = openid;

			} else {
				log.error("斗鱼|{}|{}", rs.Code, rs.getMsg());
				lr = new LoginResponse(1, rs.getMsg());
			}
			return lr;
		} catch (Exception e) {
			log.error("斗鱼  解析登录返回消息异常");
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
		int Code;
		String Msg;
		String Data;

		public int getCode() {
			return Code;
		}

		public void setCode(int codes) {
			Code = codes;
		}

		public String getMsg() {
			return Msg;
		}

		public void setMsg(String msgs) {
			Msg = msgs;
		}

		public String getData() {
			return Data;
		}

		public void setData(String datas) {
			Data = datas;
		}
	}

	static class Data {
		String openid;
		String icon;
		String nickname;

		public String getOpenid() {
			return openid;
		}

		public void setOpenid(String openid) {
			this.openid = openid;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

	}
}