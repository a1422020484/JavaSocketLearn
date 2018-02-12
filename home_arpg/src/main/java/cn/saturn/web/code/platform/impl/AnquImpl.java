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

@Component
public class AnquImpl implements PlatformInterface {

    static final Logger log = LoggerFactory.getLogger("anqu");

    String appId = PTConfig.val("AnquAppId");
    String appKey = PTConfig.val("AnquAppKey");
    String appSecret = PTConfig.val("AnquAppSecret");
    String verifyUrl = PTConfig.val("AnquVerifyUrl");

    String[] params = new String[]{"userid", "sid"};

    @Override
    public LoginResponse login(Map<String, String> params) {
        // TODO Auto-generated method stub
        String userId = params.get("userid");
        String sid = params.get("sid");
        String sign = MD5.encode(userId + sid + appId + appSecret);
        String resp = HttpUtils.create(verifyUrl).addParam("appid", appId).addParam("uid", userId).addParam("vkey", sid)
                .addParam("sign", sign).post();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }
        LoginResponse lr = null;
        if ("0".equals(rs.status)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = userId;
        } else {
            log.error("安趣|{}|{}", rs.status, rs.msg);
            lr = new LoginResponse(Integer.parseInt(rs.status), rs.msg);
        }
        return lr;
    }

    @Override
    public String ptFlag() {
        // TODO Auto-generated method stub
        return "anqu";
    }

    @Override
    public String[] requireParams() {
        // TODO Auto-generated method stub
        return params;
    }

    static class Resp {
        String status;
        String msg;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
