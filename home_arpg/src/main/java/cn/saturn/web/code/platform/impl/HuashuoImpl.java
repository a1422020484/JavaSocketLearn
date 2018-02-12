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
 * 华硕
 *
 * @author zhuangyuetao
 */
@Component
public class HuashuoImpl implements PlatformInterface {
    public static final String flag = "huashuo";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"roleid", "roletoken"};

    public static final String appId = PTConfig.val("HuashuoAppId");
    public static final String appKey = PTConfig.val("HuashuoAppKey");
    public static final String appSecret = PTConfig.val("HuashuoAppSecretkey");
    public static final String url = PTConfig.val("HuashuoVerifyUrl");

    /**
     * 1 appid 应用 ID <br>
     * 2 apptoken 应用的密钥，即appkey <br>
     * 2 roleid 角色 ID，CP可以将一个角色看作一个独立玩家 <br>
     * 3 roletoken 角色 Token，CP可以将一个角色看作一个独立玩家 <br>
     * 4 sign 以上各参数的"GET形式字符串"+ "&{appSecretKey}" ，然后 MD5，即： MD5_32(appid=123&roleid=123&roletoken=123&key={AppSecretKey})，其 中 { AppSecretKey } 参数请联系闪现商务与后台同事 <br>
     */
    @Override
    public LoginResponse login(Map<String, String> params) {
        String roletoken = params.get("roletoken");
        String roleid = params.get("roleid");

        StringBuffer strBuf = new StringBuffer();
        strBuf.append("appid=");
        strBuf.append(appId);
        strBuf.append("&roleid=");
        strBuf.append(roleid);
        strBuf.append("&roletoken=");
        strBuf.append(roletoken);
        strBuf.append("&key=");
        strBuf.append(appSecret);    //别加{}, 就是个坑爹的文档
        strBuf.append("");
        String sign = MD5.encode(strBuf.toString());

        // 请求
        String resp = HttpUtils.create(url)
                .addParam("appid", appId)
                .addParam("roleid", roleid)
                .addParam("roletoken", roletoken)
                .addParam("apptoken", appKey)
                .addParam("sign", sign).get();
        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
        LoginResponse lr = null;
        if ("0".equals(rs.rescode)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = roleid;
        } else {
            log.error(flag + "|{}|{}", rs.rescode, rs.resmsg);
            lr = new LoginResponse(1, rs.resmsg);
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
        protected String rescode;
        protected String resmsg;

        public String getRescode() {
            return rescode;
        }

        public void setRescode(String rescode) {
            this.rescode = rescode;
        }

        public String getResmsg() {
            return resmsg;
        }

        public void setResmsg(String resmsg) {
            this.resmsg = resmsg;
        }

    }

}
