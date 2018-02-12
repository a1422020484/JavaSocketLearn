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
 * 魅族平台
 *
 * @author zhuangyuetao
 */
@Component
public class MeizuImpl implements PlatformInterface {
    public static final String flag = "meizu";
    public static final Logger log = LoggerFactory.getLogger("login");
    public static final String[] params = new String[]{"sessionId", "uid"};

    public static final String appId = PTConfig.val("MeizuAppId");
    public static final String appKey = PTConfig.val("MeizuAppKey");
    public static final String appSecret = PTConfig.val("MeizuAppSecret");
    public static final String url = PTConfig.val("MeizuVerifyUrl");

    /**
     * app_id long Y 游戏 id <br>
     * session_id String Y sessionId <br>
     * uid long Y 用户 id <br>
     * ts long Y timestamp.eg: 1396424644001 <br>
     * sign_type String Y 常量:md5 <br>
     * sign String Y 签数签名 <br>
     */
    @Override
    public LoginResponse login(Map<String, String> params) {
        // String app_id = params.getAccount("app_id");
        String app_id = appId;
        String session_id = params.get("sessionId");
        String uid = params.get("uid");
        long ts = System.currentTimeMillis();
        String sign_type = "md5";
        // 签名
        String sign0 = "app_id=" + app_id + "&session_id=" + session_id + "&ts=" + ts + "&uid=" + uid + ":" + appSecret;
        String sign = MD5.encode(sign0);
        
        log.info(flag + "|sign0:{}", sign0);

        // 请求
        String resp = HttpUtils.create(url).addParam("app_id", app_id).addParam("session_id", session_id).addParam("uid", uid).addParam("ts", String.valueOf(ts)).addParam("sign_type", sign_type)
                .addParam("sign", sign).post();
        log.info(flag + "|resp:{}", resp);
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
        LoginResponse lr = null;
        if (rs.code.equals("200")) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = uid;
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
        protected String code;
        protected String message;
        protected String value;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
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
