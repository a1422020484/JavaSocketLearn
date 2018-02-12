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
 * 泡椒
 *
 * @author zhuangyuetao
 */
@Component
public class PaojiaoImpl implements PlatformInterface {
    public static final String flag = "paojiao";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"token"};

    public static final String appId = PTConfig.val("PaojiaoAppId");
    public static final String appKey = PTConfig.val("PaojiaoAppKey");
    public static final String appSecret = PTConfig.val("PaojiaoAppSecret");
    public static final String url = PTConfig.val("PaojiaoVerifyUrl");

    /**
     * token 泡椒SDK登录成功后返回的会话标识<br>
     * appId 在泡椒网获取的appId<br>
     * sign 生成规则：MD5(appId+token+serverSecret)<br>
     */
    @Override
    public LoginResponse login(Map<String, String> params) {
        String token = params.get("token");
        String sign = MD5.encode(appId + token + appSecret);

        // 请求
        String resp = HttpUtils.create(url).addParam("appId", appId).addParam("token", token).addParam("sign", sign).post();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
        LoginResponse lr = null;
        if (rs.code.equals("1")) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = rs.data.uid;
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
        protected String code;
        protected String msg;
        protected Data data;

        public static class Data {
            protected String avatar;
            protected String uid;
            protected String userName;
            protected String token;
            protected String niceName;
            protected String createdTime;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getNiceName() {
                return niceName;
            }

            public void setNiceName(String niceName) {
                this.niceName = niceName;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
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
