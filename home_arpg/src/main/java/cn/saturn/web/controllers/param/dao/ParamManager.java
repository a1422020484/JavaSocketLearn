package cn.saturn.web.controllers.param.dao;

import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ParamManager implements ApplicationContextAware {

    protected static ParamDAO paramDAO;

    /**
     * @param id {@link Param#getId()}
     * @return {@link Param}
     */
    public static Param getParam(long id) {
        Param model = paramDAO.get(id);
        return model;
    }

    /**
     * @param id   {@link Param#getId()}
     * @param type {@link Param#getType()}
     * @return {@link Param}
     */
    public static synchronized Param getParam(long id, String type) {
        Param param = null;
        String field = null;
        if (RedisUtils.RedisParam) {
            field = id + "_" + type;
            String json = RedisUtils.hget(RedisKeys.K_PARAM, field);
            if (json != null) {
                try {
                    param = JSON.parseObject(json, Param.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (param == null) {
            param = paramDAO.get(id, type);
            if (param != null && RedisUtils.RedisParam) {
                RedisUtils.hset(RedisKeys.K_PARAM, field, JSON.toJSONString(param));
            }
        }

        return param;
    }

    public static synchronized Param getGlobalParam() {
        Param globalParam = getParam(0, "global");
        if (globalParam == null) {
            globalParam = new Param();
            globalParam.setId(0);
            globalParam.setType("global");
            globalParam.setInfo("");
            // 保存入数据
            paramDAO.insertOrUpdate(globalParam);
        }

        return globalParam;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        paramDAO = applicationContext.getBean(ParamDAO.class);
        getGlobalParam();
    }
}
