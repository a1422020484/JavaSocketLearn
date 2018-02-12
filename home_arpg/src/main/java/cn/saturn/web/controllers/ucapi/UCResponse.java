package cn.saturn.web.controllers.ucapi;

import com.alibaba.fastjson.JSON;

/**
 * @author xiezuojie
 */
public class UCResponse {

    public static final State STATE_2000000 = new State(2000000, "成功");
    public static final State STATE_5000000 = new State(5000000, "服务器内部错误");
    public static final State STATE_5000010 = new State(5000010, "Caller错误");
    public static final State STATE_5000011 = new State(5000011, "签名错误");
    public static final State STATE_5000012 = new State(5000012, "AES加/解密错误");
    public static final State STATE_5000020 = new State(5000020, "业务参数无效,错误");
    public static final State STATE_5000030 = new State(5000030, "gameId错误");
    public static final State STATE_5000031 = new State(5000031, "accountId错误");
    public static final State STATE_5000032 = new State(5000032, "礼包编号错误");
    public static final State STATE_5000033 = new State(5000033, "数量错误");
    public static final State STATE_5000034 = new State(5000034, "角色信息错误");
    public static final State STATE_5000035 = new State(5000035, "区服信息错误");
    public static final State STATE_5000036 = new State(5000036, "同角色一天不能领取多次");
    public static final State STATE_5000050 = new State(5000050, "其它错误");

    public long id; // 请求的唯一标识, Unix时间戳,与请求内容中的id值相同.
    public State state; // 响应状态, json
    public String data; // 响应数据, json

    public UCResponse() {
    }

    /**
     *
     * @param id
     * @param state
     * @param encryptedData 加密后的内容
     */
    public UCResponse(long id, State state, String encryptedData) {
        this.id = id;
        this.state = state;
        this.data = encryptedData;
    }

    /**
     *
     * @param id
     * @param state
     * @param data 加密前的内容
     * @param AESKey 加密用的AESKey
     */
    public UCResponse(long id, State state, String data, String AESKey) {
        this.id = id;
        this.state = state;
        try {
            this.data = UCSign.encrypt(AESKey, AESKey, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

    public static class State {
        public int code; // 响应码
        public String msg; // 结果描述

        public State(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String jsonString() {
            return JSON.toJSONString(this);
        }
    }

}
