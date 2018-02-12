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
import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class JouYouPPImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("jouyoupp");

    int appId = PTConfig.intVal("JouYouAppId");
    String appKey = PTConfig.val("JouYouAppKey");
    String service = "account.verifySession";
    String verifyUrl = PTConfig.val("JouYouVerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {
        String sid = params.get("sid");
        RequestContent content = new RequestContent();
        content.setId(System.currentTimeMillis() / 1000L);
        content.setService(service);
        Data data = new Data();
        data.setSid(sid);
        content.setData(data);
        Game game = new Game();
        game.setGameId(appId);
        content.setGame(game);
        content.setEncrypt("MD5");
        String sign = MD5.encode(sid + appKey);
        content.setSign(sign);
        String resp = null;
        try {
            resp = HttpUtils.create(verifyUrl).post(JSON.toJSONString(content).getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Resp rs = JSON.parseObject(resp, Resp.class);
        if (rs == null)
            return LoginResponse.Timeout;

        LoginResponse lr = null;
        if (1 == rs.state.code) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = rs.data.creator + rs.data.accountId;
        } else {
            log.error("九游|{}|{}", sid, rs.state.msg);
            lr = new LoginResponse(1, rs.state.msg);
        }
        return lr;
    }

    @Override
    public String ptFlag() {
        return "ppzhushou";
    }

    String[] params = new String[]{"sid"};

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
