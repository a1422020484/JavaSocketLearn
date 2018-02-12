package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.Base64;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * 金立
 *
 * @author xiezuojie
 */
@Component
public class JinliImpl implements PlatformInterface {
    static final String PT = "jinli";
    static final Logger log = LoggerFactory.getLogger("login");

    private static final String JinLiApiKey = PTConfig.val("JinLiApiKey");
    private static final String JinLiSecretKey = PTConfig.val("JinLiSecretKey");
    private static final String JinLiVerifyUrl = PTConfig.val("JinLiVerifyUrl");
    private static final String JinLiVerifyapi = PTConfig.val("JinLiVerifyapi");
    private static final String JinLiHost = PTConfig.val("JinLiHost");

    private static final String port = "443";
    private static final String method = "POST";

    @Override
    public String ptFlag() {
        return PT;
    }

    String[] params = new String[]{"amigoToken"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String amigoToken = params.get("amigoToken");
        log.debug("jinli|amigoToken:{}|JinLiApiKey:{}|JinLiSecretKey:{}|JinLiVerifyUrl:{}|JinLiVerifyapi:{}|JinLiHost:{}", amigoToken, JinLiApiKey, JinLiSecretKey, JinLiVerifyUrl, JinLiVerifyapi, JinLiHost);

        String content = null;
        try {
            content = verify(amigoToken);
            log.debug("jinli|verify content:{}", content);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (StringUtils.isBlank(content)) {
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

        // 如果存在'r',表示异常
        if (StringUtils.isNotBlank(resp.r)) {
            return new LoginResponse(1, "Jinli:" + resp.r);
        }

        LoginResponse lr = null;
        if (StringUtils.isNotBlank(resp.u)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = resp.u;
        } else {
            lr = new LoginResponse(1, "Jinli u err");
        }
        return lr;
    }

    static class Resp {
        /*
         * 金立的程序真是有病
         * 1. 帐号验证搞的这么复杂,就像是这么做安全性就高了一样,SB
         * 2. 返回的字符用缩写,开发文档上也没写明各缩写的用意,真折磨人,脑子里装的都是尿吧
         */
        String r; // 可选 账号安全验证返回状态码。失败时才出 现,具体含义请见“5.1.2.3 返回状态码说 明”
        String wid; // 可选 失败时才出现,为UUID字符串,用于错 误跟踪,见 “返回状态码说明”
        String u; // user id
        String tn;
        String na;
        String ptr;
        String ul;
        String sty;
        String ply;

        public String getR() {
            return r;
        }

        public void setR(String r) {
            this.r = r;
        }

        public String getWid() {
            return wid;
        }

        public void setWid(String wid) {
            this.wid = wid;
        }

        public String getU() {
            return u;
        }

        public void setU(String u) {
            this.u = u;
        }

        public String getTn() {
            return tn;
        }

        public void setTn(String tn) {
            this.tn = tn;
        }

        public String getNa() {
            return na;
        }

        public void setNa(String na) {
            this.na = na;
        }

        public String getPtr() {
            return ptr;
        }

        public void setPtr(String ptr) {
            this.ptr = ptr;
        }

        public String getUl() {
            return ul;
        }

        public void setUl(String ul) {
            this.ul = ul;
        }

        public String getSty() {
            return sty;
        }

        public void setSty(String sty) {
            this.sty = sty;
        }

        public String getPly() {
            return ply;
        }

        public void setPly(String ply) {
            this.ply = ply;
        }
    }

    static class Ply {
        String a;
        String pid;
        String na;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getNa() {
            return na;
        }

        public void setNa(String na) {
            this.na = na;
        }
    }


    // verify 方法封装了 验证方法，调用此方法即可完成帐号安全验证
    public static String verify(String amigoToken) {
        HttpsURLConnection httpURLConnection = null;
        OutputStream out;

        TrustManager[] tm = {new MyX509TrustManager()};
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL sendUrl = new URL(JinLiVerifyUrl);
            httpURLConnection = (HttpsURLConnection) sendUrl.openConnection();
            httpURLConnection.setSSLSocketFactory(ssf);
            httpURLConnection.setDoInput(true); // true表示允许获得输入流,读取服务器响应的数据,该属性默认值为true
            httpURLConnection.setDoOutput(true); // true表示允许获得输出流,向远程服务器发送数据,该属性默认值为false
            httpURLConnection.setUseCaches(false); // 禁止缓存
            int timeout = 3000;
            httpURLConnection.setReadTimeout(timeout); // 30秒读取超时
            httpURLConnection.setConnectTimeout(timeout); // 30秒连接超时
            String method = "POST";
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Authorization", builderAuthorization());
            out = httpURLConnection.getOutputStream();
            out.write(amigoToken.getBytes());
            out.flush();
            out.close();
            InputStream in = httpURLConnection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = in.read(buff)) != -1) {
                buffer.write(buff, 0, len);
            }
            return buffer.toString();

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String builderAuthorization() {

        Long ts = System.currentTimeMillis() / 1000;
        String nonce = StringUtil.randomStr().substring(0, 8);
        String mac = CryptoUtility.macSig(JinLiHost, port, JinLiSecretKey, ts.toString(), nonce, method, JinLiVerifyapi);
        mac = mac.replace("\n", "");
        StringBuilder authStr = new StringBuilder();
        authStr.append("MAC ");
        authStr.append(String.format("id=\"%s\"", JinLiApiKey));
        authStr.append(String.format(",ts=\"%s\"", ts));
        authStr.append(String.format(",nonce=\"%s\"", nonce));
        authStr.append(String.format(",mac=\"%s\"", mac));
        return authStr.toString();
    }

    static class MyX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    static class CryptoUtility {

        private static final String MAC_NAME = "HmacSHA1";

        public static String macSig(String host, String port, String macKey, String timestamp, String nonce, String method, String uri) {
            // 1. build mac string
            // 2. hmac-sha1
            // 3. base64-encoded

            StringBuffer buffer = new StringBuffer();
            buffer.append(timestamp).append("\n");
            buffer.append(nonce).append("\n");
            buffer.append(method.toUpperCase()).append("\n");
            buffer.append(uri).append("\n");
            buffer.append(host.toLowerCase()).append("\n");
            buffer.append(port).append("\n");
            buffer.append("\n");
            String text = buffer.toString();

            byte[] ciphertext = null;
            try {
                ciphertext = hmacSHA1Encrypt(macKey, text);
            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            }

            String sigString = Base64.encode(ciphertext);
            return sigString;
        }

        public static byte[] hmacSHA1Encrypt(String encryptKey, String encryptText) throws InvalidKeyException, NoSuchAlgorithmException {
            Mac mac = Mac.getInstance(MAC_NAME);
            mac.init(new SecretKeySpec(StringUtil.getBytes(encryptKey), MAC_NAME));
            return mac.doFinal(StringUtil.getBytes(encryptText));
        }

    }

    static class StringUtil {
        public static final String UTF8 = "UTF-8";
        private static final byte[] BYTEARRAY = new byte[0];

        public static boolean isNullOrEmpty(String s) {
            if (s == null || s.isEmpty() || s.trim().isEmpty())
                return true;
            return false;
        }

        public static String randomStr() {
            return CamelUtility.uuidToString(java.util.UUID.randomUUID());
        }

        public static byte[] getBytes(String value) {
            return getBytes(value, UTF8);
        }

        public static byte[] getBytes(String value, String charset) {
            if (isNullOrEmpty(value))
                return BYTEARRAY;
            if (isNullOrEmpty(charset))
                charset = UTF8;
            try {
                return value.getBytes(charset);
            } catch (UnsupportedEncodingException e) {
                return BYTEARRAY;
            }
        }
    }

    static class CamelUtility {
        public static final int SizeOfUUID = 16;
        private static final int SizeOfLong = 8;
        private static final int BitsOfByte = 8;
        private static final int MBLShift = (SizeOfLong - 1) * BitsOfByte;

        private static final char[] HEX_CHAR_TABLE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        public static String uuidToString(java.util.UUID uuid) {
            long[] ll = {uuid.getMostSignificantBits(), uuid.getLeastSignificantBits()};
            StringBuilder str = new StringBuilder(SizeOfUUID * 2);
            for (int m = 0; m < ll.length; ++m) {
                for (int i = MBLShift; i > 0; i -= BitsOfByte)
                    formatAsHex((byte) (ll[m] >>> i), str);
                formatAsHex((byte) (ll[m]), str);
            }
            return str.toString();
        }

        public static void formatAsHex(byte b, StringBuilder s) {
            s.append(HEX_CHAR_TABLE[(b >>> 4) & 0x0F]);
            s.append(HEX_CHAR_TABLE[b & 0x0F]);
        }

    }

}
