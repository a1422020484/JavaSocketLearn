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

@Component
public class KuaiWanImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("kuaiwan");

    String checkUrl = PTConfig.val("KuaiWanCheckUrl");

    @Override
    public String ptFlag() {
        return "kuaiwan";
    }

    String[] params = new String[]{"sid"};

    @Override
    public LoginResponse login(Map<String, String> params) {
        String sid = params.get("sid");
        String resp = HttpUtils.create(checkUrl)
                .addParam("sid", sid)
                .get();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null)
            return LoginResponse.Timeout;

        LoginResponse lr = null;
        if (rs.ok) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = rs.data.user_id;
        } else {
            log.error("快玩|{}|{}", sid, rs.reason);
            lr = new LoginResponse(1, rs.reason);
        }
        return lr;
    }

    @Override
    public String[] requireParams() {
        return params;
    }

    static class Resp {
        boolean ok;
        Data data;
        String reason;

        public boolean isOk() {
            return ok;
        }

        public void setOk(boolean ok) {
            this.ok = ok;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    static class Data {
        String user_id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
