package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * kupai
 *
 * @author xiezuojie
 */
@Component
public class KupaiImpl implements PlatformInterface {
    static final String PT = "kupai";
    static final Logger log = LoggerFactory.getLogger("kupai");

    private static final String KupaiAppId = PTConfig.val("KupaiAppId");
    private static final String KupaiAppKey = PTConfig.val("KupaiAppKey");
    private static final String KupaiGrantType_AuthCode = PTConfig.val("KupaiGrantType_AuthCode");
    private static final String KupaiVerifyUrl = PTConfig.val("KupaiVerifyUrl");

    @Override
    public String ptFlag() {
        return PT;
    }

    String[] params = new String[]{"authcode"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String authCode = params.get("authcode");

        String content = HttpUtils.create(KupaiVerifyUrl)
                .addParam("grant_type", KupaiGrantType_AuthCode)
                .addParam("client_id", KupaiAppId)
                .addParam("client_secret", KupaiAppKey)
                .addParam("code", authCode)
                .addParam("redirect_uri", KupaiAppKey)
                .post();
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
        if (StringUtils.isNotBlank(resp.openid)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = resp.openid;
            lr.setExt(resp.access_token + "|" + resp.openid);
        } else {
//			log.error("UC|{}|{}|{}", sid, rs.state.code, rs.state.msg);
            lr = new LoginResponse(1, "Kupai err: " + resp.error);
        }
        return lr;
    }

    static class Resp {
        String access_token;
        String expires_in;
        String refresh_token;
        String openid;
        String error;
        String error_description;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getError_description() {
            return error_description;
        }

        public void setError_description(String error_description) {
            this.error_description = error_description;
        }
    }

}
