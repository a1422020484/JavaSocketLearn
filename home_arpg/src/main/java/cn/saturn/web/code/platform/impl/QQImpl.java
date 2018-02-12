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
import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class QQImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("QQ");

    static final String QQAppId = PTConfig.val("QQAppId");
    static final String QQAppKey = PTConfig.val("QQAppKey");
    static final boolean Debug = PTConfig.booleanVal("Debug");
    static final String QQVerifyUrl = Debug ? PTConfig.val("QQVerifyUrlDebug") : PTConfig.val("QQVerifyUrl");
    static final String QQVerifyLogin = PTConfig.val("QQVerifyLogin");

    static final String[] params = new String[]{"openid", "openkey"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "qq";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String openid = params.get("openid");
        String openkey = params.get("openkey");
        String userip = "";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
        String sig = MD5.encode(QQAppKey + timestamp);
        String url = QQVerifyUrl + QQVerifyLogin + "?timestamp=" + timestamp + "&appid=" + QQAppId + "&sig=" + sig + "&openid=" + openid + "&encode=1";

        Resp rs = null;
        try {
            Data data = new Data(QQAppId, openid, openkey, userip);
            byte[] dataArr = JSON.toJSONString(data).getBytes("utf-8");
            String resp = HttpUtils.create(url).post(dataArr);
            rs = JSON.parseObject(resp, Resp.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (rs == null) {
            return LoginResponse.Timeout;
        }

        LoginResponse lr = null;
        if ("0".equals(rs.ret)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = openid;
        } else {
//			log.error("QQ|{}|{}|{}|{}", openid, openkey, userip, rs.msg);
            lr = new LoginResponse(1, rs.msg);
        }
        return lr;
    }

    static class Resp {
        String ret;
        String msg;

        public String getRet() {
            return ret;
        }

        public void setRet(String ret) {
            this.ret = ret;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    static class Data {
        String appid;
        String openid;
        String openkey;
        String userip;

        public Data() {
        }

        public Data(String appid, String openid, String openkey, String userip) {
            super();
            this.appid = appid;
            this.openid = openid;
            this.openkey = openkey;
            this.userip = userip;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getOpenkey() {
            return openkey;
        }

        public void setOpenkey(String openkey) {
            this.openkey = openkey;
        }

        public String getUserip() {
            return userip;
        }

        public void setUserip(String userip) {
            this.userip = userip;
        }
    }

}
