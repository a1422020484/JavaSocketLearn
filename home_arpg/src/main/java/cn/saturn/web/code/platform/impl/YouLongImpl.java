package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.HttpUtils;

import java.util.Map;

/**
 * 游龙
 *
 * @author xiezuojie
 */
@Component
public class YouLongImpl implements PlatformInterface {
    public static final String flag = "youlong";
    static final Logger log = LoggerFactory.getLogger(flag);

    String YouLongPid = PTConfig.val("YouLongPid");
    String YouLongPkey = PTConfig.val("YouLongPkey");
    String YouLongVerifyUrl = PTConfig.val("YouLongVerifyUrl");

    String[] params = new String[]{"token"};

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
        String token = params.get("token");

        String respCtx = HttpUtils.create(YouLongVerifyUrl)
                .addParam("token", token)
                .addParam("pid", YouLongPid)
                .post();

        Resp resp = null;
        try {
            resp = JSON.parseObject(respCtx, Resp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resp == null) {
            return LoginResponse.Timeout;
        }

        LoginResponse lr = null;
        if ("1".equals(resp.state)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = resp.username;
        } else {
            lr = new LoginResponse(1, resp.errcMsg);
        }
        return lr;
    }

    static class Resp {
        String state;
        String username;
        String errMsg;
        String errcMsg;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public String getErrcMsg() {
            return errcMsg;
        }

        public void setErrcMsg(String errcMsg) {
            this.errcMsg = errcMsg;
        }
    }

}
