package cn.saturn.web.utils;

import net.paoding.rose.web.Invocation;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Utils {
//	public static final Config config = new Config("config");
//	public static final TempLockMap<Object, Object> tempDates = new TempLockMap<>(1000);

    public static final boolean isDebug = Config.booleanVal("debug");
//	public static final int cacheSize = Config.intVal("cacheSize");
//	public static final boolean cacheEnable = Config.booleanVal("cacheEnable");

    // web server
//	public static final boolean webServer = Config.booleanVal("webServer");

    public static String redirect(Invocation inv, String path) {
        String codeStr = "r:" + url(inv, path);
        return codeStr;
    }

    public static String predirect(Invocation inv, String path) {
        String codeStr = "f:" + url(inv, path);
        return codeStr;
    }

    public static String url(Invocation inv, String path) {
        ServletContext servletContext = inv.getServletContext();
        return url(servletContext, path);
    }

    public static String url(ServletContext context, String path) {
        String ctxPath = context.getContextPath();
        String url = "/" + ctxPath + "/" + path;
        // codeStr = codeStr.replaceAll("\\", "/");
        url = url.replaceAll("//", "/");

        // 删除最后的斜杠
        int length = url.length() - 1;
        if (url.charAt(length) == '/') {
            url = url.substring(0, length);
        }
        return url;
    }

    public static String setArg(String url, String arg, String value) {
        String code = arg + "=" + value;
        String now = findArg(url, arg);
        if (now != null && now.length() > 0) {
            if (now.equals(value)) {
                return url; // 参数相同
            }

            // 存在参数, 覆盖
            return url.replace(arg + "=" + now, code);
        }

        // 添加
        int indexOf = url.indexOf('?');
        if (indexOf < 0) {
            return url + "?" + code;
        }
        // 加到尾部
        return url + "&" + code;
    }

    public static String findArg(String url, String arg) {
        int startIndex = url.lastIndexOf('?');
        if (startIndex <= 0) {
            return null; // 没有参数
        }
        int length = url.length();
        startIndex = startIndex + 1;

        // 遍历
        while (true) {
            int nextIndex = url.indexOf('&', startIndex);
            if (nextIndex <= 0) {
                nextIndex = length;
            }
            // 截取信息
            String str = url.substring(startIndex, nextIndex);
            int index = str.indexOf('='); // name=value
            if (index > 0) {
                String name = str.substring(0, index);
                String value = str.substring(index + 1);
                // 检测是否相符
                if (name.equals(arg)) {
                    return value;
                }

                // System.out.println(str + "-> " + name + "=" + value);
            } else {
                // 格式不对
            }

            // 下一个
            startIndex = nextIndex + 1;
            if (startIndex >= length) {
                break;
            }
        }
        return null;
    }

    /**
     * 获取整形参数
     *
     * @param inv
     * @param name
     * @return
     */
    public static int getIntParameter(Invocation inv, String name) {
        String param = inv.getParameter(name);
        if (param == null) {
            return 0;
        }

        try {
            int value = Integer.valueOf(param);
            return value;
        } catch (Exception ex) {

        }
        return 0;
    }

    /**
     * 读取url
     *
     * @param request
     * @return
     */
    public static String getInvUrl(Invocation inv) {
        HttpServletRequest request = inv.getRequest();
        return getUrl(request);
    }

    /**
     * 读取url
     *
     * @param request
     * @return
     */
    public static String getUrl(HttpServletRequest request) {
        String url = request.getRequestURI();
        // 读取参数
        String args = request.getQueryString();
        if (args != null) {
            url += "?" + args;
        }
        return url;
    }

//	public static <T> T getSessionValue(Invocation inv, String key, Class<T> clazz) {
//		// 设置登陆状态
//		HttpServletRequest request = inv.getRequest();
//		HttpSession session = request.getSession();
//		Object value = session.getAttribute(key);
//		T ret = zyt.utils.Utils.ObjectUtils.getAccount(value, clazz);
//		return (ret != null) ? ret : zyt.utils.Utils.ObjectUtils.defualtValue(clazz);
//	}

    public static <T> boolean setSessionValue(Invocation inv, String key, T value) {
        // 设置登陆状态
        HttpServletRequest request = inv.getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
        return true;
    }

    public static class ArrayUtils {

        public static boolean find(int[] array, int value) {
            int size = (array != null) ? array.length : 0;
            for (int i = 0; i < size; i++) {
                if (array[i] == value) {
                    return true;
                }
            }
            return false;
        }
    }
}
