package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.PlatformInterface;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhuangyuetao
 */
@Component
public class Yijie07073Impl implements PlatformInterface {
    public static final String flag = "yijie-07073";

    @Override
    public String[] requireParams() {
        return Yijie07073Tool.params;
    }

    @Override
    public String ptFlag() {
        return flag;
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        return Yijie07073Tool.login(params);
    }

}
