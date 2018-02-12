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
 * 木蚂蚁平台
 *
 * @author zhuangyuetao
 */
@Component
public class MumayiImpl implements PlatformInterface {
    public static final String flag = "mumayi";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"token", "uid"};

    public static final String appId = PTConfig.val("MumayiAppId"); // client_Id
    public static final String appKey = PTConfig.val("MumayiAppKey"); // client_secret
    public static final String url = PTConfig.val("MumayiVerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String token = params.get("token");
        String uid = params.get("uid");
        // 请求
        String resp = HttpUtils.create(url).addParam("token", token).addParam("uid", uid).post();
        LoginResponse lr = null;
        if (resp.equals("success")) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = uid;
        } else {
            log.error(flag + "|{}|{}", 0, resp);
            lr = new LoginResponse(1, resp);
        }
        return lr;
    }

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return flag;
    }

}
