package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import net.paoding.rose.web.Invocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class XYKaiYingImpl implements PlatformInterface {
	
	static final Logger log = LoggerFactory.getLogger("login");
	
    static final String flag = "xy";

    static final String XYAppId = PTConfig.val("XYAppId");
    static final String XYAppKey = PTConfig.val("XYAppKey");
    static final String XYVerifyUrl = PTConfig.val("XYVerifyUrl");

    @Autowired
    Invocation inv;

    static final String[] params = new String[]{"uid", "token"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return flag;
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String uid = params.get("uid");
        String token = params.get("token");

        String resp = HttpUtils.create(XYVerifyUrl)
                .addParam("uid", uid)
                .addParam("appid", XYAppId)
                .addParam("token", token)
                .post();
        log.info("xy|uid:{}|token:{}|appid:{}|resp:{}", uid, token, XYAppId, resp);
        
        Resp rs = null;
        try {
            rs = JSON.parseObject(resp, Resp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        if (!"0".equals(rs.ret)) {
            return new LoginResponse(1, rs.error);
        }

        LoginResponse lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = uid;

        return lr;
    }

    /*
     * 0 成功,是登录状态
     * 2 uid 不能为空
     * 20 缺少 APPID
     * 997 Token 过期
     * 999 验证码校验失败
     */
    static class Resp {
        String ret;
        String error;

        public String getRet() {
            return ret;
        }

        public void setRet(String ret) {
            this.ret = ret;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

}
