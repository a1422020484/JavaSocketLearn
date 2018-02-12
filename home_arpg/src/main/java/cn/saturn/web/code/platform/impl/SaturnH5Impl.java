package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PlatformInterface;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.util.Map;

/**
 * Saturn平台(测试平台)
 */
@Component
public class SaturnH5Impl implements PlatformInterface {

    @Override
    public String[] requireParams() {
        return new String[]{};
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String account = params.get("account");
        String password = params.get("password");
//		String ext = params.getAccount("ext");

        password = MD5.encode(password); // md5加密

        LoginResponse lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.setAutoRegister(false);
        lr.userInfo.account = account;
        lr.userInfo.password = password;
//		lr.setAccount(account);
        // lr.setPassword(password);
        return lr;
    }

    @Override
    public String ptFlag() {
        return "Saturn_H5";
    }

}

