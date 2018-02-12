package cn.saturn.web.code.platform;

import com.nearme.oauth.util.Constants;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

public class PlatformUtils {
    public static final boolean ptdebug = PTConfig.booleanVal("Debug");

    public static class SHA {

        public static String SHA1(String decript) {
            try {
                MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
                digest.update(decript.getBytes());
                byte messageDigest[] = digest.digest();
                // Create Hex String
                StringBuffer hexString = new StringBuffer();
                // 字节数组转换为 十六进制 数
                for (int i = 0; i < messageDigest.length; i++) {
                    String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                    if (shaHex.length() < 2) {
                        hexString.append(0);
                    }
                    hexString.append(shaHex);
                }
                return hexString.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public static String SHA2(String decript) {
            try {
                MessageDigest digest = java.security.MessageDigest.getInstance("SHA");
                digest.update(decript.getBytes());
                byte messageDigest[] = digest.digest();
                // Create Hex String
                StringBuffer hexString = new StringBuffer();
                // 字节数组转换为 十六进制 数
                for (int i = 0; i < messageDigest.length; i++) {
                    String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                    if (shaHex.length() < 2) {
                        hexString.append(0);
                    }
                    hexString.append(shaHex);
                }
                return hexString.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

    }

    public static class HMACSHA1 {

        private static final String MAC_NAME = "HmacSHA1";

        // public static byte[] hmacSHA1Encrypt(String encryptKey, String encryptText) throws InvalidKeyException, NoSuchAlgorithmException {
        // Mac mac = Mac.getInstance(MAC_NAME);
        // mac.init(new SecretKeySpec(StringUtil.getBytes(encryptKey), MAC_NAME));
        // return mac.doFinal(StringUtil.getBytes(encryptText));
        // }

        public static String hmacSHA1Encrypt(String encryptKey, String encryptText) {
            try {
                Mac mac = Mac.getInstance(MAC_NAME);
                mac.init(new SecretKeySpec(StringUtil.getBytes(encryptKey), MAC_NAME));
                byte[] buffer = mac.doFinal(StringUtil.getBytes(encryptText));
                return StringUtil.getString(buffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public static byte[] getHmacSHA1(String src) {
            try {
                Mac mac = Mac.getInstance("HmacSHA1");
                SecretKeySpec secret = new SecretKeySpec(Constants.APP_SECRET.getBytes("UTF-8"), mac.getAlgorithm());
                mac.init(secret);
                return mac.doFinal(src.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public static class StringUtil {
        public static final String UTF8 = "UTF-8";
        private static final byte[] BYTEARRAY = new byte[0];

        public static boolean isNullOrEmpty(String s) {
            if (s == null || s.isEmpty() || s.trim().isEmpty())
                return true;
            return false;
        }

        public static byte[] getBytes(String value) {
            return getBytes(value, UTF8);
        }

        public static byte[] getBytes(String value, String charset) {
            if (isNullOrEmpty(value)) {
                return BYTEARRAY;
            }
            if (isNullOrEmpty(charset)) {
                charset = UTF8;
            }
            try {
                return value.getBytes(charset);
            } catch (UnsupportedEncodingException e) {
                return BYTEARRAY;
            }
        }

        public static String getString(byte[] buffer) {
            try {
                return new String(buffer, UTF8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 生成签名
     * <p/>
     * 1.按照key字母排序
     * <p/>
     * 2.生成格式 key=a&b=2&c=2
     *
     * @param params
     * @return
     */
    public static String sign(Map<String, String> params, ISignHandler handler) {
        List<String> list = new ArrayList<>(params.keySet());
        // 按照自然升序处理
        Collections.sort(list);

        // 遍历参数
        int i = 0;
        StringBuffer strBuf = new StringBuffer();
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = params.get(key);
            // 创建并添加参数
            strBuf.append(handler.handler(key, value, i++));
        }
        String sign = handler.encode(strBuf.toString());
        return sign;
    }

    public interface ISignHandler {
        String handler(String key, String value, int index);

        String encode(String signStr);
    }

    /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;

    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String urlencode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
}
