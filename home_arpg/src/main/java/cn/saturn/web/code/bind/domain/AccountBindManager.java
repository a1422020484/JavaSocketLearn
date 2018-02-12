package cn.saturn.web.code.bind.domain;

import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AccountBindManager implements ApplicationContextAware {

    private static AccountBindDAO accountBindDAO;

    /**
     * @param accountId
     * @return List&lt;AccountBind&gt;
     */
    public static List<AccountBind> getAccountBindList(long accountId) {
        List<AccountBind> list = null;
        String field = String.valueOf(accountId);
        if (RedisUtils.RedisAccountBind) {
            String json = RedisUtils.hget(RedisKeys.K_ACCOUNT_BIND, field);
            if (json != null) {
                try {
                    list = JSON.parseArray(json, AccountBind.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (list == null) {
            list = accountBindDAO.getList(accountId);
            if (list != null && RedisUtils.RedisAccountBind && list.size() > 0) {
                RedisUtils.hset(RedisKeys.K_ACCOUNT_BIND, field, JSON.toJSONString(list));
            }
        }

        return list;
    }

    /**
     * @param accountId
     * @return Map&lt;Integer, AccountBind&gt;, k:{@link AccountBind#getSrvId()}, v:AccountBind
     */
    public static Map<Integer, AccountBind> getAccountBindMap(long accountId) {
        List<AccountBind> list = getAccountBindList(accountId);
        if (list != null) {
            Map<Integer, AccountBind> map = new HashMap<>(list.size() << 1);
            for (AccountBind bind : list) {
                map.put(bind.getSrvId(), bind);
            }
            return map;
        }

        return null;
    }

    /**
     * @param accountId
     * @param serverId
     * @return {@link AccountBind}
     */
    public static AccountBind getAccountBind(long accountId, int serverId) {
        AccountBind bind = null;
        String field = accountId + "_" + serverId;
        if (RedisUtils.RedisAccountBind) {
            
        	try {
            	String json = RedisUtils.hget(RedisKeys.K_ACCOUNT_BIND_ACT_SRV, field);
            	if(json != null){
            		bind = JSON.parseObject(json, AccountBind.class);
            	}  
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (bind == null) {
            bind = accountBindDAO.get(accountId, serverId);
            if (bind != null && RedisUtils.RedisAccountBind) {
                try {
					RedisUtils.hset(RedisKeys.K_ACCOUNT_BIND_ACT_SRV, field, JSON.toJSONString(bind));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        return bind;
    }
    
    /**
     * @param playerId
     * @param serverId
     * @return {@link AccountBind}
     */
    public static AccountBind getAccountBindByPlayerId(int playerId, int serverId) {
    	return accountBindDAO.getByPlayerId(playerId, serverId);
    }

    /**
     * @param accountBind
     */
    public static void insert(AccountBind accountBind) {
        Long id = accountBindDAO.insert(accountBind);
        if (id != null) {
            accountBind.setId(id);
        }
        if (RedisUtils.RedisAccountBind) {
            RedisUtils.hdel(RedisKeys.K_ACCOUNT_BIND, String.valueOf(accountBind.getAccountId()));
            String field = accountBind.getAccountId() + "_" + accountBind.getSrvId();
            RedisUtils.hdel(RedisKeys.K_ACCOUNT_BIND_ACT_SRV, field);
        }
    }

    /**
     * @param accountBind
     */
    public static void insertOrUpdate(AccountBind accountBind) {
        accountBindDAO.insertOrUpdate(accountBind);
        if (RedisUtils.RedisAccountBind) {
            RedisUtils.hdel(RedisKeys.K_ACCOUNT_BIND, String.valueOf(accountBind.getAccountId()));
            String field = accountBind.getAccountId() + "_" + accountBind.getSrvId();
            RedisUtils.hdel(RedisKeys.K_ACCOUNT_BIND_ACT_SRV, field);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        accountBindDAO = applicationContext.getBean(AccountBindDAO.class);
    }
}
