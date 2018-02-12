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
 * 深海印尼平台
 *
 * @author zhuangyuetao
 */
@Component
public class ShenHaiYinNiImpl implements PlatformInterface {
    public static final String flag = "login";
    static final Logger log = LoggerFactory.getLogger(flag);


	// String[] params = new String[] { "gameid", "uid", "token" };
	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "shenhaiyinni";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String subPlatform = params.get("subPt");
		String sid = params.get("uid");
		try {
	        LoginResponse lr = null;
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = sid;
            log.debug("shenhaiyinni|params: {}| sid:{}", params, sid);
	        return lr;
		} catch (Exception e) {
			log.error("深海印尼 解析登录返回消息异常");
            e.printStackTrace();
        }
	    return null;
	}
}