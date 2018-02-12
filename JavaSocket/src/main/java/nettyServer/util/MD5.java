package nettyServer.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;

import nettyServer.util.annotation.NotNull;

/**
 * MD5工具
 *
 * @author yangxp
 */
public final class MD5 {
	// 用来将字节转换成 16 进制表示的字符
	static final char HexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 用MD5加密,如果源串是null或空,那么返回空字符串
	 *
	 * @param source
	 * @return MD5编码
	 */
	@NotNull
	public static String encode(String source) {
		if (StringUtils.isEmpty(source))
			return "";
		byte[] data = null;
		try {
			data = source.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encode(data);
	}

	/**
	 * 用MD5加密,如果源字节数组是null,那么返回空字符串
	 *
	 * @param source
	 *            字符串源转换后的字节数组
	 * @return MD5编码
	 */
	@NotNull
	public static String encode(byte[] source) {
		if (source == null)
			return "";
		String s = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = HexDigits[byte0 >>> 4 & 0xf];
				str[k++] = HexDigits[byte0 & 0xf];
			}
			s = new String(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
}
