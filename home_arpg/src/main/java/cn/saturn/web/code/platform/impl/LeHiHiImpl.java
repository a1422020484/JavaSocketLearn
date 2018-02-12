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
public class LeHiHiImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("lehihi");
    static final String flag = "lehihi";

    String LeHiHiPid = PTConfig.val("LeHiHiPid");
    String LeHiHiAppKey = PTConfig.val("LeHiHiAppKey");
    String LeHiHiSecretKey = PTConfig.val("LeHiHiSecretKey");
    String LeHiHiVerifyUrl = PTConfig.val("LeHiHiVerifyUrl");

    String[] params = new String[]{"username", "token"};

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
        String username = params.get("username");
        String token = params.get("token");

        String sign = MD5.encode("pid=" + LeHiHiPid + "&token=" + token + "&username=" + username + LeHiHiSecretKey);
        String resp = HttpUtils.create(LeHiHiVerifyUrl)
                .addParam("username", username)
                .addParam("token", token)
                .addParam("pid", LeHiHiPid)
                .addParam("sign", sign)
                .post();
        Resp rs = null;
        try {
            rs = JSON.parseObject(resp, Resp.class);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        if (rs.State != 1) {
            return new LoginResponse(1, rs.msg);
        }

        Data data = null;
        try {
            data = JSON.parseObject(rs.data, Data.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null) {
            return new LoginResponse(1, "data error");
        }

        LoginResponse lr = null;
        lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = String.valueOf(data.uid);
        return lr;
    }

    static class Resp {
        int State;
        String msg;
        String data;

        public int getState() {
            return State;
        }

        public void setState(int state) {
            State = state;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    static class Data {
        String username;
        int uid;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
