package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import net.paoding.rose.web.Invocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class WeiXinImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("WeiXin");

    static final String WeiXinAppId = PTConfig.val("WeiXinAppId");
    static final String WeiXinAppKey = PTConfig.val("WeiXinAppKey");
    static final boolean Debug = PTConfig.booleanVal("Debug");
    static final String WeiXinVerifyUrl = Debug ? PTConfig.val("WeiXinVerifyUrlDebug") : PTConfig.val("WeiXinVerifyUrl");
    static final String WeiXinCheckToken = PTConfig.val("WeiXinCheckToken");

    @Autowired
    Invocation inv;

    static final String[] params = new String[]{"openid", "accessToken"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "weixin";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String openid = params.get("openid");
        String accessToken = params.get("accessToken");
//		String userip = CommonUtils.getIpAddr(inv.getRequest());
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
        String sig = MD5.encode(WeiXinAppKey + timestamp);
        String url = WeiXinVerifyUrl + WeiXinCheckToken +
                "?timestamp=" + timestamp + "&appid=" + WeiXinAppId + "&sig=" + sig + "&openid=" + openid + "&encode=1";

        Resp rs = null;
        try {
            Data data = new Data(openid, accessToken);
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
//			log.error("WeiXin|{}|{}|{}|{}", openid, openkey, userip, rs.msg);
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
        String openid;
        String accessToken;

        public Data() {
        }

        public Data(String openid, String accessToken) {
            super();
            this.openid = openid;
            this.accessToken = accessToken;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }

}
