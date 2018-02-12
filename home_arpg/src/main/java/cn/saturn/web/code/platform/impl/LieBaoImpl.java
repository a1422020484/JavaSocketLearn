package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.util.Map;

/**
 * 猎宝
 *
 * @author xiezuojie
 */
@Component
public class LieBaoImpl implements PlatformInterface {
    public static final String flag = "liebao";
    static final Logger log = LoggerFactory.getLogger(flag);

    String LieBaoAppId = PTConfig.val("LieBaoAppId");
    String LieBaoAppKey = PTConfig.val("LieBaoAppKey");
    String LieBaoGameId = PTConfig.val("LieBaoGameId");

    String[] params = new String[]{"username", "logintime", "sign"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "liebao";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String username = params.get("username");
        String logintime = params.get("logintime");
        String sign = params.get("sign");

        String mysign = MD5.encode("username=" + username + "&appkey=" + LieBaoAppKey + "&logintime=" + logintime);

        LoginResponse lr = null;
        if (mysign.equals(sign)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = username;
        } else {
            lr = new LoginResponse(1, "sign error");
        }
        return lr;
    }

}
