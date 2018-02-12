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
 * 07073平台
 *
 * @author zhuangyuetao
 */
@Component
public class A07073Impl implements PlatformInterface {
    public static final String flag = "07073";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"username", "token"};

    public static final String appId = PTConfig.val("07073AppId");
    public static final String pId = PTConfig.val("07073PId");
    public static final String secretkey = PTConfig.val("07073AppSecretkey");
    public static final String url = PTConfig.val("07073VerifyUrl");

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
        String token = params.get("token");
        String username = params.get("username");
        // String token = params.getAccount("username");
        // String username = params.getAccount("token");

        // 生成签名
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("pid=");
        strBuf.append(pId);
        strBuf.append("&token=");
        strBuf.append(token);
        strBuf.append("&username=");
        strBuf.append(username);
        strBuf.append(secretkey);
        String sign = MD5.encode(strBuf.toString());

        // 请求
        String resp = HttpUtils.create(url).addParam("username", username).addParam("token", token).addParam("pid", pId).addParam("sign", sign).urlDecode(false).post();
        resp = resp.replace("\"data\":[]", "\"data\":{}"); // 空字符处理
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
        LoginResponse lr = null;
        if (rs.state == 1) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = rs.data.uid;
        } else {
            log.error(flag + "|{}|{}", rs.state, rs.msg);
            lr = new LoginResponse(1, rs.msg);
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
        protected int state;
        protected String msg;
        protected Data data;

        public static class Data {
            protected String uid;
            protected String username;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
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
