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
import xzj.core.util.MD5;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class A91WanImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("91wan");

    String A91WanAppId = PTConfig.val("91WanAppId");
    String A91WanAppKey = PTConfig.val("91WanAppKey");
    String A91WanLoginKey = PTConfig.val("91WanLoginKey");
    String A91WanPayKey = PTConfig.val("91WanPayKey");
    String A91WanVerifyUrl = PTConfig.val("91WanVerifyUrl");

    String[] params = new String[]{"uid", "state"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "91wan";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String uid = params.get("uid");
        String state = params.get("state");

        StringBuilder data = new StringBuilder();
        data.append(A91WanAppId).append(uid).append(state).append(A91WanLoginKey);
        String sign = MD5.encode(data.toString());
        String resp = HttpUtils.create(A91WanVerifyUrl)
                .addParam("appid", A91WanAppId)
                .addParam("uid", uid)
                .addParam("state", state)
                .addParam("flag", sign)
                .get();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null)
            return LoginResponse.Timeout;

        LoginResponse lr = null;
        if ("100".equals(rs.ret)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = uid;
        } else {
            lr = new LoginResponse(1, rs.msg);
        }
        return lr;
    }

    static class Resp {
        String ret;
        String uid;
        String msg;

        public String getRet() {
            return ret;
        }

        public void setRet(String ret) {
            this.ret = ret;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
