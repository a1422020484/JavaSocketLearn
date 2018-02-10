package nettyServer.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * 
 * 
 * @author xiezuojie
 */
public class RSAUtils {

	static byte[] publicKeyBytes;
	static byte[] privateKeyBytes;
	static {
		try {
			publicKeyBytes = RSAKeys.decryptBASE64(CoreConfig.stringValue("SystemPublicKey"));
			privateKeyBytes = RSAKeys.decryptBASE64(CoreConfig.stringValue("SystemPrivateKey"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	/**
//	 * 用私钥加密
//	 * 
//	 * @param data
//	 *            加密数据
//	 * @return
//	 * @throws Exception
//	 */
//	public static byte[] encryptByPrivateKey(byte[] data) throws Exception {
//		// 取私钥
//		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
//		KeyFactory keyFactory = KeyFactory.getInstance(RSAKeys.KEY_ALGORITHM);
//		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
//		// 对数据加密
//		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
//		return cipher.doFinal(data);
//	}

//	/**
//	 * 不支持长度大于117!
//	 * 用私钥解密
//	 * 
//	 * @param data
//	 *            加密数据
//	 * @return 解密后的数据
//	 * @throws Exception
//	 */
//	public static byte[] decryptByPrivateKey(byte[] data) throws Exception {
//		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
//		KeyFactory keyFactory = KeyFactory.getInstance(RSAKeys.KEY_ALGORITHM);
//		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
//		// 对数据解密
//		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//		cipher.init(Cipher.DECRYPT_MODE, privateKey);
//		return cipher.doFinal(data);
//	}

	/**
	 * 用私钥解密,数据长度支持大于117
	 * 
	 * @param data
	 *            加密数据
	 * @return 解密后的数据
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data) throws Exception {
		int dataLen = data.length;
		short encryptLen = (short) (data[0] << 8 | data[1] & 0xFF);
		int backLen = dataLen - 2 - encryptLen;
		byte[] encryptFront = new byte[encryptLen];
		System.arraycopy(data, 2, encryptFront, 0, encryptLen);
//		System.out.println("encryptFront>>" + Arrays.toString(encryptFront));
		
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSAKeys.KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] front = cipher.doFinal(encryptFront);
//		System.out.println("front>>" + Arrays.toString(front));
		int frontLen = front.length;
		
		byte[] out = new byte[frontLen + backLen];
		System.arraycopy(front, 0, out, 0, frontLen);
		if (backLen > 0) {
			System.arraycopy(data, 2 + encryptLen, out, frontLen, backLen);
		}
		return out;
	}
	
	/**
	 * 用公钥加密,数据长度支持大于117
	 * 
	 * @param data
	 *            加密数据
	 * @return 加密后的数据,结构: 加密部分长度(short) + 加密部分 + 未加密部分
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data) throws Exception {
		int dataLen = data.length;
		
		byte[] front = null;
		int limitLen = 117;
		if (dataLen > limitLen) {
			front = new byte[limitLen];
			System.arraycopy(data, 0, front, 0, limitLen);
		} else {
			front = data;
		}
//		System.out.println("front>" + Arrays.toString(front));
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSAKeys.KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] encryptFront = cipher.doFinal(front);
//		System.out.println("encryptFront>" + Arrays.toString(encryptFront));
		int encryptLen = encryptFront.length;
		
		int backLen = dataLen - limitLen;
//		System.out.println("dataLen=" + dataLen + " backLen=" + backLen);
		byte[] out = backLen > 0 ? new byte[2 + encryptLen + backLen] : new byte[2 + encryptLen]; // 前面2字节是加密数据的长度
		out[0] = (byte) (encryptLen >>> 8);
		out[1] = (byte) (encryptLen);
		System.arraycopy(encryptFront, 0, out, 2, encryptLen);
		if (backLen > 0) {
			System.arraycopy(data, limitLen, out, 2 + encryptLen, backLen);
		}
		return out;
	}

	public static void main(String[] args) throws Exception {
		String s = "abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#abcd中文!$%#";
		byte[] data = s.getBytes("utf-8");
		System.out.println("data.length>" + data.length);
		byte[] encrypt = encryptByPublicKey(data);
		byte[] decrypt = decryptByPrivateKey(encrypt);
		System.out.println(new String(decrypt, "utf-8"));
	}
	
//	/**
//	 * 不支持数据长度大于117!
//	 * 用公钥加密
//	 * 
//	 * @param data
//	 *            加密数据
//	 * @return 加密后的数据
//	 * @throws Exception
//	 */
//	public static byte[] encryptByPublicKey(byte[] data) throws Exception {
//		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
//		KeyFactory keyFactory = KeyFactory.getInstance(RSAKeys.KEY_ALGORITHM);
//		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
//		// 对数据解密
//		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//		return cipher.doFinal(data);
//	}

//	/**
//	 * 用公钥解密
//	 * 
//	 * @param data
//	 *            加密数据
//	 * @return
//	 * @throws Exception
//	 */
//	public static byte[] decryptByPublicKey(byte[] data) throws Exception {
//		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
//		KeyFactory keyFactory = KeyFactory.getInstance(RSAKeys.KEY_ALGORITHM);
//		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
//
//		// 对数据解密
//		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//		cipher.init(Cipher.DECRYPT_MODE, publicKey);
//
//		return cipher.doFinal(data);
//	}
}
