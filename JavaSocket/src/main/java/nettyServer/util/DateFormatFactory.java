package nettyServer.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期格式化工具工厂
 * 
 * @author yangxp
 */
public final class DateFormatFactory {

	private static DateFormatFactory instance;
	
	private final ThreadLocal<Map<String, DateFormat>> formats;

	private DateFormatFactory() {
		formats = new ThreadLocal<>();
	}

	private synchronized static DateFormatFactory instance() {
		if (instance == null)
			instance = new DateFormatFactory();
		if (instance.formats.get() == null) {
			instance.formats.set(new HashMap<String, DateFormat>());
		}

		return instance;
	}

	/**
	 * 通过格式获取相应的格式化工具,每个线程获取到属于自己的独立的实例.
	 * 
	 * @param pattern 格式
	 * @return {@link SimpleDateFormat}
	 */
	public static DateFormat get(String pattern) {
		Map<String, DateFormat> map = instance().formats.get();
		DateFormat format = map.get(pattern);
		if (format == null) {
			format = new SimpleDateFormat(pattern);
			map.put(pattern, format);
		}
		return format;
	}

}
