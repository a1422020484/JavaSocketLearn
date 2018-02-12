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

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 狐狸平台
 *
 * @author zhuangyuetao
 */
@Component
public class A4399Impl implements PlatformInterface {
    public static final String flag = "4399";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"state", "uid"};

    public static final String appKey = PTConfig.val("4399AppKey"); // client_secret
    public static final String url = PTConfig.val("4399VerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String state = params.get("state");
        String uid = params.get("uid");

        // 进行url加密
        try {
            state = java.net.URLEncoder.encode(state, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 请求(不进行url解码)
        String resp = HttpUtils.create(url).addParam("state", state).addParam("uid", uid).urlDecode(false).get();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
        LoginResponse lr = null;
        if (rs.code == Resp.code_ok) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = rs.result.uid;
        } else {
            log.error(flag + "|{}|{}", rs.code, rs.message);
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
        public static final int code_ok = 100;
        public static final int code_paramerr = 87; // 参数不全
        public static final int code_checkerr = 85; // 验证失败
        public static final int code_ttimeout = 82; // 验证成功、但state有效期超时，重置state
        protected int code;
        protected String message;
        protected Result result;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public static class Result {
            protected String uid;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
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
