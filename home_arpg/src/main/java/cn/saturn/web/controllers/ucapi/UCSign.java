package cn.saturn.web.controllers.ucapi;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author xiezuojie
 */
public class UCSign {

    public static void main(String[] args) throws Exception {
//        String caller = "ChangYou";
//        String secret = "123456";
//        String aesDataSecret = "1234567890123456";
//        HashMap<String, String> paramsMap = new HashMap<String, String>();
//
//        paramsMap.put("accountId", "abc123");
//        paramsMap.put("kaId", "123432532324");
//        paramsMap.put("serverId", "1");
//        paramsMap.put("roleId", "1");
//        //生成用于签名的原生请求数据
//        String jsonString = JSON.toJSONString(paramsMap);
//        System.out.println("jsonString=" + jsonString);
//        //请求数据加密处理
//        String params = encrypt(aesDataSecret, aesDataSecret, jsonString);
//        System.out.println("params=" + params);
//
//        String deJsonString = decrypt(aesDataSecret, aesDataSecret, params);
//        System.out.println("deJsonString=" + deJsonString);
//
//        paramsMap = new HashMap<String, String>();
//        paramsMap.put("params", params);
//        //生成数据签名
//        String sign = getSign(paramsMap, caller, secret);
//        System.out.println("sign=" + sign);

//        String source = "{\"gameId\":339400,\"platform\":2,\"page\":1,\"count\":20}";
//        String source = "{\"gameId\":339400,\"kaId\":\"3011\",\"count\":2}";
//        String source = "{\"accountId\":\"123\",\"gameId\":\"339400\",\"kaId\":\"3011\",\"serverId\":\"1\",\"roleId\":\"51\"}";
//        String source = "{\"accountId\":\"xzj0037@t.com\",\"gameId\":\"339400\",\"platform\":2}";
//        String encode = encrypt("s1m2Hq433d2mk93E", "s1m2Hq433d2mk93E", source);
//        System.out.println(encode);
//        String desource = decrypt("s1m2Hq433d2mk93E", "s1m2Hq433d2mk93E", encode);
//        System.out.println(desource);
        String desource = decrypt("s1m2Hq433d2mk93E", "s1m2Hq433d2mk93E", "fw21C7/zw/eB2GtI9Q7+zJF+H12UXtHFJlK4/T0oILQh3FK1f6kkKrgot7mo+LmgXaTKy2yABVQ5rHvkfFlE7kKRkyGW8mZBUPqevRc42Iw=");
        System.out.println(desource);
    }

    public static String getSign(HashMap<String, String> params, String caller, String secret) {
        Map<String, String> formatParam = new HashMap<String, String>();
        if (params != null) {
            formatParam = formatParamMap(params);
        }
        String sortContent = getSortStrFromMap(formatParam, null, false);
        String sign = hexMD5(caller + sortContent + secret).toLowerCase();
        return sign;
    }

    public static Map<String, String> formatParamMap(Map<String, String> param) {
        Map<String, String> formatMap = new HashMap<String, String>();
        for (Iterator<String> i = param.keySet().iterator(); i.hasNext(); ) {
            String key = i.next();
            String value = param.get(key);
            String val = value == null ? "" : value;
            formatMap.put(key, val);
        }
        return formatMap;
    }

    public static String getSortStrFromMap(Map<String, String> params, String[] notIn,
                                           boolean withChar) {
        StringBuffer content = new StringBuffer();

        // 按照key做排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);

            if (notIn != null && Arrays.asList(notIn).indexOf(key) >= 0)
                continue;

            String value = params.get(key) != null ? params.get(key).toString() : null;

            if (withChar == true) {
                if (value != null) {
                    content.append((index == 0 ? "" : "&") + key + "=" + value);
                } else {
                    content.append((index == 0 ? "" : "&") + key + "=");
                }
            } else {
                if (value != null) {
                    content.append(key + "=" + value);
                } else {
                    content.append(key + "=");
                }
            }
            index++;
        }
        return content.toString();
    }

    /**
     * 字符串MD5处理
     *
     * @param value
     * @return
     */
    public static String hexMD5(String value) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(value.getBytes("utf-8"));
            byte[] digest = messageDigest.digest();
            return String.valueOf(Hex.encodeHex(digest));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String key, String iv, String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
        int blockSize = cipher.getBlockSize();
        byte[] dataBytes = text.getBytes();

        int plaintextLen = dataBytes.length;
        if (plaintextLen % blockSize != 0) {
            plaintextLen = plaintextLen + (blockSize - plaintextLen % blockSize);
        }
        byte[] plaintext = new byte[plaintextLen];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encryptBytes = cipher.doFinal(plaintext);
        return new sun.misc.BASE64Encoder().encode(encryptBytes);
    }

    public static String decrypt(String key, String iv, String text) throws Exception {
        byte[] baseDecryptBytes = new sun.misc.BASE64Decoder().decodeBuffer(text);
        Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] orginBytes = cipher.doFinal(baseDecryptBytes);
        return new String(orginBytes).trim();
    }
}
