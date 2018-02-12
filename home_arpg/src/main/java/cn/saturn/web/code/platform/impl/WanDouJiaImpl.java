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
 * 豌豆夹
 *
 * @author xiezuojie
 */
@Component
public class WanDouJiaImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("wandoujia");

    String appKey = PTConfig.val("WanDouJiaAppKey");
    String checkUrl = PTConfig.val("WanDouJiaCheckUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String uid = params.get("uid");
        String token = params.get("token");

        String resp = HttpUtils.create(checkUrl)
                .addParam("uid", uid)
                .addParam("token", token)
                .addParam("appkey_id", appKey)
                .get();
        if (StringUtils.isBlank(resp))
            return LoginResponse.Timeout;

        boolean bool = Boolean.parseBoolean(resp);
        if (!bool) {
            log.error("wandoujia|{}|{} 令牌无效", uid, token);
            return new LoginResponse(1, "令牌无效,验证失败");
        }

        LoginResponse lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = uid;
        return lr;
    }

    @Override
    public String ptFlag() {
        return "wandoujia";
    }

    String[] params = new String[]{"uid", "token"};

    @Override
    public String[] requireParams() {
        return params;
    }

}
