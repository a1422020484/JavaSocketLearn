package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.PlatformInterface;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhuangyuetao
 */
@Component
public class XiaMenXiangTongImpl implements PlatformInterface {
    public static final String flag = "yijie-xiamenxiangtong";

    @Override
    public String[] requireParams() {
        return YijieTool.params;
    }

    @Override
    public String ptFlag() {
        return flag;
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        return YijieTool.login(params);
    }

}
