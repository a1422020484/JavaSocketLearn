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

@Component
public class A17173Impl implements PlatformInterface {

    static final Logger log = LoggerFactory.getLogger("17173");

    String gameId = PTConfig.val("17173GameId");
    String appId = PTConfig.val("17173AppId");
    String appKey = PTConfig.val("17173AppKey");

    String[] params = new String[]{"loginTime", "userName"};

    @Override
    public LoginResponse login(Map<String, String> params) {
        // TODO Auto-generated method stub
        String data = params.get("loginTime");
        String[] strs = data.split("\\|", 4);
        String userId = strs[0];
        String loginTime = strs[1];
        String code = strs[2];
        String sign = strs[3];
        String userName = params.get("userName");

        String mysign = MD5.encode("logintime=" + loginTime + "&username=" + userName + "&appkey=" + appKey);

        LoginResponse lr = null;
        if (code.equals("0")) {
            if (mysign.equals(sign)) {
                lr = new LoginResponse();
                lr.userInfo = new UserInfo();
                lr.userInfo.account = userId;
            } else {
                lr = new LoginResponse(1, "sign error");
            }
        } else {
            log.error("17173|{}|{}", "登录失败:", code);
            lr = new LoginResponse(1, code);
        }
        return lr;
    }

    @Override
    public String ptFlag() {
        // TODO Auto-generated method stub
        return "17173";
    }

    @Override
    public String[] requireParams() {
        // TODO Auto-generated method stub
        return params;
    }

}
