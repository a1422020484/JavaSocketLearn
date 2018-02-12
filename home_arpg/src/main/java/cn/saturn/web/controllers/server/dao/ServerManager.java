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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class ServerManager implements ApplicationContextAware {

    private static ServerDAO serverDAO;

    /**
     * @return 服务器列表
     */
    public static synchronized List<Server> getServerList() {
        List<Server> list = null;
        if (RedisUtils.RedisServer) {
            List<String> serversJson = RedisUtils.hvals(RedisKeys.K_SERVER);
            if (serversJson != null && serversJson.size() > 0) {
                list = new ArrayList<>(serversJson.size());
                try {
                    for (String json : serversJson) {
                        list.add(JSON.parseObject(json, Server.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (list == null || list.size() == 0) {
            list = serverDAO.getList();
            if (list != null && RedisUtils.RedisServer && list.size() > 0) {
                Map<String, String> hash = new HashMap<>();
                for (Server server : list) {
                    hash.put(String.valueOf(server.getId()), JSON.toJSONString(server));
                }
                RedisUtils.hmset(RedisKeys.K_SERVER, hash);
            }
        }

        return list;
    }

    /**
     *
     * @return 仅从缓存读取服务器数据,缓存读不到时返回null.
     */
    public static Map<Integer, Server> getServerMapFromCache() {
        Map<Integer, Server> map = null;
        if (RedisUtils.RedisServer) {
            Map<String, String> serversJson = RedisUtils.hgetAll(RedisKeys.K_SERVER);
            if (serversJson != null && serversJson.size() > 0) {
                map = new HashMap<>(serversJson.size() << 1);
                try {
                    for (Map.Entry<String, String> entry : serversJson.entrySet()) {
                        int serverId = Integer.valueOf(entry.getKey());
                        Server server = JSON.parseObject(entry.getValue(), Server.class);
                        if(server.getState()<1)
                        	continue;
                        map.put(serverId, server);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * 通过id查找Server
     *
     * @param serverId
     * @return {@link Server}
     */
    public static Server getServer(int serverId) {
        Server srv = null;
        if (RedisUtils.RedisServer) {
           try {
              String json = RedisUtils.hget(RedisKeys.K_SERVER, String.valueOf(serverId));
              if(json != null){
                srv = JSON.parseObject(json, Server.class);
              }         
             } catch (Exception e) {
                 e.printStackTrace();
             }
        }
        if (srv == null) {
            srv = serverDAO.get(serverId);
            // 在服务器列表变化时,会重新加载整个列表,这里无需再更新
//            if (srv != null && RedisUtils.RedisServer) {
//                RedisUtils.hset(RedisKeys.K_SERVER, String.valueOf(serverId), JSON.toJSONString(srv));
//            }
        }

        return srv;
    }
    
    public static List<Server> servers() {
    List<Server> servers = new LinkedList<>();
    boolean cache = false;
    if (RedisUtils.RedisServer) {
      Map<String, String> map = RedisUtils.hgetAll(RedisKeys.K_SERVER);
      if (map != null) {
        cache = true;
        for (String json : map.values()) {
          if (json != null) {
            servers.add(JSON.parseObject(json, Server.class));
          }
        }
      }
    } 
    if (!cache) {
      servers = serverDAO.getList();
    }
    return servers;
  }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        serverDAO = applicationContext.getBean(ServerDAO.class);
        getServerList();
    }
}
