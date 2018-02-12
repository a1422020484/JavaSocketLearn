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

import java.util.HashMap;
import java.util.Map;

@Component
public class SougouImpl implements PlatformInterface {
    public static final String flag = "sougou";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{};

    public static final String appId = PTConfig.val("SougouAppId");
    public static final String appKey = PTConfig.val("SougouAppKey");
    public static final String appSecret = PTConfig.val("SougouAppSecret");
    // public static final String url = (PlatformUtils.ptdebug) ? PTConfig.val("SougouVerifyUrlDebug") : PTConfig.val("SougouVerifyUrl");
    public static final String url = PTConfig.val("SougouVerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String gid = appId;
        String user_id = params.get("userId");
        String session_key = params.get("sessionKey");
        // 签名
        Map<String, String> signParams = new HashMap<>();
        signParams.put("gid", gid);
        signParams.put("session_key", session_key);
        signParams.put("user_id", user_id);
        // 签名生成
        String auth = "gid=" + gid + "&session_key=" + session_key + "&user_id=" + user_id + "&" + appSecret;
        auth = MD5.encode(auth);
        // 请求
        String resp = HttpUtils.create(url).addParam("gid", gid).addParam("session_key", session_key).addParam("user_id", user_id).addParam("auth", auth).post();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }
        // 登陆成功返回
        LoginResponse lr = null;
        if (rs.result) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = user_id;
        } else {
            log.error(flag + "|{}|{}", 0, resp);
            lr = new LoginResponse(1, resp);
        }
        return lr;
    }

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return flag;
    }

    public static class Resp {
        protected boolean result;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    }

    // public static void main(String[] args) {
    // Map<String, String> signParams = new HashMap<>();
    // signParams.put("gid", "123");
    // signParams.put("session_key", "123123");
    // signParams.put("user_id", "123123");
    // signParams.put("appSecret", appSecret);
    // // 签名生成
    // String auth = PlatformUtils.sign(signParams, new PlatformUtils.ISignHandler() {
    // @Override
    // public String handler(String key, String value, int index) {
    //
    // if (index == 0) {
    // return key + "=" + value;
    // }
    // return "&" + key + "=" + value;
    // }
    //
    // @Override
    // public String encode(String signStr) {
    // String str = signStr + "&" + appSecret;
    // System.out.println(str);
    // return MD5.encode(str);
    // }
    // });
    //
    // System.out.println(auth);
    //
    // }
}
