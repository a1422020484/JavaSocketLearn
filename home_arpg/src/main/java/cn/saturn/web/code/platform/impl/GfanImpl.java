package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class GfanImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("gfan");

    String GfanChannelID = PTConfig.val("GfanChannelID");
    String GfanAppName = PTConfig.val("GfanAppName");
    String GfanPackageName = PTConfig.val("GfanPackageName");
    String GfanSecretKey = PTConfig.val("GfanSecretKey");
    String GfanVerifyTokenUrl = PTConfig.val("GfanVerifyTokenUrl");

    String[] params = new String[]{"token"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "jifeng";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String token = params.get("token");

        String ctx = HttpUtils.create(GfanVerifyTokenUrl)
                .addHeader("packageName", GfanPackageName)
                .addHeader("appName", GfanAppName)
                .addHeader("channelID", GfanChannelID)
                .addParam("token", token)
                .post();

        Resp rs = JSON.parseObject(ctx, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        LoginResponse lr = null;
        if ("1".equals(rs.resultCode)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = String.valueOf(rs.uid);
        } else {
            lr = LoginResponse.ParamError;
        }
        return lr;
    }

    static class Resp {
        String resultCode;
        int uid;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
