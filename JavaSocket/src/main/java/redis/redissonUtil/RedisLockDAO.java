//package redis.redissonUtil;
//
//import net.paoding.rose.jade.annotation.DAO;
//import net.paoding.rose.jade.annotation.SQL;
//
///**
// * @author yangxp
// * 2020年7月28日上午11:39:24
// * <p>描述：
// */
//@DAO
//public interface RedisLockDAO {
//    String TABLE = "redis_lock";
//    String FIELDS = "id, lock_repairer, lock_time_repair";
//
//    @SQL("INSERT INTO " + TABLE + "(id) " +
//            "SELECT 1 FROM DUAL WHERE NOT EXISTS(SELECT id FROM " + TABLE + " WHERE id=1)")
//    void init();
//
//    /*
//     * 模拟'锁'的用法，锁有'有效时间'，过期则失效，锁不可重入
//     */
//    /**
//     *
//     * @param serverId
//     * @param timeout 锁超时时间，单位：秒
//     * @return 大于0表示获得锁成功
//     */
//    @SQL("UPDATE " + TABLE + " SET " +
//            "lock_repairer=:1," +
//            "lock_time_repair=NOW() " +
//            "WHERE id=1 AND (lock_repairer=0 || TIMESTAMPDIFF(SECOND,lock_time_repair,NOW())>:2)")
//    long getRepairLock(int serverId, long timeout);
//
//    /**
//     *
//     * @param serverId
//     * @return 大于0表示释放锁成功
//     */
//    @SQL("UPDATE " + TABLE + " SET " +
//            "lock_repairer=0," +
//            "lock_time_repair=NULL " +
//            "WHERE id=1 AND lock_repairer=:1")
//    long releaseRepairLock(int serverId);
//
//}
