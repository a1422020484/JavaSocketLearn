package cn.saturn.web.code.platform;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author xiezuojie
 */
public final class PTConfig {

    private PTConfig() {
    }

    private static final Map<String, String> props = new HashMap<>();

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("ptconfig");
        for (String key : bundle.keySet()) {
            props.put(key, bundle.getString(key).trim());
        }
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
