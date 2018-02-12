package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class YouXinMoMoImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("youxin");
    static final String flag = "youxin";

    String YouXinMoMoAppId = PTConfig.val("YouXinMoMoAppId");
    String YouXinMoMoAppKey = PTConfig.val("YouXinMoMoAppKey");
    String YouXinMoMoVerifyUrl = PTConfig.val("YouXinMoMoVerifyUrl");

    String[] params = new String[]{"token"};

    @Override
    public String ptFlag() {
        return flag;
    }

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String token = params.get("token");
        String sign = MD5.encode(token + "|" + YouXinMoMoAppKey);

        String uid = null;
        try {
            String resp = HttpUtils.create(YouXinMoMoVerifyUrl)
                    .addParam("token", token)
                    .addParam("app_id", YouXinMoMoAppId)
                    .addParam("sign", sign)
                    .get();
            JSONObject json = JSON.parseObject(resp);
            String status = json.getString("status");
            if (!"1".equals(status)) {
                return new LoginResponse(1, json.getString("errormsg"));
            }
            uid = json.getString("uid");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (uid == null) {
            return LoginResponse.ParamError;
        }

        LoginResponse lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = uid;
        return lr;
    }

}
