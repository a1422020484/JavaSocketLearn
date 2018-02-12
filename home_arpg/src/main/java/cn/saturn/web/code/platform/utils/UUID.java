package cn.saturn.web.code.platform.utils;

/**
 * @author xiezuojie
 */
public class UUID {
    /**
     * @return uuid
     */
    public static String uuid() {
        return java.util.UUID.randomUUID().toString();
    }
}
