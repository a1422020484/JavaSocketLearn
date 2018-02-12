package cn.saturn.web.code.platform.other;

import cn.saturn.web.code.platform.PTConfig;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;


/**
 * RSA工具类
 */
public class OppoRsa {
    public final static String PublicKey = PTConfig.val("OppoPublicKey");

    public static boolean doCheck(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes("UTF-8"));

//            byte[] d = Base64.decodeBase64(sign);
//            byte[] data = new byte[128];
//            System.arraycopy(d, 0, data, 0, d.length);
            boolean bverify = signature.verify(Base64.decodeBase64(sign));
            return bverify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean doCheck(String content, String sign) {
        return doCheck(content, sign, PublicKey);
    }


}
