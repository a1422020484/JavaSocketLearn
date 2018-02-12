package cn.saturn.web.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import xzj.core.util.CoreConfig;

/**
 * @author xiezuojie
 */
public final class Config {

    private Config() {
    }

    private static final Map<String, String> props = new HashMap<>();

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        for (String key : bundle.keySet()) {
            props.put(key, bundle.getString(key).trim());
        }
    }
    
    /**
	 * 语言,地区
	 */
	public static Locale Locale = null;
	
	static {
		String localCfg = CoreConfig.stringValue("Locale");
		if (StringUtils.isBlank(localCfg)) {
			localCfg = "zh_CN";
		}
		String[] arr = localCfg.split("\\_");
		if (arr.length != 2) {
			arr = new String[] {"zh", "CN"};
		}
		Locale = new Locale(arr[0], arr[1]);
	}

    /**
     * @param key
     * @return 与key关联的值
     */
    public static String val(String key) {
        return props.get(key);
    }

    /**
     * @param key
     * @return 与key关联的值
     */
    public static int intVal(String key) {
        return Integer.valueOf(val(key));
    }

    /**
     * @param key
     * @return 与key关联的值
     */
    public static long longVal(String key) {
        return Long.valueOf(val(key));
    }

    /**
     * @param key
     * @return 与key关联的值
     */
    public static boolean booleanVal(String key) {
        return Boolean.valueOf(val(key));
    }
}
