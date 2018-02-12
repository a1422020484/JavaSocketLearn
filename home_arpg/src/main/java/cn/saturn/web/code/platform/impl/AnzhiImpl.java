package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.HttpUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author zhuangyuetao
 */
@Component
public class AnzhiImpl implements PlatformInterface {
    public static final String flag = "anzhi";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"sid"};

    public static final String appId = PTConfig.val("AnzhiAppId");
    public static final String AnzhiAppKey = PTConfig.val("AnzhiAppKey");
    public static final String AnzhiAppSecret = PTConfig.val("AnzhiAppSecret");
    public static final String AnzhiVerifyUrl = PTConfig.val("AnzhiVerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String sid = params.get("sid");

        String sign = null;
        try {
            sign = Base64.encode((AnzhiAppKey + sid + AnzhiAppSecret).getBytes("utf-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String respCtx = HttpUtils.create(AnzhiVerifyUrl)
                .addParam("time", String.valueOf(System.currentTimeMillis()))
                .addParam("appkey", AnzhiAppKey)
                .addParam("sid", sid)
                .addParam("sign", sign)
                .post();

        Resp resp = null;
        try {
            resp = JSON.parseObject(respCtx, Resp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resp == null) {
            return LoginResponse.Timeout;
        }
        if (!"1".equals(resp.sc)) {
            return new LoginResponse(1, resp.st);
        }

        String uidJson = Base64.decodeAsString(resp.msg);
        JSONObject json = JSON.parseObject(uidJson);
        String uid = json.getString("uid");

        // 返回
        LoginResponse lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = uid;
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

    static class Resp {
        String sc;
        String st;
        String time;
        String msg;

        public String getSc() {
            return sc;
        }

        public void setSc(String sc) {
            this.sc = sc;
        }

        public String getSt() {
            return st;
        }

        public void setSt(String st) {
            this.st = st;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
