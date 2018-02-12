package nettyServer.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 
 * 
 * @author yangxp
 */
public class SessionIdGenerator {
	
	static KeyGenerator gen = null;
	
	static {
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(random.generateSeed(16));
			gen = KeyGenerator.getInstance("AES");
			gen.init(256, random);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public synchronized static String generate() {
		SecretKey key = gen.generateKey();
		byte[] data = key.getEncoded();
		return org.apache.commons.codec.binary.Base64.encodeBase64String(data);
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<>();
		Object o = new Object();
		for (int i = 0; i < 10000; i ++) {
			String s = generate();
			if (map.containsKey(s)) {
				System.out.println("发现......重复");
			} else {
				map.put(s, o);
			}
		}
		System.out.println("map size: " + map.size());
	}
}
