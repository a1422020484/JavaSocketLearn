package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class HuaWeiImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("huawei");

    String HuaWeiAccessTokenSvc = PTConfig.val("HuaWeiAccessTokenSvc");
    String HuaWeiAPIUrl = PTConfig.val("HuaWeiAPIUrl");

    String[] params = new String[]{"access_token"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "huawei";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String token = params.get("access_token");
        try {
            token = URLEncoder.encode(token, "utf-8");
            token = token.replaceAll("\\+", "%2B");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String ctx = HttpUtils.create(HuaWeiAPIUrl)
                .addParam("nsp_svc", HuaWeiAccessTokenSvc)
                .addParam("nsp_ts", String.valueOf(System.currentTimeMillis() / 1000))
                .addParam("access_token", token)
                .urlDecode(false)
                .get();

        Resp rs = null;
        try {
            rs = JSON.parseObject(ctx, Resp.class);
        } catch (Exception e) {
        }
        if (rs == null) {
            return LoginResponse.ParamError;
        }

        LoginResponse lr = null;
        if (StringUtils.isNotBlank(rs.userID)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = rs.userID;
        } else {
            lr = LoginResponse.ParamError;
        }
        return lr;
    }

    /*
     * 重要提醒
     * 1、在验证请求时,请使用https协议;
     * 2、在填写access_token参数值前,请对access_token参数值进行编码并替换加号,例如,用java 编码方法为:java.net.URLEncoder.encode(access_token, "utf-8"),然后将符号“+”使用 replace(“+”, “%2B”)进行替换;
     * 3、返回值,只要存在userID就说明校验成功
     */
    static class Resp {
        String userID; // 用户ID
        String userName; // 用户名;优先显示昵称;没有昵称则显示帐号(隐藏部分字符)
        String languageCode; // 语言代码
        int userState; // 用户验证状态0:未验证1:已验证(该参数预 留,目前不作为是否合法登录的依据)
        int userValidStatus; // 用户有效状态 1 正常 2 暂停 3 销户(该参数预留,目前不作为是否合法登录的依据)

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }

        public int getUserState() {
            return userState;
        }

        public void setUserState(int userState) {
            this.userState = userState;
        }

        public int getUserValidStatus() {
            return userValidStatus;
        }

        public void setUserValidStatus(int userValidStatus) {
            this.userValidStatus = userValidStatus;
        }
    }
}
