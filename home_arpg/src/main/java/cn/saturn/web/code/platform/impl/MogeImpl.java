package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.util.Map;

/**
 * pengyowuan平台
 *
 * @author zhuangyuetao
 */
@Component
public class MogeImpl implements PlatformInterface {
    public static final String flag = "moge";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"username", "token", "sign"};

    public static final String gameId = PTConfig.val("MogeGameId");
    public static final String appId = PTConfig.val("MogeAppId");
    public static final String appKey = PTConfig.val("MogeAppKey");
    public static final String url = PTConfig.val("MogeVerifyUrl");

    /**
     * 参数 类型 说明 是否必须<br>
     * username String 玩家平台帐号 是<br>
     * token String 登录时返回令牌 是<br>
     * pid int 为我方分配参数 是<br>
     * sign String 数据签名，算法如下 是<br>
     * ●签名加密说明<br>
     * sign = md5("pid="+pid+"&token="+token+"&username="+username+key)<br>
     * 密钥 key由我方分配<br>
     */
    @Override
    public LoginResponse login(Map<String, String> params) {
        String data = params.get("username");
        String token = params.get("token");
        String sign = params.get("sign");
        String[] username = data.split("\\|", 2);// "\\|"转义字符，数组长度位2
        // ，0是读的第一个，1是读的第二个
        String logintime = username[0];
        String username1 = username[1];
        // 生成签名
        StringBuffer strBuf = new StringBuffer();

        strBuf.append("username=");
        strBuf.append(username1);
        strBuf.append("&appkey=");
        strBuf.append(appKey);
        strBuf.append("&logintime=");
        strBuf.append(logintime);
        String loginsign = MD5.encode(strBuf.toString());

        LoginResponse lr = null;
        if (loginsign.equals(sign)) {

        } else {
            lr = new LoginResponse(1, "sign error");
            return null;
        }

        String resp = HttpUtils.create(url).addParam("username", username1).addParam("memkey", token).post();
        lr = null;
        if (resp.equals("success")) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = username1;
        } else {
            lr = LoginResponse.ParamError;
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
        String msg;
        String data;
        protected String token;

        public String msg() {
            return msg;
        }

        public void msg(String msg) {
            this.msg = msg;
        }

        public String getToken() {
            return token;
        }

        public void setUsername(String token) {
            this.token = token;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

}
