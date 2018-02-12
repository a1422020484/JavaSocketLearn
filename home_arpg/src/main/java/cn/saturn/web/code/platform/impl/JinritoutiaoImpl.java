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
 * 今日头条
 *
 * @author zhuangyuetao
 */
@Component
public class JinritoutiaoImpl implements PlatformInterface {
    public static final String flag = "jrtt";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"accessToken", "uId"};

    public static final String appKey = PTConfig.val("JinritoutiaoAppKey"); // client_secret
    public static final String url = PTConfig.val("JinritoutiaoVerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String access_token = params.get("accessToken");
        String uid = params.get("uId");

        // 请求(不进行url解码)
        String resp = HttpUtils.create(url).addParam("client_key", appKey).addParam("access_token", access_token).addParam("uid", uid).urlDecode(false).get();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
        LoginResponse lr = null;
        if (rs.data != null && rs.data.verify_result == 1) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = uid;
        } else {
            log.error(flag + "|{}|{}", rs.data.error_code, rs.data.description);
            lr = new LoginResponse(1, rs.message);
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
        protected String message;
        protected Data data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public static class Data {
            protected int verify_result;
            protected int error_code;
            protected String description;

            public int getError_code() {
                return error_code;
            }

            public void setError_code(int error_code) {
                this.error_code = error_code;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getVerify_result() {
                return verify_result;
            }

            public void setVerify_result(int verify_result) {
                this.verify_result = verify_result;
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
