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
 * 蜗牛平台
 *
 * @author zhuangyuetao
 */
@Component
public class WoniuImpl implements PlatformInterface {
    public static final String flag = "woniu";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"sessionId", "userid"};

    public static final String appId = PTConfig.val("WoniuAppId"); // client_Id
    public static final String appKey = PTConfig.val("WoniuAppKey"); // client_secret
    public static final String url = PTConfig.val("WoniuVerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String sessionId = params.get("sessionId");
        String userid = params.get("userid");
        String act = "4"; // 固定等于4

        StringBuilder strBuf = new StringBuilder();
        strBuf.append(appId);
        strBuf.append(act);
        strBuf.append(userid);
        strBuf.append(sessionId);
        strBuf.append(appKey);
        String sign = MD5.encode(strBuf.toString()).toLowerCase();

        // 请求
        String resp = HttpUtils.create(url).addParam("Act", act).addParam("Uin", userid).addParam("SessionId", sessionId).addParam("AppId", appId).addParam("Sign", sign).get();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
        LoginResponse lr = null;
        // 错误码:0=失败，1=成功(SessionId 有效)，2= AppId无效，3=Act器返回给应用无效，4=参数无效， 5= Sign无效， 11=SessionId无效
        if (rs.ErrorCode.equals("1")) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = userid;
        } else {
            log.error(flag + "|{}|{}", rs, rs);
            lr = new LoginResponse(1, String.valueOf(rs));
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
        protected String ErrorCode;
        protected String ErrorDesc;

        public String getErrorCode() {
            return ErrorCode;
        }

        public void setErrorCode(String errorCode) {
            ErrorCode = errorCode;
        }

        public String getErrorDesc() {
            return ErrorDesc;
        }

        public void setErrorDesc(String errorDesc) {
            ErrorDesc = errorDesc;
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
