package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.PlatformUtils;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 狐狸平台
 *
 * @author zhuangyuetao
 */
@Component
public class KuaifaImpl implements PlatformInterface {
    public static final String flag = "kuaifa";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"openid", "token"};

    public static final String appKey = PTConfig.val("KuaifaAppKey");
    public static final String appSecurity = PTConfig.val("KuaifaSecurityKey");
    public static final String url = PTConfig.val("KuaifaVerifyUrl");

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
        String openid = params.get("openid");
        String token = params.get("token");
        int timestamp = (int) (System.currentTimeMillis() / 1000L);
        String gamekey = appKey;
        // 生成签名
        Map<String, String> signParams = new HashMap<>();
        signParams.put("token", token);
        signParams.put("openid", openid);
        signParams.put("timestamp", timestamp + "");
        signParams.put("gamekey", gamekey);
        // String sign = PlatformUtils.createSign(signParams);
        String sign = PlatformUtils.sign(signParams, new PlatformUtils.ISignHandler() {
            @Override
            public String handler(String key, String value, int index) {
                try {
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (index == 0) {
                    return key + "=" + value;
                }
                return "&" + key + "=" + value;
            }

            @Override
            public String encode(String signStr) {
                return MD5.encode(signStr);
            }
        });
        // 再加密一次
        sign = MD5.encode(sign + appSecurity);

        // 发送请求
        String resp = HttpUtils.create(url).addParam("openid", openid).addParam("token", token).addParam("timestamp", String.valueOf(timestamp)).addParam("gamekey", gamekey).addParam("_sign", sign)
                .post();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        LoginResponse lr = null;
        if (rs.result == 0) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = openid;
        } else {
            log.error(flag + "|{}|{}", rs.getResult(), rs.getResult_desc());
            lr = new LoginResponse(1, rs.getResult_desc());
        }
        return lr;
    }

    static class Resp {
        protected int result;
        protected String result_desc;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public String getResult_desc() {
            return result_desc;
        }

        public void setResult_desc(String result_desc) {
            this.result_desc = result_desc;
        }

    }

    // public static void main(String[] args) {
    // Map<String, String> signParams = new HashMap<>();
    // signParams.put("token", "1");
    // signParams.put("openid", "2");
    // signParams.put("timestamp", "3");
    // signParams.put("gamekey", "4");
    // String sign = PlatformUtils.sign(signParams, new PlatformUtils.ISignHandler() {
    // @Override
    // public String handler(String key, String value, int index) {
    // if (index == 0) {
    // return key + "=" + value;
    // }
    // return "&" + key + "=" + value;
    // }
    //
    // @Override
    // public String encode(String signStr) {
    // return MD5.encode(signStr);
    // }
    // });
    // // String sign0 = PlatformUtils.createSign(signParams);
    //
    // System.out.println(sign);
    // // System.out.println(sign0);
    // }

}
