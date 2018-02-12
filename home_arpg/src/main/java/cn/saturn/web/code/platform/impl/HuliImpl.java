package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
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
public class HuliImpl implements PlatformInterface {
    public static final String flag = "huli";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{};

    public static final String appId = PTConfig.val("HuliAppId");
    public static final String appKey = PTConfig.val("HuliAppKey");
    public static final String url = PTConfig.val("HuliVerifyUrl");

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
        String accessToken = params.get("accesstoken");
        String resp = HttpUtils.create(url).addParam("uid", uid).addParam("app_id", appId).addParam("token", accessToken).post();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        LoginResponse lr = null;
        if (rs.error == 0) {
            lr = new LoginResponse();
            lr.userInfo.account = uid;
        } else {
            log.error(flag + "|{}|{}", rs.getError(), rs.getMsg());
            lr = new LoginResponse(1, rs.getMsg());
        }
        return lr;
    }

    static class Resp {
        protected int error;
        protected String msg;

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }

}
