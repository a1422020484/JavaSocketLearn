package cn.saturn.web.code.login.domain;

import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 帐号管理
 */
@Component
public class AccountManager implements ApplicationContextAware {

    private static AccountDAO accountDAO;

    /**
     * @param id 帐号唯一id
     * @return {@link Account}
     */
    public static Account getAccount(long id) {
        Account account = null;
        if (RedisUtils.RedisAccount) {
            String json = RedisUtils.hget(RedisKeys.K_ACCOUNT, String.valueOf(id));
            if (json != null) {
                try {
                    account = JSON.parseObject(json, Account.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (account == null) {
            account = accountDAO.get(id);
            if (account != null) {
                if (RedisUtils.RedisAccount) {
                    RedisUtils.hset(RedisKeys.K_ACCOUNT, String.valueOf(id), JSON.toJSONString(account));
                }
            }
        }
        return account;
    }

    /**
     * @param accountName 帐号名
     * @param platform    平台
     * @return Account
     */
	public static Account getAccount(String accountName, String platform) {
		platform = (platform != null) ? platform : "";
		String field = null;

		Account account = null;
		if (RedisUtils.RedisAccount) {
			field = accountName + platform;
			try {
				String json = RedisUtils.hget(RedisKeys.K_ACCOUNT_NAME, field);
				if (json != null) {

					account = JSON.parseObject(json, Account.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (account == null) {
			account = accountDAO.get(accountName, platform);
			if (account != null) {
				try {
					if (RedisUtils.RedisAccount) {
						RedisUtils.hset(RedisKeys.K_ACCOUNT_NAME, field, JSON.toJSONString(account));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return account;
	}

    /**
     *
     * @param thirdUserId 第三方渠道用户id,例,易接的子渠道用户id
     * @return Account
     */
    public static Account getAccountByThirdUserId(String thirdUserId) {
        Account account = account = accountDAO.get(thirdUserId);
        return account;
    }

    /**
     * 注册账号
     *
     * @param account
     * @return
     */
    public static boolean insert(Account account) {
        Long id = insertByDAO(account);
        boolean result = id != null && id > 0;
        if (result && RedisUtils.RedisAccount) {
            RedisUtils.hset(RedisKeys.K_ACCOUNT, String.valueOf(id), JSON.toJSONString(account));
        }

        return result;
    }

    /**
     * @param id
     * @return 帐号是否存在
     */
    public static boolean isAccountExist(long id) {
        boolean exists = false;
        if (RedisUtils.RedisAccount) {
            exists = RedisUtils.hexists(RedisKeys.K_ACCOUNT, String.valueOf(id));
        }

        if (!exists) {
            exists = getAccount(id) != null;
        }

        return exists;
    }

    /**
     * 更新
     *
     * @param model
     * @return
     */
    public static boolean update(Account model) {
        updateByDAO(model);
        if (RedisUtils.RedisAccount) {
            RedisUtils.hdel(RedisKeys.K_ACCOUNT, String.valueOf(model.getId()));
            RedisUtils.hdel(RedisKeys.K_ACCOUNT_NAME, model.getAccount() + model.getPlatform());
        }
        return true;
    }

    protected static Long insertByDAO(Account account) {
        Long id = accountDAO.insert(account);
        if (id != null) {
            account.setId(id);
        }
        return id;
    }

    protected static void updateByDAO(Account account) {
        accountDAO.insertOrUpdate(account);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        accountDAO = applicationContext.getBean(AccountDAO.class);
    }
}
