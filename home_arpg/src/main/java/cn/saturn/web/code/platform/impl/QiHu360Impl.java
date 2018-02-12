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
 * 奇虎360
 *
 * @author xiezuojie
 */
@Component
public class QiHu360Impl implements PlatformInterface {
    public static final String flag = "360";
    static final Logger log = LoggerFactory.getLogger(flag);

    String appId = PTConfig.val("QiHu360AppId");
    String appKey = PTConfig.val("QiHu360AppKey");
    String appSecret = PTConfig.val("QiHu360AppSecret");
    String authorizeUrl = PTConfig.val("QiHu360AuthorizeUrl");
    String accessTokenUrl = PTConfig.val("QiHu360AccessTokenUrl");

    String[] params = new String[]{"accessToken"};

    @Override
    public LoginResponse login(Map<String, String> params) {
        String access_token = params.get("accessToken");
        String fields = "id,name,avatar";
        String resp = HttpUtils.create(authorizeUrl).addParam("access_token", access_token).addParam("fields", fields).post();
        User user = JSON.parseObject(resp, User.class);
        if (user == null) {
            return LoginResponse.Timeout;
        }

        // 返回信息
        LoginResponse lr = null;
        if (user.error == null) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = user.id;
            lr.setExt(user.id);
        } else {
            log.error(flag + "|{}|{}", user.error, user.error_code);
            lr = new LoginResponse(1, user.error);
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

    static class User {
        protected String id;
        protected String name;
        protected String avatar; // 头像
        protected String sex;
        protected String area;
        protected String nick;
        protected String error_code;
        protected String error;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getError_code() {
            return error_code;
        }

        public void setError_code(String error_code) {
            this.error_code = error_code;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    // public static void main(String[] args) {
    // // 计算360私密key
    // String appSecret = "d05460e85a97d61e567cb0174d4c4896";
    // String appKey = "f01021eaab3ba6693920c2519a4f1cca";
    // String priveteKey = MD5.encode(appSecret + "#" + appKey);
    //
    // System.out.println(priveteKey);
    //
    // }

}
