package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class LenovoImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("lenovo");

    String LenovoAppId = PTConfig.val("LenovoAppId");
    String LenovoGetAccountUrl = PTConfig.val("LenovoGetAccountUrl");

    String[] params = new String[]{"lpsust"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "lianxiang";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String lpsust = params.get("lpsust");

        String ctx = HttpUtils.create(LenovoGetAccountUrl)
                .addParam("lpsust", lpsust)
                .addParam("realm", LenovoAppId)
                .get();
        if (ctx.contains("Error")) {
            Error err = null;
            try {
                XStream xs = new XStream();
                xs.alias("Error", Error.class);
                err = (Error) xs.fromXML(ctx);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (err != null) {
                return new LoginResponse(1, "Lenovo error code:" + err.Code);
            } else {
                return LoginResponse.ParamError;
            }
        }

        IdentityInfo rs = null;
        try {
            XStream xs = new XStream();
            xs.alias("IdentityInfo", IdentityInfo.class);
            rs = (IdentityInfo) xs.fromXML(ctx);
        } catch (Exception e) {
        }
        if (rs == null) {
            return LoginResponse.ParamError;
        }
        if ("0".equals(rs.verified)) {
            return new LoginResponse(1, "未激活联想帐号!");
        }

        LoginResponse lr = null;
        if (StringUtils.isNotBlank(rs.AccountID)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = rs.AccountID;
        } else {
            lr = LoginResponse.ParamError;
        }
        return lr;
    }

    /*
     * 例:
     * <?xml version="1.0" encoding="UTF-8"?>
     * <IdentityInfo>
     *     <AccountID>10003550803</AccountID>
     *     <Username >13810535887</Username>
     *     <DeviceID>123</DeviceID>
     *     <verified>1</verified>
     * </Identit yInfo>
     */
    static class IdentityInfo {
        String AccountID; // 对于用户帐号,该字段为用户ID。对于PID帐号,该字段为PID值。
        String Username; // 用户名(可选项)
        String DeviceID; // 登录所用设备ID(可选项)
        String verified; // 帐户是否已激活。0:未激活,1:已激活。

        public String getAccountID() {
            return AccountID;
        }

        public void setAccountID(String accountID) {
            AccountID = accountID;
        }

        public String getUsername() {
            return Username;
        }

        public void setUsername(String username) {
            Username = username;
        }

        public String getDeviceID() {
            return DeviceID;
        }

        public void setDeviceID(String deviceID) {
            DeviceID = deviceID;
        }

        public String getVerified() {
            return verified;
        }

        public void setVerified(String verified) {
            this.verified = verified;
        }
    }

    /*
     * <?xml version="1.0" encoding="UTF-8"?>
     * <Error>
     *     <Code>USS-0121</Code>
     *     <Timestamp>2014-07-01T17:51:49+08:00</Timestamp>
     * </Error>
     */
    static class Error {
        String Code;
        String Timestamp;

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getTimestamp() {
            return Timestamp;
        }

        public void setTimestamp(String timestamp) {
            Timestamp = timestamp;
        }
    }
}
