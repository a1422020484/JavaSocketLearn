package cn.saturn.web.controllers.param.dao;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Param {
    public static final String theme = "theme";
    public static final String csInfo = "csInfo";

    private long id;
    private String type;
    private String info;

    private transient final Map<String, Object> map = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        Object obj = map.get(key);
        if (obj == null) {
            return null;
        }
        try {
            return (T) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object set(String key, Object value) {
        if (value == null) {
            return null;
        }
        return map.put(key, value);
    }

    public Object get(String key) {
        return map.get(key);
    }

    protected boolean saveToInfo() {
        String jsonStr = JSON.toJSONString(map);
        if (this.info != null && this.info.equals(jsonStr)) {
            return false;
        }
        this.info = jsonStr;
        return true;
    }

    //

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;

        // 清除数据
        map.clear();
        if (StringUtils.isBlank(info)) {
            return;
        }
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> map0 = (Map<String, Object>) JSON.parseObject(info, Map.class);
            // 插入数据
            map.putAll(map0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
