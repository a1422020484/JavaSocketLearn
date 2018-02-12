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
 * @author zhuangyuetao
 */
@Component
public class ZhuoYiImpl implements PlatformInterface {
    public static final String flag = "zhuoyi";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"uid", "access_token"};

    public static final String ZhuoYiAppId = PTConfig.val("ZhuoYiAppId");
    public static final String ZhuoYiAppKey = PTConfig.val("ZhuoYiAppKey");
    public static final String ZhuoYiVerifyUrl = PTConfig.val("ZhuoYiVerifyUrl");
    public static final String ZhuoYiSecurtKey = PTConfig.val("ZhuoYiSecurtKey");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String uid = params.get("uid");
        String access_token = params.get("access_token");
        String sign = MD5.encode("uid=" + uid + "&access_token=" + access_token + "&app_id=" + ZhuoYiAppId + "&key=" + ZhuoYiAppKey);

        // 请求
        String resp = HttpUtils.create(ZhuoYiVerifyUrl).addParam("uid", uid).addParam("access_token", access_token).addParam("app_id", ZhuoYiAppId).addParam("sign", sign).get();
        Resp rs = null;
        try {
            rs = JSON.parseObject(resp, Resp.class);
        } catch (Exception e) {
            e.printStackTrace();

        }
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
        LoginResponse lr = null;
        if (rs.code == 0) {
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
        protected int code;
        protected String message;

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

    }
}
