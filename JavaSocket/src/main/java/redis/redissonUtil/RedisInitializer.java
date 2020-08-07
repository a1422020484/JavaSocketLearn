package redis.redissonUtil;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import xzj.saturn.player.PlayerIdName;
import xzj.saturn.player.domain.PlayerDAO;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.concurrent.TimeUnit;

/**
 * 在服务器启动时检查Redis中必要的数据是否存在,如果不存在,那么创建
 *
 * @author xiezuojie
 */
public class RedisInitializer implements ApplicationContextAware {

    private static Logger log = LoggerFactory.getLogger("redis");

    @Resource
    PlayerDAO playerDAO;

    /**
     * 检查Redis是否需要初始化数据
     */
    void checkRedis() {
        log.info(String.format("'初始化Redis'检查 - 正在等待获取锁 (仅在异常情况下需要强制释放锁时可以执行: del %s)", RedisKeys.Public.INITIALIZE_LOCK));

        RedissonClient cli = RedissonUtil.getRedisson();
        RLock lock = cli.getLock(RedisKeys.Public.INITIALIZE_LOCK);
        /*
         * 获取锁后检查各项需要初始化的数据
         * 检查是每个服务器启动时必须的，可以无限等待，但不能在不确定数据正确的情况下启动
         */
        try {
            lock.lock(120, TimeUnit.SECONDS);
            log.info("'初始化Redis'检查 - 成功获得锁");

            initialize();
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
                log.info("'初始化Redis'检查 - 成功释放锁");
            }
        }
    }

    private void initialize() {
        // 这里进行各项功能数据检查，如果异常，应该抛出异常
        checkPlayerNameID();
    }

    /**
     * @return 在执行了更新数据时返回true，否则返回false。
     */
    private boolean checkPlayerNameID() {
//        String err = "'角色名_ID'检查 - 从Redis读取现有数据失败,需要检查Redis连接是否正常!";
        if (RedissonUtil.isEnable()) {
            OptionalInt lenOpt = RedissonUtil.hLen(RedisKeys.Public.PLAYER_NAME__ID);
            if (lenOpt.getAsInt() > 0) { // 这里无需检查值存在，如果不存在，就该让异常抛出来
                log.info(String.format("'角色名_ID'检查 - 检查到Redis中已存在数量为%d,检查完成", lenOpt.getAsInt()));
                return false;
            }
        }

        final int n = 100000;
        int idn = 0;
        int count = 0;
        for (; ; ) {
            List<PlayerIdName> list = playerDAO.getIdNames(idn, n);
            if (list.size() == 0) {
                break;
            }

            Map<String, Integer> cache = new HashMap<>(n << 1);
            for (PlayerIdName idName : list) {
                cache.put(idName.getName(), idName.getId());
            }
            count += cache.size();

            // 如果是恢复，在此时'Redis'是关闭的，需要绕过开关检查
            RedissonUtil.getRedisson().getMap(RedisKeys.Public.PLAYER_NAME__ID).putAll(cache);

            if (list.size() < n) {
                break;
            }
            idn += n;
        }
        log.info(String.format("'角色名_ID'检查 - 成功加载%d条数据到Redis,检查完成", count));
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("正在检查Redis初始数据，详细日志请查看$LogPath/redis/xxx.log");
        checkRedis();
    }
}
