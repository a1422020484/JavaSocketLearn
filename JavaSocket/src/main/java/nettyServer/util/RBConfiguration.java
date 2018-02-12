package nettyServer.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author yangxp
 *         <p>
 *         国际化消息
 *         Resource Bundle Message
 */
public class RBConfiguration {

    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String get() {
        return RBConfiguration.get(key);
    }

    /**
     * @param key
     * @return
     */
    public static String get(String key) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Configuration", RBMessage.getLocale());
            return bundle.getString(key);
        } catch (Exception e) {
//            e.printStackTrace();
            return key;//TODO 表还在建，建完换成下一句的返回
//            return "Unknown Configuration Key: " + key;


        }
    }

    /**
//     *
     * @param key
     * @param locale
     * @return
     */
    public static String get(String key, Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Configuration", locale);
            return bundle.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Configuration Key: " + key;
        }
    }

    /**
     * @param key
     * @param params
     * @return 格式化后的内容
     */
    public static String get(String key, Object... params) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Configuration", RBMessage.getLocale());
            String msg = bundle.getString(key);
            return MessageFormat.format(msg, params);
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Configuration key: " + key;
        }
    }

    /**
     * @param key
     * @param locale
     * @param params
     * @return 格式化后的内容
     */
    public static String get(String key, Locale locale, Object... params) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Configuration", locale);
            String msg = bundle.getString(key);
            return MessageFormat.format(msg, params);
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Configuration key: " + key;
        }
    }

}
