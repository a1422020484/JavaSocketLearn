package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 熊猫玩
 *
 * @author zhuangyuetao
 */
@Component
public class XiongMaoWanImpl implements PlatformInterface {
    public static final String flag = "xiongmaowan";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"code"};

    public static final String XiongMaoWanId = PTConfig.val("XiongMaoWanId");
    public static final String XiongMaoWanSecret = PTConfig.val("XiongMaoWanSecret");
    public static final String XiongMaoWanAccessToken = PTConfig.val("XiongMaoWanAccessToken");
    public static final String XiongMaoWanGetUser = PTConfig.val("XiongMaoWanGetUser");
    public static final boolean Debug = PTConfig.booleanVal("Debug");
    public static final String XiongMaoWanHost = Debug ? PTConfig.val("XiongMaoWanHostDebug") : PTConfig.val("XiongMaoWanHost");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String code = params.get("code");
//		String token = params.getAccount("refresh_token");

        String resp = HttpUtils.create(XiongMaoWanHost + XiongMaoWanAccessToken)
                .addParam("client_id", XiongMaoWanId)
                .addParam("client_secret", XiongMaoWanSecret)
                .addParam("grant_type", "authorization_code")
                .addParam("code", code)
                .addParam("refresh_token", "")
                .post();
        JSONObject json = JSON.parseObject(resp);
        String access_token = json.getString("access_token"); // OAuth2验证通过后返回的访问授权码。
//		String refresh_token = json.getString("refresh_token");

        String resp2 = HttpUtils.create(XiongMaoWanHost + XiongMaoWanGetUser)
                .addParam("access_token", access_token)
                .get();
        JSONObject json2 = JSON.parseObject(resp2);
        /*
		 * {
			"xmw_open_id": "XMWC1U1",
			"nickname": "小峰",
			"avatar": "http://www.xmwan.com/71590343432359e02/avatar.jpg",
			"gender": 1
			}
		 */
        String uid = json2.getString("xmw_open_id");
        if (StringUtils.isBlank(uid)) {
            return LoginResponse.ParamError;
        }

        // 返回数据
        LoginResponse lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = uid;
        lr.setExt(access_token);
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

}
