package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class TongBuTuiImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("tongbutui");

    int TongBuTuiAppId = PTConfig.intVal("TongBuTuiAppId");
    String TongBuTuiAppKey = PTConfig.val("TongBuTuiAppKey");
    String TongBuTuiVerifyUrl = PTConfig.val("TongBuTuiVerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String uid = params.get("uid");
        String sessionid = params.get("sessionid");
        String resp = HttpUtils.create(TongBuTuiVerifyUrl)
                .addParam("k", sessionid)
                .get();
        if (StringUtils.isBlank(resp)
                || !StringUtils.isNumeric(resp))
            return LoginResponse.Timeout;

        int ret = Integer.valueOf(resp);
        LoginResponse lr = null;
        if (ret > 0) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = uid;
        } else {
            log.error("同步推|{}", sessionid);
            lr = new LoginResponse(1, "错误");
        }
        return lr;
    }

    @Override
    public String ptFlag() {
        return "tongbu";
    }

    String[] params = new String[]{"uid", "sessionid"};

    @Override
    public String[] requireParams() {
        return params;
    }

    static class RequestContent {
        long id;
        String service;
        Data data;
        Game game;
        String encrypt;
        String sign;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public Game getGame() {
            return game;
        }

        public void setGame(Game game) {
            this.game = game;
        }

        public String getEncrypt() {
            return encrypt;
        }

        public void setEncrypt(String encrypt) {
            this.encrypt = encrypt;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }

    static class Data {
        String sid;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }
    }

    static class Game {
        int gameId;

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }
    }

    static class Resp {
        long id;
        State state;
        RespData data;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

        public RespData getData() {
            return data;
        }

        public void setData(RespData data) {
            this.data = data;
        }
    }

    static class State {
        int code;
        String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    static class RespData {
        String accountId;
        String creator;
        String nickName;

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

}
