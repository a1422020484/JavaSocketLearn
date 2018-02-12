package cn.saturn.web.controllers.server.dao;

import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PackageManager implements ApplicationContextAware {

    private static PackageDAO packageDAO;

    public final static Comparator<Package> PACKAGE_COMPARATOR = new Comparator<Package>() {
        @Override
        public int compare(Package o1, Package o2) {
            String a = o1.getVersion();
            String b = o2.getVersion();
            int c = a.compareTo(b);
            if (c > 0) {
                return 1;
            } else if (c == 0) {
                int a1 = o1.getResversion();
                int b1 = o2.getResversion();
                return (a1 > b1) ? 1 : (a1 == b1) ? 0 : -1;
            }
            return -1;
        }
    };

    public static synchronized List<Package> getList() {
        List<Package> list = null;
        if (RedisUtils.RedisPackage) {
            List<String> packagesJson = RedisUtils.hvals(RedisKeys.K_PACKAGE);
            if (packagesJson != null && packagesJson.size() > 0) {
                list = new ArrayList<>(packagesJson.size());
                try {
                    for (String json : packagesJson) {
                        list.add(JSON.parseObject(json, Package.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (list == null) {
            list = packageDAO.getList();
            if (list != null && RedisUtils.RedisPackage && list.size() > 0) {
                Map<String, String> hash = new HashMap<>();
                for (Package pkg : list) {
                    hash.put(String.valueOf(pkg.getId()), JSON.toJSONString(pkg));
                }
                RedisUtils.hmset(RedisKeys.K_PACKAGE, hash);
            }
        }

        return list;
    }

    public static List<Package> findByPlatformAndType(List<Package> list, String platform, String version, int type) {
        if (platform == null || platform.length() <= 0) {
            return null;
        }

        List<Package> found = new ArrayList<>();
        for (Package pkg : list) {
            String checkPlatform = pkg.getPlatform();
            if (type != pkg.getType()) {
                continue;
            }
            if (!platform.equals(checkPlatform)) {
                continue;
            }
            if (!pkg.getVersion().equals(version)) {
                continue;
            }
            found.add(pkg);
        }
        return found;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        packageDAO = applicationContext.getBean(PackageDAO.class);
        getList();
    }

}
