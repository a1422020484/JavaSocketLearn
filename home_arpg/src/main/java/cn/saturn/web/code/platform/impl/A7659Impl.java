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
 * 狐狸平台
 *
 * @author zhuangyuetao
 */
@Component
public class A7659Impl implements PlatformInterface {
    public static final String flag = "7659";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{};

    public static final String appKey = PTConfig.val("7659AppKey"); // client_secret
    public static final String url = PTConfig.val("7659VerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String token = params.get("token");
        String sign = MD5.encode(appKey + token);

        // 请求
        String resp = HttpUtils.create(url).addParam("tokenKey", token).addParam("sign", sign).post();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
        LoginResponse lr = null;
        if (rs.code == 0) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = rs.data.guid;
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
        protected int code;
        protected String msg;
        protected Data data;

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

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public static class Data {
            protected String guid;
            protected String username;
            protected String refer_type;
            protected String refer_name;

            public String getGuid() {
                return guid;
            }

            public void setGuid(String guid) {
                this.guid = guid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getRefer_type() {
                return refer_type;
            }

            public void setRefer_type(String refer_type) {
                this.refer_type = refer_type;
            }

            public String getRefer_name() {
                return refer_name;
            }

            public void setRefer_name(String refer_name) {
                this.refer_name = refer_name;
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
