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
 * 海马
 *
 * @author xiezuojie
 */
@Component
public class HaiMaImpl implements PlatformInterface {
    public static final String flag = "haima";
    static final Logger log = LoggerFactory.getLogger(flag);

    String HaiMaAppId = PTConfig.val("HaiMaAppId");
    String HaiMaAppKey = PTConfig.val("HaiMaAppKey");
    String HaiMaVerifyUrl = PTConfig.val("HaiMaVerifyUrl");

    String[] params = new String[]{"validateToken", "uid"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "haima";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String uid = params.get("uid");
        String validateToken = params.get("validateToken");

        String resp = HttpUtils.create(HaiMaVerifyUrl)
                .addParam("appid", HaiMaAppId)
                .addParam("t", validateToken)
                .addParam("uid", uid)
                .post();
        if (resp == null) {
            return LoginResponse.Timeout;
        }
        if ("fail".equals(resp)) {
            return new LoginResponse(1, resp);
        }
        String[] arr = resp.split("\\&");
        if (arr.length != 2) {
            return new LoginResponse(1, resp);
        }

        LoginResponse lr = null;
        if ("success".equals(arr[0])) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = arr[1];
        } else {
            lr = new LoginResponse(1, resp);
        }
        return lr;
    }
}
