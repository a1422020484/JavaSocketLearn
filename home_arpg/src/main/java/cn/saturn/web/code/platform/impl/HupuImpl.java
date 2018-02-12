package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class HupuImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("hupu");
    static final String flag = "hupu";

    String HupuAppId = PTConfig.val("HupuAppId");
    String HupuGameId = PTConfig.val("HupuGameId");
    String HupuServerId = PTConfig.val("HupuServerId");
    String HupuServerName = PTConfig.val("HupuServerName");
    String HupuVerifyUrl = PTConfig.val("HupuVerifyUrl");

    String[] params = new String[]{"uid", "token"};

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
        String uid = params.get("uid");
        String token = params.get("token");

        String resp = HttpUtils.create(HupuVerifyUrl)
                .addParam("appid", HupuAppId)
                .addParam("uid", uid)
                .addParam("gameid", HupuGameId)
                .addParam("rolename", HupuServerName)
                .addParam("token", token)
                .addParam("serverid", HupuServerId)
                .post();
        if (!"1".equals(resp)) {
            return LoginResponse.Timeout;
        }

        LoginResponse lr = null;
        lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = uid;
        return lr;
    }

}
