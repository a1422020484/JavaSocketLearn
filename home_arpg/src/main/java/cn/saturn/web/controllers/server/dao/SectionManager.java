package cn.saturn.web.controllers.server.dao;

import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SectionManager implements ApplicationContextAware {

    private static SectionDAO sectionDAO;

    /**
     * @return List&lt;Section&gt;
     */
    public static synchronized List<Section> getSectionList() {
        List<Section> list = null;
        if (RedisUtils.RedisServer) {
            List<String> sectionsJson = RedisUtils.hvals(RedisKeys.K_SERVER_SECTION);
            if (sectionsJson != null && sectionsJson.size() > 0) {
                list = new ArrayList<>(sectionsJson.size());
                for (String json : sectionsJson) {
                    list.add(JSON.parseObject(json, Section.class));
                }
            }
        }

        if (list == null) {
            list = sectionDAO.getList();
            if (list != null && RedisUtils.RedisServer && list.size() > 0) {
                Map<String, String> hash = new HashMap<>();
                for (Section section : list) {
                    hash.put(String.valueOf(section.getId()), JSON.toJSONString(section));
                }
                RedisUtils.hmset(RedisKeys.K_SERVER_SECTION, hash);
            }
        }
        return list;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        sectionDAO = applicationContext.getBean(SectionDAO.class);
        getSectionList();
    }

}
