package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.HttpUtils;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class ChongChongImpl implements PlatformInterface {
    static final String flag = "chongchong";
    static final Logger log = LoggerFactory.getLogger(flag);

    String ChongChongId = PTConfig.val("ChongChongId");
    String ChongChongVerifyUtl = PTConfig.val("ChongChongVerifyUtl");

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

        String resp = HttpUtils.create(ChongChongVerifyUtl).addParam("token", token).post();
        if (!"success".equals(resp)) {
            return new LoginResponse(1, "loginerror: " + resp);
        }

        LoginResponse lr = null;
        lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = uid;
        return lr;
    }

    // public static void main(String[] args) {
    // Map<String, String> params = new HashMap<>();
    // params.put("token", "3b8bfd7c54e0442c84b7985bef28ef3d");
    // params.put("uid", "1314851");
    //
    // ChongChongImpl c = new ChongChongImpl();
    // c.login(params);
    // }
}
