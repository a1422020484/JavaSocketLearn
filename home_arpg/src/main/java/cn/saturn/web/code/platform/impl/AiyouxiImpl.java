package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 爱游戏
 *
 * @author zhuangyuetao
 */
@Component
public class AiyouxiImpl implements PlatformInterface {
    public static final String flag = "aiyouxi";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{};

    public static final String appId = PTConfig.val("AiyouxiAppId"); // client_Id
    public static final String appKey = PTConfig.val("AiyouxiAppKey"); // client_secret
    public static final String url = PTConfig.val("AiyouxiVerifyUrl");
    public static final String version = PTConfig.val("AiyouxiVersion");

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
        String client_id = appId;
        String client_secret = appKey;
        String access_token = params.get("access_token");
        String code = params.get("code");
        String grant_type = "authorization_code";
        // String scope = params.getAccount("scope");
        // String state = params.getAccount("state");
        String sign_method = "MD5";
        String version = AiyouxiImpl.version;
        int timestamp = (int) (System.currentTimeMillis() / 1000L); // 精确到秒的时间
        String sign_sort0 = "client_id&client_secret&code&grant_type&version&sign_method";
        String sign_sort = sign_sort0;
        try {
            sign_sort = URLEncoder.encode(sign_sort0, "UTF-8"); // url加密
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sign0 = client_id + client_secret + code + grant_type + version + sign_method;
        String sign = MD5.encode(sign0);

        // 请求
        String resp = HttpUtils.create(url).addParam("client_id", client_id).addParam("access_token", access_token).addParam("code", code).addParam("grant_type", grant_type)
                .addParam("sign_method", sign_method).addParam("version", version).addParam("timestamp", timestamp + "").addParam("sign_sort", sign_sort).addParam("sign", sign).post();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        LoginResponse lr = null;
        if (rs.error == null) {
            lr = new LoginResponse();
            lr.userInfo.account = rs.user_id;
        } else {
            log.error(flag + "|{}|{}", rs.error, rs.error_description);
            lr = new LoginResponse(1, rs.error_description);
        }
        return lr;
    }

    static class Resp {
        protected String access_token;
        protected String token_type;
        protected String refresh_token;
        protected String expires_in;
        protected String re_expires_in;
        protected String scope;
        protected String user_id;

        protected String error_uri;
        protected String error;
        protected String state;
        protected String error_description;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        public String getRe_expires_in() {
            return re_expires_in;
        }

        public void setRe_expires_in(String re_expires_in) {
            this.re_expires_in = re_expires_in;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getError_uri() {
            return error_uri;
        }

        public void setError_uri(String error_uri) {
            this.error_uri = error_uri;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getError_description() {
            return error_description;
        }

        public void setError_description(String error_description) {
            this.error_description = error_description;
        }

    }

    // public static void main(String[] args) {
    // String sign_sort0 = "password&username&client_secret&timestamp&version&sign_met";
    // String sign_sort = sign_sort0;
    // try {
    // sign_sort = URLEncoder.encode(sign_sort0, "UTF-8"); // url加密
    // } catch (UnsupportedEncodingException e) {
    // e.printStackTrace();
    // }
    // System.out.println(sign_sort);
    //
    // }

}
