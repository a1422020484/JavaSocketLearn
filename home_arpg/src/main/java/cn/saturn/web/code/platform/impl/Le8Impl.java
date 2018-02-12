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
 * 乐8 IOS
 *
 * @author zhuangyuetao
 */
@Component
public class Le8Impl implements PlatformInterface {
    public static final String flag = "le8";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"token", "uid"};

    public static final String appId = PTConfig.val(flag + "AppId"); // client_secret
    public static final String appKey = PTConfig.val(flag + "AppKey"); // client_secret
    public static final String url = PTConfig.val(flag + "VerifyUrl");

    /**
     * 1、访问地址url: http://api.le890.com/index.php?m=api&a=validate_token<br>
     * 2、 此外需要post参数3个: appid （即应用的appId） t （即服务器校验token） uid (即sdk返回的用户唯一id) 不可将这3个参数拼到url中<br>
     * 3、请求方式: POST<br>
     * 4、SDK登陆成功返回token后，请在一分钟内进行验证，一分钟以后token失效。<br>
     * 访问后，如果SDK服务器检测token有效，会返回success字符串。失败返回fail。 如要做登录验证，请在联运后台-应用管理中添加服务器IP白名单。
     */
    @Override
    public LoginResponse login(Map<String, String> params) {
        String token = params.get("token");
        String uid = params.get("uid");

        // 请求
        String resp = HttpUtils.create(url).addParam("appid", appId).addParam("uid", uid).addParam("t", token).post();
        if (resp == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
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
