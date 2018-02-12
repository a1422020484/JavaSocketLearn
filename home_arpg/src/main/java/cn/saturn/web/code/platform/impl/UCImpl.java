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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class UCImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("uc");

    String appId = PTConfig.val("UCAppId");
    String appKey = PTConfig.val("UCAppKey");
    String gameId = PTConfig.val("UCGameId");
    String sdkUrl = PTConfig.val("UCSdkUrl");

    String[] params = new String[]{"sid"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "uc";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String sid = params.get("sid");

        Map<String, String> reqdata = new HashMap<>();
        reqdata.put("sid", sid);

        Map<String, String> gamedata = new HashMap<>();
        gamedata.put("gameId", gameId);

        String sig = MD5.encode("sid=" + sid + appKey);

        Map<String, Object> p = new HashMap<>();
        p.put("id", System.currentTimeMillis());
        p.put("data", reqdata);
        p.put("game", gamedata);
        p.put("sign", sig);

        String content = null;
        try {
            content = HttpUtils.create(sdkUrl).post(JSON.toJSONString(p).getBytes("utf-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        if (content == null) {
            return LoginResponse.Timeout;
        }
        Resp resp = null;
        try {
            resp = JSON.parseObject(content, Resp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resp == null) {
            return LoginResponse.Timeout;
        }

        LoginResponse lr = null;
        if ("1".equals(resp.state.code)) {
            Data data = JSON.parseObject(resp.data, Data.class);
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = data.accountId;
        } else {
//			log.error("UC|{}|{}|{}", sid, rs.state.code, rs.state.msg);
            lr = new LoginResponse(1, resp.state.msg);
        }
        return lr;
    }

    static class Resp {
        String id;
        State state;
        String data;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    static class Data {
        String accountId;
        String nickName;
        String creator;

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
    }

    static class State {
        String code;
        String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
