package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.PlatformInterface;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhuangyuetao
 */
@Component
public class YijieMzwJuHeImpl implements PlatformInterface {
    public static final String flag = "yijie-mzwjuhe";

    @Override
    public String[] requireParams() {
        return YijieMzwJuHeTool.params;
    }

    @Override
    public String ptFlag() {
        return flag;
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        return YijieMzwJuHeTool.login(params);
    }

}
