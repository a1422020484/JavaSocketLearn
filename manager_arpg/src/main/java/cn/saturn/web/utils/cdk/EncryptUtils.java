package cn.saturn.web.utils.cdk;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

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

	public static byte[] overturn(byte[] source, int offset, int size, int interval) {
		int count = size / 2;
		for (int i = 0; i < count; i++)
			if ((interval <= 0) || (i % (interval + 1) == 0)) {
				byte b = source[(offset + i)];
				source[(offset + i)] = source[(offset + size - i - 1)];
				source[(offset + size - i - 1)] = b;
			}
		return source;
	}

	public static byte[] inversion(byte[] source, int offset, int size, int interval, int inv) {
		int count = size;
		for (int i = 0; i < count; i++)
			if ((interval <= 0) || (i % (interval + 1) == 0)) {
				source[(offset + i)] = (byte) (source[(i + offset)] ^ inv);
			}
		return source;
	}

	public static byte[] offset(byte[] source, int start, int size, int interval, int offset0) {
		int count = size;
		for (int i = 0; i < count; i++)
			if ((interval <= 0) || (i % (interval + 1) == 0)) {
				byte b = source[(start + i)];
				int s = 0xFF & b;
				int o = (s + offset0) % 256;
				source[(start + i)] = (byte) o;
			}
		return source;
	}

	public static class Base64 {
		public static String encode(byte[] data) {
			return Base64.encode(data);
		}

		public static byte[] decode(String str) {
			try {
				return Base64.decode(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public static class DES {
		public static byte[] encrypt(byte[] datasource, byte[] password) {
			try {
				SecureRandom random = new SecureRandom();
				DESKeySpec desKey = new DESKeySpec(password);

				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				SecretKey securekey = keyFactory.generateSecret(desKey);

				Cipher cipher = Cipher.getInstance("DES");

				cipher.init(1, securekey, random);

				return cipher.doFinal(datasource);
			} catch (Throwable e) {
				if (e.getClass() == InvalidKeyException.class)
					System.err.println(" 加密密钥错误:" + password + " (需要8位长度的密码)");
				else {
					System.err.println(
							e.getLocalizedMessage() + " 加密失败:" + password + " -> " + Arrays.toString(datasource));
				}
			}

			return null;
		}

		public static byte[] decrypt(byte[] src, byte[] password) {
			try {
				SecureRandom random = new SecureRandom();

				DESKeySpec desKey = new DESKeySpec(password);

				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

				SecretKey securekey = keyFactory.generateSecret(desKey);

				Cipher cipher = Cipher.getInstance("DES");

				cipher.init(2, securekey, random);

				return cipher.doFinal(src);
			} catch (Throwable e) {
				System.err.println("解码失败:" + password + " -> " + Arrays.toString(src));
			}
			return null;
		}
	}

	public static class SHA {
		public static String SHA1(String decript) {
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-1");
				digest.update(decript.getBytes());
				byte[] messageDigest = digest.digest();

				StringBuffer hexString = new StringBuffer();

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

		public static String SHA0(String decript) {
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA");
				digest.update(decript.getBytes());
				byte[] messageDigest = digest.digest();

				StringBuffer hexString = new StringBuffer();

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

		public static byte[] HmacSHA1(byte[] buffer, byte[] key) {
			try {
				Mac mac = Mac.getInstance("HmacSHA1");
				String algorithm = mac.getAlgorithm();
				SecretKeySpec secret = new SecretKeySpec(key, algorithm);
				mac.init(secret);
				return mac.doFinal(buffer);
			} catch (Exception e) {
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

		public static int encrypt(byte[] buffer, byte[] keys, byte[] out, int offset) {
			int bufSize = buffer.length;
			int keySize = keys != null ? keys.length : 0;

			for (int i = 0; i < bufSize; i++) {
				byte b = buffer[i];
				byte index = (byte) (121 - i % 121);
				byte c = keySize > 0 ? keys[(i % keySize)] : 0;
				c = (byte) (c + index);

				b = (byte) ((b + (18 + keySize + c) + 256) % 256);

				b = (byte) (b ^ c);

				out[(i + offset)] = b;
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

		public static int decrypt(byte[] buffer, byte[] keys, byte[] out, int offset) {
			int bufSize = buffer.length;
			int keySize = keys != null ? keys.length : 0;

			for (int i = 0; i < bufSize; i++) {
				byte b = buffer[i];
				byte index = (byte) (121 - i % 121);
				byte c = keySize > 0 ? keys[(i % keySize)] : 0;
				c = (byte) (c + index);

				b = (byte) (b ^ c);

				b = (byte) ((b - (18 + keySize + c) + 256) % 256);

				out[(i + offset)] = b;
			}
			return bufSize + offset;
		}
	}
}