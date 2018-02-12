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
public class VersionManager implements ApplicationContextAware {
    public static final int DEFAULT_ID = 1;
    public static final String DEFAULT_PLATFORM = "all";

    private static VersionDAO versionDAO;

    public final static Comparator<Version> VERSION_COMPARATOR = new Comparator<Version>() {
        @Override
        public int compare(Version o1, Version o2) {
            return o1.getVersion().compareTo(o2.getVersion());
        }
    };

    /**
     * @return 版本号列表, 不保证顺序
     */
    public static synchronized List<Version> getVersionList() {
        List<Version> list = null;
        if (RedisUtils.RedisVersion) {
            List<String> versionsJson = RedisUtils.hvals(RedisKeys.K_VERSION);
            if (versionsJson != null && versionsJson.size() > 0) {
                list = new ArrayList<>(versionsJson.size());
                try {
                    for (String json : versionsJson) {
                        list.add(JSON.parseObject(json, Version.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (list == null) {
            list = versionDAO.getList();
            if (list != null && RedisUtils.RedisVersion && list.size() > 0) {
                Map<String, String> hash = new HashMap<>();
                for (Version version : list) {
                    hash.put(String.valueOf(version.getId()), JSON.toJSONString(version));
                }
                RedisUtils.hmset(RedisKeys.K_VERSION, hash);
            }
        }

        return list;
    }

    public static List<Version> findByPlatform(List<Version> list, String platform) {
        if (platform == null || platform.length() <= 0) {
            return null; // 过掉
        }

        List<Version> found = new ArrayList<>();
        for (Version version : list) {
            if (platform.equals(version.getPlatform())) {
                found.add(version);
            }
        }
        return found;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        versionDAO = applicationContext.getBean(VersionDAO.class);
        List<Version> list = versionDAO.getList();
        if (list == null) {
            Version version = new Version();
            version.setId(DEFAULT_ID);
            version.setPlatform(DEFAULT_PLATFORM);
            version.setUrl("");
            version.setVersion("");
            version.setNotice("");
            versionDAO.insertOrUpdate(version);
        }

        getVersionList();
    }
}
