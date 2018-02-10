package nettyServer.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import nettyServer.dispatch.Request;
import nettyServer.dispatch.Session;
import nettyServer.dispatch.ThreadLocalRequest;

/**
 * @author xiezuojie
 *         <p>
 *         国际化消息
 *         Resource Bundle Message
 */
public class RBMessage {

    public static final String LOCALE_KEY = "local";

    /**
     * @param key
     * @return
     */
    public static String get(String key) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Message", getLocale());
            return bundle.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Message Key: " + key;
        }
    }

    /**
     *
     * @param key
     * @param locale
     * @return
     */
    public static String get(String key, Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Message", locale);
            return bundle.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Message Key: " + key;
        }
    }

    /**
     * @param key
     * @param params
     * @return 格式化后的内容
     */
    public static String get(String key, Object... params) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Message", getLocale());
            String msg = bundle.getString(key);
            return MessageFormat.format(msg, params);
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Message key: " + key;
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
            ResourceBundle bundle = ResourceBundle.getBundle("Message", locale);
            String msg = bundle.getString(key);
            return MessageFormat.format(msg, params);
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Message key: " + key;
        }
    }

    static Locale getLocale() {
        Request req = ThreadLocalRequest.get();
        Locale locale = null;
        if (req != null) {
            Session session = req.getSession();
            if (session != null) {
                locale = (Locale) session.getAttribute(LOCALE_KEY);
            }
        }

        if (locale == null) {
            locale = GameUtils.Locale;
        }

        return locale;
    }

    /**
     * 重新加载
     */
    public static void reload() {
        ResourceBundle.clearCache();
    }

}
