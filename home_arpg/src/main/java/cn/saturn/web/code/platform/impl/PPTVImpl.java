package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * PPTV
 *
 * @author xiezuojie
 */
@Component
public class PPTVImpl implements PlatformInterface {
    static final String PT = "pptv";
    static final Logger log = LoggerFactory.getLogger("pptv");

    String PPTVApp = PTConfig.val("PPTVApp");
    String PPTVVerifyType_login = PTConfig.val("PPTVVerifyType_login");
    String PPTVVerifyUrl = PTConfig.val("PPTVVerifyUrl");

    String[] params = new String[]{"sessionid", "username"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return PT;
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String sessionid = params.get("sessionid");
        String username = params.get("username");

        String resp = HttpUtils.create(PPTVVerifyUrl)
                .addParam("type", PPTVVerifyType_login)
                .addParam("sessionid", sessionid)
                .addParam("username", username)
                .addParam("app", PPTVApp)
                .get();

        if (StringUtils.isNotBlank(resp)) {
            return LoginResponse.ParamError;
        }

        if (!"1".equals(resp)) {
            return LoginResponse.ParamError;
        }

        LoginResponse lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = username;

        return lr;
    }

}
