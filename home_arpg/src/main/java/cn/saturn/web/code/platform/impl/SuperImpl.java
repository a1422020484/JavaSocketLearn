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
public class SuperImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("super");

    String SuperGameId = PTConfig.val("SuperGameId");
    String SuperVerifyUrl = PTConfig.val("SuperVerifyUrl");

    String[] params = new String[]{"user_id", "token"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "super";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String uid = params.get("user_id");
        String token = params.get("token");

        String resp = HttpUtils.create(SuperVerifyUrl)
                .addParam("user_id", uid)
                .addParam("token", token)
                .addParam("data", "")
                .get();

        Resp rs = null;
        try {
            rs = JSON.parseObject(resp, Resp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        if (!uid.equals(rs.userinfo.user_id)
                || !SuperGameId.equals(rs.userinfo.osdk_game_id)) {
            return LoginResponse.ParamError;
        }

        LoginResponse lr = null;
        if ("1".equals(rs.status)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = uid;
        } else {
            lr = new LoginResponse(1, rs.msg);
        }
        return lr;
    }

    static class Resp {
        String status;
        String msg;
        Userinfo userinfo;
        Data data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Userinfo getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(Userinfo userinfo) {
            this.userinfo = userinfo;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    static class Userinfo {
        String osdk_game_id;
        String user_id;
        String login_sdk_name;
        String account_system_id;
        String osdk_user_id;
        String channel_id;
        String extend;

        public String getOsdk_game_id() {
            return osdk_game_id;
        }

        public void setOsdk_game_id(String osdk_game_id) {
            this.osdk_game_id = osdk_game_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getLogin_sdk_name() {
            return login_sdk_name;
        }

        public void setLogin_sdk_name(String login_sdk_name) {
            this.login_sdk_name = login_sdk_name;
        }

        public String getAccount_system_id() {
            return account_system_id;
        }

        public void setAccount_system_id(String account_system_id) {
            this.account_system_id = account_system_id;
        }

        public String getOsdk_user_id() {
            return osdk_user_id;
        }

        public void setOsdk_user_id(String osdk_user_id) {
            this.osdk_user_id = osdk_user_id;
        }

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public String getExtend() {
            return extend;
        }

        public void setExtend(String extend) {
            this.extend = extend;
        }
    }

    static class Data {
        String id;
        String name;
        String sex;
        String access_token;
        String refresh_token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }
    }
}
