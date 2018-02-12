package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 狐狸平台
 *
 * @author zhuangyuetao
 */
@Component
public class OuwanImpl implements PlatformInterface {
    public static final String flag = "ouwan";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{};

    public static final String appId = PTConfig.val("OuwanAppId");
    public static final String appKey = PTConfig.val("OuwanAppKey");
    public static final String url = PTConfig.val("OuwanVerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String uid = params.get("uid");

        // 返回数据
        LoginResponse lr = new LoginResponse();
        lr.userInfo.account = uid;
        lr.setAutoRegister(false);
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
