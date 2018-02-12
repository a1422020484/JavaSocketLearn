package cn.saturn.web.code.platform.utils;

import net.paoding.rose.web.Invocation;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiezuojie
 */
public class CommonUtils {

    /**
     * @param inv
     * @return
     */
    public static String getParamString(Invocation inv) {
        StringBuilder b = new StringBuilder();
        for (Map.Entry<String, String[]> en : inv.getRequest().getParameterMap().entrySet()) {
            String k = en.getKey();
            String v = en.getValue()[0];
            if (b.length() > 0)
                b.append("&");
            b.append(k).append("=").append(v);
        }
        return b.toString();
    }

    public static HashMap<String, String> getParamMap(Invocation inv) {
        HashMap<String, String> param = new HashMap<>();
        for (Map.Entry<String, String[]> en : inv.getRequest().getParameterMap().entrySet()) {
            String k = en.getKey();
            String v = en.getValue()[0];
            param.put(k, v);
        }
        return param;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
