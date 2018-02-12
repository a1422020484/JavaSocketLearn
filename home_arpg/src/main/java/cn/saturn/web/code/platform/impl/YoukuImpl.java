package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HmacMD5;
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
public class YoukuImpl implements PlatformInterface {
    public static final String flag = "youku";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"sessionid"};

    public static final String appId = PTConfig.val("YoukuAppId"); // client_Id
    public static final String appKey = PTConfig.val("YoukuAppKey"); // client_secret
    public static final String payKey = PTConfig.val("YoukuPayKey");
    public static final String url = PTConfig.val("YoukuVerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String sessionid = params.get("sessionid");
        String sign = "appkey=" + appKey + "&sessionid=" + sessionid;
        sign = HmacMD5.hmac(sign, payKey);
        // 请求
        String resp = HttpUtils.create(url).addParam("sessionid", sessionid).addParam("appkey", appKey).addParam("sign", sign).post();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
        LoginResponse lr = null;
        if (rs.status.equals("success")) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = rs.uid;
        } else {
            log.error(flag + "|{}|{}", 0, rs.status);
            lr = new LoginResponse(1, rs.status);
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
        protected String status;
        protected String uid;
        protected String code;
        protected String desc;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

    }

    public static void main(String[] args) {
        // String tokenKey = "a43cd7a510fc3b06792a0cb509b58415";
        // String sign = MD5.encode(appKey + tokenKey);
        //
        // System.out.println(sign);

        String resp = "{\"status\":\"failed\",\"code\":403,\"desc\":\"\u6570\u5b57\u7b7e\u540d\u9519\u8bef3\"}";
        Resp rs = JSON.parseObject(resp, Resp.class);
        System.out.println(resp);

    }

}
