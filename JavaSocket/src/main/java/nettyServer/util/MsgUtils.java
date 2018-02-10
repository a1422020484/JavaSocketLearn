package nettyServer.util;

/**
 * 
 * 
 * @author xiezuojie
 */
public final class MsgUtils {
	
	public static final byte[] otherKey = "nrutas".getBytes();
	
	public static void encrypt(byte[] source, String sessionId) {
		encrypt(source, sessionId.getBytes());
	}

	/**
	 * 对数据源加密
	 * @param source 要加密的数据源,源数据将被修改
	 * @param key
	 */
	public static void encrypt(byte[] source, byte[] key) {
		byte[] s = source;
		byte[] k = key;
		int sLen = s.length;
		int kLen = k.length;
		if (kLen == 0) {
			throw new RuntimeException("key的长度不能为0.");
		}
		for (int kIdx = 0, i = 0; i < sLen; i++, kIdx++) {
			if (kIdx == kLen) {
				kIdx = 0;
			}
			s[i] = (byte) (s[i] ^ k[kIdx]);
		}

	}
}
