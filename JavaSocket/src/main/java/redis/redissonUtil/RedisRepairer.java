package redis.redissonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import xzj.saturn.util.GameUtils;

/**
 * 原Redis修复功能取消，原功能存在数据安全隐患
 *
 * @author xiezuojie
 */
public class RedisRepairer implements ApplicationContextAware {

    private static Logger log = LoggerFactory.getLogger("redis");

    static RedisStateDAO redisStateDAO;

    void redisOn() {
        RedisState st = getRedisState();
        st.on();
        redisStateDAO.insertOrUpdate(st);
    }

    void redisOff() {
        RedisState st = getRedisState();
        st.off();
        redisStateDAO.insertOrUpdate(st);
    }

    private RedisState getRedisState() {
        RedisState st = redisStateDAO.get(GameUtils.ServerId);
        if (st == null) {
            st = new RedisState();
            st.on(); // 初始为ON
        }
        return st;
    }

    private void init() {
        /*
         * 在服务器启动时，只有集群能正常访问并且数据正确时才能启动
         * ON: 正常启动
         * OFF: 即使集群能正常访问，由于之前访问Redis出现了异常关闭了Redis功能并将状态设为OFF，可能Redis缓存的数据已经过时，
         * 需要仔细确认Redis和数据库的数据一致性，如果不能确认，那么应该清除Redis的数据
         */
        RedisState st = getRedisState();
        if (!RedisState.STATE_ON.equals(st.state)) {
            System.err.println(String.format("检查到Redis集群功能已关闭，请查明关闭原因，在确保Redis集群数据正确后，" +
                            "将数据库中表`%s`里的%s为%s的状态改为'%s'才能启动服务器！\n" +
                            "1.可以清空Redis集群中所有数据，服务器运行时会重新加载数据到Redis集群，数据能保持一致，但此方法会影响数据加载速度\n" +
                            "2.可以检查服务器运行日志，检查异常原因是否会导致Redis集群和数据库数据不一致，如果不能确定一致性，建议清空Redis集群\n" +
                            "注意：清空Redis集群前要停止所有游戏服务器",
                    RedisStateDAO.TABLE, RedisStateDAO.FIELD_SERVER_ID, GameUtils.ServerId, RedisState.STATE_ON));
            System.exit(0);
        }

        redisOn();
        RedissonUtil.addStateListener(new RedisStateListener() {
            @Override
            public void onAvailable() {
            }

            @Override
            public void onUnavailable() {
                redisOff();
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        RedissonUtil.getRedisson(); // 加载Redis
        redisStateDAO = ctx.getBean(RedisStateDAO.class);
        init();
    }

}
