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
 * 拇指玩开发者中心
 *
 * @author zhuangyuetao
 */
@Component
public class MuzhiwanImpl implements PlatformInterface {
    public static final String flag = "muzhiwan";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"token"};

    public static final String appKey = PTConfig.val("MuzhiwanAppKey");
    public static final String url = PTConfig.val("MuzhiwanVerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String token = params.get("token");
        String appkey = appKey;

        // 请求
        String resp = HttpUtils.create(url).addParam("token", token).addParam("appkey", appkey).get();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
        LoginResponse lr = null;
        if (rs.code == Resp.code_ok) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = rs.user.uid + "";
        } else {
            log.error(flag + "|{}|{}", rs.code, rs.msg);
            lr = new LoginResponse(1, rs.msg);
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

    public static class Resp {
        public static final int code_ok = 1;
        protected int code;
        protected String msg;
        protected User user;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public static class User {
            protected int uid;
            protected String username;
            protected int sex;
            protected String mail;
            protected String icon;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getMail() {
                return mail;
            }

            public void setMail(String mail) {
                this.mail = mail;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

        }

    }

    // public static void main(String[] args) {
    // String tokenKey = "a43cd7a510fc3b06792a0cb509b58415";
    // String sign = MD5.encode(appKey + tokenKey);
    //
    // System.out.println(sign);
    //
    // }

}
