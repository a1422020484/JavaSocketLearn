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
public class HaiMaIOSImpl implements PlatformInterface {
    public static final String flag = "haimaios";
    static final Logger log = LoggerFactory.getLogger(flag);

    String HaiMaIOSAppId = PTConfig.val("HaiMaIOSAppId");
    String HaiMaIOSAppKey = PTConfig.val("HaiMaIOSAppKey");
    String HaiMaIOSVerifyUrl = PTConfig.val("HaiMaIOSVerifyUrl");

    String[] params = new String[]{"validateToken", "uid"};

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
        String validateToken = params.get("validateToken");

        String resp = HttpUtils.create(HaiMaIOSVerifyUrl)
                .addParam("appid", HaiMaIOSAppId)
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
