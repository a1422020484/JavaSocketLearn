package cn.saturn.web.code.platform;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PTManager implements ApplicationContextAware {

    private static Map<String, PlatformInterface> pts = new HashMap<>();

    public static PlatformInterface getPT(String pt) {
        return pts.get(pt);
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        Map<String, PlatformInterface> map = arg0.getBeansOfType(PlatformInterface.class);
        for (PlatformInterface pi : map.values()) {
            pts.put(pi.ptFlag(), pi);
        }
    }

}
