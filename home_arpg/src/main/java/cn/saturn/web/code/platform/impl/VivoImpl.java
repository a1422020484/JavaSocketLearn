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

import java.util.Map;

/**
 * 狐狸平台
 *
 * @author zhuangyuetao
 */
@Component
public class VivoImpl implements PlatformInterface {
    public static final String flag = "vivo";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"access_token"};

    public static final String VivoAppId = PTConfig.val("VivoAppId");
    public static final String VivoCpId = PTConfig.val("VivoCpId");
    public static final String VivoCpKey = PTConfig.val("VivoCpKey");
    public static final String VivoVerifyUrl = PTConfig.val("VivoVerifyUrl");

    /**
     *
     */
    @Override
    public LoginResponse login(Map<String, String> params) {
        String access_token = params.get("access_token");

        // 请求
        String resp = HttpUtils.create(VivoVerifyUrl).addParam("access_token", access_token).post();
        Resp rs = null;
        try {
            rs = JSON.parseObject(resp, Resp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rs == null || rs.uid == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
        LoginResponse lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = rs.uid;

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
        protected String uid;
        protected String email;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

    // public static void main(String[] args) {
    // String tokenKey = "a43cd7a510fc3b06792a0cb509b58415";
    // String sign = MD5.encode(appKey + tokenKey);
    //
    // System.out.println(sign);
    //
    // }

}
