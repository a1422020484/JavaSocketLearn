package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class LiuLianGZBNSImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("liulian-gzbns");

    final String LiuLianAppId = PTConfig.val("LiuLianGZBNSAppId");
    final String LiuLianAppKey = PTConfig.val("LiuLianGZBNSAppKey");
    final String LiuLianAppSecret = PTConfig.val("LiuLianGZBNSAppSecret");
    final String LiuLianServerKey = PTConfig.val("LiuLianGZBNSServerKey");
    final String LiuLianPrivateKey = MD5.encode(LiuLianAppKey + "#" + LiuLianAppSecret);
    final String LiuLianServerKey2 = MD5.encode(LiuLianAppKey + "#" + LiuLianServerKey);
    final boolean Debug = PTConfig.booleanVal("Debug");
    final String LiuLianVerifyUrl = Debug ? PTConfig.val("LiuLianGZBNSVerifyUrlDebug") : PTConfig.val("LiuLianGZBNSVerifyUrl");

    @Override
    public String ptFlag() {
        return "liulian-gzbns";
    }

    String[] params = new String[]{"sid"};

    @Override
    public LoginResponse login(Map<String, String> params) {
        String sid = params.get("sid");

        String sign = MD5.encode(LiuLianAppId + LiuLianAppKey + LiuLianServerKey2 + sid);
        String account = null;
        try {
            String resp = HttpUtils.create(LiuLianVerifyUrl)
                    .addParam("appid", LiuLianAppId)
                    .addParam("appkey", LiuLianAppKey)
                    .addParam("sid", sid)
                    .addParam("sign", sign)
                    .post();
            JSONObject json = JSON.parseObject(resp);
            String status = json.getString("status");
            if (!"1".equals(status)) {
                return new LoginResponse(1, json.getString("msg"));
            }
            account = json.getString("account");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (StringUtils.isBlank(account)) {
            return LoginResponse.Timeout;
        }

        LoginResponse lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = account;
        return lr;
    }

    @Override
    public String[] requireParams() {
        return params;
    }

}
