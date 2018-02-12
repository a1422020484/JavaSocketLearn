package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HmacSHA1Encryption;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class XiaoMiImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("xiaomi");

    String appId = PTConfig.val("XiaoMiAppId");
    //	String appKey = PTConfig.val("XiaoMiAppKey");
    String secret = PTConfig.val("XiaoMiAppSecret");
    String url = PTConfig.val("XiaoMiVerifySessionUrl");

    String[] params = new String[]{"uid", "session"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "xiaomi";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String uid = params.get("uid");
        String session = params.get("session");

        StringBuilder data = new StringBuilder();
        data.append("appId=").append(appId)
                .append("&session=").append(session)
                .append("&uid=").append(uid);
        String sig = HmacSHA1Encryption.HmacSHA1Encrypt(data.toString(), secret);
        String resp = HttpUtils.create(url)
                .addParam("appId", appId)
                .addParam("session", session)
                .addParam("uid", uid)
                .addParam("signature", sig)
                .get();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null)
            return LoginResponse.Timeout;

        LoginResponse lr = null;
        if ("200".equals(rs.errcode)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = uid;
        } else {
//			log.error("小米|{}|{}|{}|{}", uid, session, rs.errcode, rs.errMsg);
            lr = new LoginResponse(1, rs.errMsg);
        }
        return lr;
    }

    static class Resp {
        String errcode;
        String errMsg;

        public String getErrcode() {
            return errcode;
        }

        public void setErrcode(String errcode) {
            this.errcode = errcode;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }
    }
}
