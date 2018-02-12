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
 * 当乐
 *
 * @author xiezuojie
 */
@Component
public class DangLeImpl implements PlatformInterface {
    public static final String flag = "dangle";
    static final Logger log = LoggerFactory.getLogger(flag);

    String appId = PTConfig.val("DangLeAppId");
    String appKey = PTConfig.val("DangLeAppKey");
    String url = PTConfig.val("DangLeInfoUrl");

    String[] params = new String[]{"umid", "token"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "dangle";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String umid = params.get("umid");
        String token = params.get("token");

        String sig = MD5.encode(appId + "|" + appKey + "|" + token + "|" + umid);

        String resp = HttpUtils.create(url)
                .addParam("appid", appId)
                .addParam("umid", umid)
                .addParam("token", token)
                .addParam("sig", sig)
                .get();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        LoginResponse lr = null;
        if (rs.msg_code == 2000) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = umid;
        } else {
            log.error(flag + "|{}|{}", rs.msg_code, rs.msg_desc);
            lr = new LoginResponse(1, rs.msg_desc);
        }
        return lr;
    }

    /**
     * msg_code long 必须 状态码：见“1.2.3 状态码说明”。如果返回的数据没有该字段，说服服务器出了故障。<br>
     * msg_desc string 必须 状态码说明<br>
     * valid int 必须 0 为未验证，1 为有效，2为无效<br>
     * interval int 必须 请求频次限制时间段<br>
     * times int 必须 时间段内允许的最大请求次数<br>
     * roll boolean 必须 时间是否滚动<br>
     */
    static class Resp {
        long msg_code;
        String msg_desc;
        int valid;
        int interval;
        int times;
        boolean roll;

        public long getMsg_code() {
            return msg_code;
        }

        public void setMsg_code(long msg_code) {
            this.msg_code = msg_code;
        }

        public String getMsg_desc() {
            return msg_desc;
        }

        public void setMsg_desc(String msg_desc) {
            this.msg_desc = msg_desc;
        }

        public int getValid() {
            return valid;
        }

        public void setValid(int valid) {
            this.valid = valid;
        }

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public boolean isRoll() {
            return roll;
        }

        public void setRoll(boolean roll) {
            this.roll = roll;
        }

    }
}
