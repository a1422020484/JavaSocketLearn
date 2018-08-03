package hotReload;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nettyServer.util.Tool;

/**
 * 管理服务器配置
 *
 * @author zuojie.x
 */
public final class CoreConfig {

	private static final Logger log = LoggerFactory.getLogger(LogKey.CORE);

	/**
	 * 保存属性映射
	 */
	private static Map<String, String> res = new HashMap<>();
	
	/**
	 * 允许每次接收数据最大长度(字节长度)
	 */
	public static int MaxDataLength = 0;

	static {
		// 加载默认配置文件
		ResourceBundle bundle = ResourceBundle.getBundle("default");
		Set<String> keySet = bundle.keySet();
		for (String key : keySet)
			res.put(key, bundle.getString(key));
		// 加载配置文件,覆盖默认配置
		try {
			bundle = ResourceBundle.getBundle("config");
			keySet = bundle.keySet();
			for (String key : keySet)
				res.put(key, bundle.getString(key));
			log.info("load file config.properties.");
		} catch (Exception e) {
			log.info("no file config.properties, load default.properties!");
		}
		if (log.isDebugEnabled()) {
			for (Map.Entry<String, String> entry : res.entrySet()) {
				log.debug("load property -> {}={}", entry.getKey(), entry.getValue());
			}
		}
		MaxDataLength = intValue("MaxDataLength");
	}

	/**
     *
     */
	private CoreConfig() {
	}

	/**
	 * 查找匹配的值,并将值转换为指定的类型T
	 *
	 * @param key
	 * @param clazz
	 * @return 与key匹配的值, 如果没有找到匹配, 那么返回null.
	 */
	private static <T> T get(String key, Class<T> clazz) {
		String value = res.get(key);
		return Tool.convertBaseType(value, clazz);
	}

	/**
	 * 查找匹配的值,并将值转换为short类型
	 *
	 * @param key
	 * @return 匹配的值
	 */
	public static short shortValue(String key) {
		return get(key, short.class);
	}

	/**
	 * 查找匹配的值,并将值转换为int类型
	 *
	 * @param key
	 * @return 匹配的值
	 */
	public static int intValue(String key) {
		return get(key, int.class);
	}

	/**
	 * 查找匹配的值,并将值转换为long类型
	 *
	 * @param key
	 * @return 匹配的值
	 */
	public static long longValue(String key) {
		return get(key, long.class);
	}

	/**
	 * 查找匹配的值,并将值转换为float类型
	 *
	 * @param key
	 * @return 匹配的值
	 */
	public static float floatValue(String key) {
		return get(key, float.class);
	}

	/**
	 * 查找匹配的值,并将值转换为double类型
	 *
	 * @param key
	 * @return 匹配的值
	 */
	public static double doubleValue(String key) {
		return get(key, double.class);
	}

	/**
	 * 查找匹配的值,并将值转换为boolean类型
	 *
	 * @param key
	 * @return 匹配的值
	 */
	public static boolean booleanValue(String key) {
		return get(key, boolean.class);
	}

	/**
	 * 查找匹配的值,并将值转换为String类型
	 *
	 * @param key
	 * @return 匹配的值
	 */
	public static String stringValue(String key) {
		return res.get(key);
	}
}
