package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.Rsa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class HTCImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("htc");
    static final String flag = "htc";

    String HTCAppId = PTConfig.val("HTCAppId");
    String HTCPublicKey = PTConfig.val("HTCPublicKey");

    String[] params = new String[]{"userId", "account", "accountSign"};

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
        String userId = params.get("userId");
        String account = params.get("account");
        String accountSign = params.get("accountSign");
        String accountSign2 = accountSign.replaceAll(" ", "+");

        boolean rs = Rsa.doCheck(account, accountSign2, HTCPublicKey);
        if (!rs) {
            return new LoginResponse(1, "sign error!");
        }

        LoginResponse lr = null;
        lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = userId;
        return lr;
    }

}
