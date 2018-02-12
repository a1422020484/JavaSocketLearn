package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.Base64;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * oppo
 *
 * @author xiezuojie
 */
@Component
public class OppoImpl implements PlatformInterface {
    public static final String flag = "oppo";
    static final Logger log = LoggerFactory.getLogger(flag);

    String[] params = new String[]{"token", "ssoid"};

    public static final String appId = PTConfig.val("OppoAppId");
    public static final String appKey = PTConfig.val("OppoAppKey");
    public static final String appSecret = PTConfig.val("OppoAppSecret");
    public static final String verifyUrl = PTConfig.val("OppoVerifyUrl");

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String token0 = params.get("token");
        String token = token0;
        String ssoid0 = params.get("ssoid");
        String ssoid = ssoid0;
        try {
            // token = URLEncoder.encode(token, "UTF-8");
            token = token.replaceAll(" ", "+");// 外部解析时处理过一次把+解析回空格, 这里需要还原下.
            token = URLEncoder.encode(token, "UTF-8"); // 二次编码
            ssoid = URLEncoder.encode(ssoid, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        long timestamp = System.currentTimeMillis() / 1000;
        int random = (int) (Math.random() * 1000);
        String baseStr = OppoUtils.generateBaseString(String.valueOf(timestamp), String.valueOf(random), token0);
        String sign = OppoUtils.generateSign(baseStr);

        // 请求
        String resp = HttpUtils.create(verifyUrl).addParam("fileId", ssoid).addParam("token", token).addHeader("param", baseStr).addHeader("oauthsignature", sign).urlDecode(false).get();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        LoginResponse lr = null;
        if (rs.resultCode != null && rs.resultCode.equals("200") && rs.ssoid.equals(ssoid)) {
            // if (rs != null) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = ssoid;
        } else {
            log.error(flag + "|{}|{}", rs.resultCode, rs.resultMsg);
            lr = new LoginResponse(1, rs.resultMsg);
        }
        return lr;
    }

    /**
     * { "resultCode":"200","resultMsg":"正常 ","loginToken":xxx,"ssoid":123456,"appKey":null,"userName":abc,"email":null,"mobileNumber":null,"createTime":null,"userStatus":null}
     */
    static class Resp {
        String resultCode;
        String resultMsg;
        String loginToken;
        String ssoid;
        String appKey;
        String userName;
        String email;
        String mobileNumber;
        String createTime;
        String userStatus;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }

        public String getLoginToken() {
            return loginToken;
        }

        public void setLoginToken(String loginToken) {
            this.loginToken = loginToken;
        }

        public String getSsoid() {
            return ssoid;
        }

        public void setSsoid(String ssoid) {
            this.ssoid = ssoid;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

    }

    @Override
    public String ptFlag() {
        return flag;
    }

}

class OppoUtils {
    public static final String OAUTH_CONSUMER_KEY = "oauthConsumerKey";
    public static final String OAUTH_TOKEN = "oauthToken";
    public static final String OAUTH_SIGNATURE_METHOD = "oauthSignatureMethod";
    public static final String OAUTH_SIGNATURE = "oauthSignature";
    public static final String OAUTH_TIMESTAMP = "oauthTimestamp";
    public static final String OAUTH_NONCE = "oauthNonce";
    public static final String OAUTH_VERSION = "oauthVersion";
    public static final String CONST_SIGNATURE_METHOD = "HMAC-SHA1";
    public static final String CONST_SIGNATURE_METHOD0 = "HmacSHA1";
    public static final String CONST_OAUTH_VERSION = "1.0";

    public static String generateBaseString(String timestamp, String nonce, String token) {

        StringBuilder sb = new StringBuilder();
        try {
            sb.append(OAUTH_CONSUMER_KEY).append("=").append(URLEncoder.encode(OppoImpl.appKey, "UTF-8")).append("&").append(OAUTH_TOKEN).append("=").append(URLEncoder.encode(token, "UTF-8"))
                    .append("&").append(OAUTH_SIGNATURE_METHOD).append("=").append(URLEncoder.encode(CONST_SIGNATURE_METHOD, "UTF-8")).append("&").append(OAUTH_TIMESTAMP).append("=")
                    .append(URLEncoder.encode(timestamp, "UTF-8")).append("&").append(OAUTH_NONCE).append("=").append(URLEncoder.encode(nonce, "UTF-8")).append("&").append(OAUTH_VERSION).append("=")
                    .append(URLEncoder.encode(CONST_OAUTH_VERSION, "UTF-8")).append("&");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return sb.toString();
    }

    public static String generateSign(String baseStr) {
        byte[] byteHMAC = null;

        try {
            Mac mac = Mac.getInstance(CONST_SIGNATURE_METHOD0);
            SecretKeySpec spec = null;
            // String oauthSignature = encode(OppoImpl.appSecret) + "&";
            String oauthSignature = OppoImpl.appSecret + "&";
            spec = new SecretKeySpec(oauthSignature.getBytes(), CONST_SIGNATURE_METHOD0);
            mac.init(spec);
            byteHMAC = mac.doFinal(baseStr.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return URLEncoder.encode(String.valueOf(Base64.encode(byteHMAC)), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";

        // String key = OppoImpl.appSecret + "&";
        // return HmacSHA1Encryption.HmacSHA1Encrypt(baseStr, key);

    }
}
