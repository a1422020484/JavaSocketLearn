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

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class P91ZhuShouImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("91zhushou");

    String appId = PTConfig.val("91ZhuShouAppId");
    String appKey = PTConfig.val("91ZhuShouAppKey");
    String verifyUrl = PTConfig.val("91ZhuShouVerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String uin = params.get("uin");
        String sessionId = params.get("sessionid");
        String act = "4";
        StringBuilder strSign = new StringBuilder();
        strSign.append(appId);
        strSign.append(act);
        strSign.append(uin);
        strSign.append(sessionId);
        strSign.append(appKey);
        String sign = MD5.encode(strSign.toString());
        String resp = HttpUtils.create(verifyUrl)
                .addParam("Appid", appId)
                .addParam("Act", act)
                .addParam("Uin", uin)
                .addParam("SessionId", sessionId)
                .addParam("sign", sign)
                .get();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null)
            return LoginResponse.Timeout;
        LoginResponse lr = null;
        if ("1".equals(rs.ErrorCode)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = uin;
        } else {
            log.error("91助手|{}|{}", uin, rs.ErrorDesc);
            lr = new LoginResponse(1, rs.ErrorDesc);
        }
        return lr;
    }

    @Override
    public String ptFlag() {
        return "91zs";
    }

    String[] params = new String[]{"uin", "sessionid"};

    @Override
    public String[] requireParams() {
        return params;
    }

    static class Resp {
        String ErrorCode;
        String ErrorDesc;

        public String getErrorCode() {
            return ErrorCode;
        }

        public void setErrorCode(String errorCode) {
            ErrorCode = errorCode;
        }

        public String getErrorDesc() {
            return ErrorDesc;
        }

        public void setErrorDesc(String errorDesc) {
            ErrorDesc = errorDesc;
        }
    }
}
