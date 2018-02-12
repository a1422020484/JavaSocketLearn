package cn.saturn.web.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptUtils {
    public static String MD5(String code) {
        try {
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(code.getBytes());
            byte[] md = mdInst.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                String shaHex = Integer.toHexString(md[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static class DES {

        public static byte[] encrypt(byte[] datasource, String password) {
            try {
                SecureRandom random = new SecureRandom();
                DESKeySpec desKey = new DESKeySpec(password.getBytes());
                SecretKeyFactory keyFactory = SecretKeyFactory
                        .getInstance("DES");
                SecretKey securekey = keyFactory.generateSecret(desKey);
                Cipher cipher = Cipher.getInstance("DES");
                cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
                return cipher.doFinal(datasource);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }

        public static byte[] decrypt(byte[] src, String password) {
            try {
                SecureRandom random = new SecureRandom();
                DESKeySpec desKey = new DESKeySpec(password.getBytes());
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey securekey = keyFactory.generateSecret(desKey);
                Cipher cipher = Cipher.getInstance("DES");
                cipher.init(Cipher.DECRYPT_MODE, securekey, random);
                return cipher.doFinal(src);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class ZED {
        protected static final byte offsetKey = 18;
        protected static final byte indexKey = 121;

        public static byte[] encrypt(byte[] buffer, String password) {
            return encrypt(buffer, password.getBytes());
        }

        public static byte[] encrypt(byte[] buffer, byte[] keys) {
            int bufSize = buffer.length;
            byte[] out = new byte[bufSize];
            encrypt(buffer, keys, out, 0);
            return out;
        }

        public static int encrypt(byte[] buffer, byte[] keys, byte[] out,
                                  int offset) {
            int bufSize = buffer.length;
            int keySize = (keys != null) ? keys.length : 0;

            for (int i = 0; i < bufSize; i++) {
                byte b = buffer[i];
                byte index = (byte) (indexKey - (i % indexKey));
                byte c = (keySize > 0) ? keys[i % keySize] : 0;
                c += index;
                b = (byte) ((b + (offsetKey + keySize + c) + 256) % 256);
                b = (byte) (b ^ c);
                out[i + offset] = b;
            }
            return bufSize + offset;
        }

        public static byte[] decrypt(byte[] buffer, String password) {
            return decrypt(buffer, password.getBytes());
        }

        public static byte[] decrypt(byte[] buffer, byte[] keys) {
            int bufSize = buffer.length;
            byte[] out = new byte[bufSize];

            decrypt(buffer, keys, out, 0);
            return out;
        }

        public static int decrypt(byte[] buffer, byte[] keys, byte[] out,
                                  int offset) {
            int bufSize = buffer.length;
            int keySize = (keys != null) ? keys.length : 0;
            for (int i = 0; i < bufSize; i++) {
                byte b = buffer[i];
                byte index = (byte) (indexKey - (i % indexKey));
                byte c = (keySize > 0) ? keys[i % keySize] : 0;
                c += index;
                b = (byte) (b ^ c);
                b = (byte) ((b - (offsetKey + keySize + c) + 256) % 256);
                out[i + offset] = b;
            }
            return bufSize + offset;
        }

    }

}