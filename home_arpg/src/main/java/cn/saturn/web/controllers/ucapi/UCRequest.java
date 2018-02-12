package cn.saturn.web.controllers.ucapi;

import xzj.core.util.MD5;

/**
 * @author xiezuojie
 */
public class UCRequest {

    public long id; // 请求的唯一标识, Unix 时间戳, 10位
    public UCRequestData data; // 请求数据, json格式
    public UCRequestClient client; // 调用者信息, json, 格式: {"caller":"调用者标识", "ex":"扩展参数"}
    public String encrypt; // 加密方式, 支持MD5
    public String sign; // 签名参数, MD5(caller+签名内容+apiKey)

    public UCRequest() {
        data = UCRequestData.EMPTY;
        client = UCRequestClient.EMPTY;
        encrypt = "";
        sign = "";
    }

    /**
     * 解密数据
     * @param AESKey
     */
    public void decrypt(String AESKey) {
        try {
            data.decryptedParams = UCSign.decrypt(AESKey, AESKey, data.params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param caller
     * @param apiKey
     * @return Sign是否验证通过
     */
    public boolean verifySign(String caller, String apiKey) {
        try {
            String mySign = MD5.encode(caller + "params=" + data.params + apiKey).toLowerCase();
            return mySign.equals(sign);
//            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static final UCRequest EMPTY = new UCRequest();

    public static class UCRequestClient {
        public static final UCRequestClient EMPTY = UCRequestClient.empty();

        public String caller; // 调用者标识
        public String ex; // 扩展参数

        public UCRequestClient() {}

        public static UCRequestClient empty() {
            UCRequestClient client = new UCRequestClient();
            client.caller = "";
            client.ex = "";
            return client;
        }
    }

    public static class UCRequestData {
        public static final UCRequestData EMPTY = UCRequestData.empty();

        public String params; // 加密后的内容
        public String decryptedParams; // 解密后的内容

        public UCRequestData() {}

        public static UCRequestData empty() {
            UCRequestData data = new UCRequestData();
            data.params = "";
            data.decryptedParams = "";
            return data;
        }
    }
}
