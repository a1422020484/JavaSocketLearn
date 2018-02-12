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
 * 游戏群/熊猫玩
 *
 * @author zhuangyuetao
 */
@Component
public class YouxiqunImpl implements PlatformInterface {
    public static final String flag = "youxiqun";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{};

    public static final String cliendId = PTConfig.val("YouxiqunClientID"); // client_Id
    public static final String clientSecret = PTConfig.val("YouxiqunClientSecret"); // client_secret
    public static final String url = PTConfig.val("YouxiqunVerifyUrl");

    /**
     * 接口地址 ~/v2/oauth2/access_token<br>
     * 调用方法 POST<br>
     * 调用参数<br>
     * 参数名 必传 类型 说明<br>
     * client_id 是 字符 游戏的客户端号<br>
     * client_secret 是 字符 游戏的客户端密钥<br>
     * grant_type 是 字符 获取授权码的依据类型可传 refresh_token 和 authorization_code 两种<br>
     * code 是 字符 当使用 authorization_code 方式获取授权码时, 本参数必传并填入 authorization_code 的内容<br>
     * refresh_token 是 字符 当使用 refresh_token 方式获取授权码时, 本参数必传并填入 refresh_token 的内容<br>
     * 返回内容<br>
     * {<br>
     * "access_token": "a9e652e8070ea8a2da893c804a5c5c1220cdd520", // 授权码<br>
     * "refresh_token": "e8d45d2bb0345b08b1daed535fb7d9572c3cac1e", // 刷新授权码3<br>
     * "expires_in": 1209600 // 有效时长 1209600 秒<br>
     * }<br>
     */
    @Override
    public LoginResponse login(Map<String, String> params) {
        String grant_type = "authorization_code"; // 获取授权码的依据类型可传 refresh_token 和 authorization_code 两种
        String code = params.get("code");

        // 请求
        String resp = HttpUtils.create(url).addParam("client_id", cliendId).addParam("client_secret", clientSecret).addParam("grant_type", grant_type).addParam("code", code).post();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
        // LoginResponse lr = null;
        // if (rs.code == Resp.code_ok) {
        // lr = new LoginResponse();
        // lr.userInfo.account = rs.result.uid;
        // } else {
        // log.error(flag + "|{}|{}", rs.code, rs.message);
        // lr = new LoginResponse(1, rs.message);
        // }
        // return lr;

        return null;
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
        protected String access_token;
        protected String refresh_token;
        protected int expires_in;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
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
