package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.Base64;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class BaiDuImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("baidu");

    String appId = PTConfig.val("BaiduAppId");
    String appKey = PTConfig.val("BaiduAppKey");
    String appSecret = PTConfig.val("BaiduAppSecret");
    String url = PTConfig.val("BaiduVerifyUrl");

    String[] params = new String[]{"accesstoken"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "baidu";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String accessToken = params.get("accesstoken");
        String sig = MD5.encode(appId + accessToken + appSecret);
        String resp = HttpUtils.create(url)
                .addParam("AppID", appId)
                .addParam("AccessToken", accessToken)
                .addParam("Sign", sig)
                .post();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null)
            return LoginResponse.Timeout;

        LoginResponse lr = null;
        if (rs.ResultCode == 1) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            String uid = null;
            try {
                String s = Base64.decodeAsString(URLDecoder.decode(rs.Content, "utf-8"));
                Content c = JSON.parseObject(s, Content.class);
                uid = String.valueOf(c.UID);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            lr.userInfo.account = uid;
        } else {
            log.error("百度|{}|{}", rs.ResultCode, rs.getResultMsg());
            lr = new LoginResponse(1, rs.getResultMsg());
        }
        return lr;
    }

    static class Resp {
        int AppID;
        int ResultCode;
        String ResultMsg;
        String Sign;
        String Content;

        public int getAppID() {
            return AppID;
        }

        public void setAppId(int appID) {
            AppID = appID;
        }

        public int getResultCode() {
            return ResultCode;
        }

        public void setResultCode(int resultCode) {
            ResultCode = resultCode;
        }

        public String getResultMsg() {
            return ResultMsg;
        }

        public void setResultMsg(String resultMsg) {
            ResultMsg = resultMsg;
        }

        public String getSign() {
            return Sign;
        }

        public void setSign(String sign) {
            Sign = sign;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }
    }

    static class Content {
        long UID;

        public long getUID() {
            return UID;
        }

        public void setUID(long uID) {
            UID = uID;
        }
    }
}
