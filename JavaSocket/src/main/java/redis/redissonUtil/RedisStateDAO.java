//package redis.redissonUtil;
//
//
///**
// * @author xiezuojie
// */
//@DAO
//public interface RedisStateDAO {
//
//    String TABLE = "redis_state";
//    String FIELD_SERVER_ID = "`server_id`";
//    String FIELDS = "`server_id`,`state`,`time`";
//
//    @SQL("SELECT server_id,`state`,`time` FROM " + TABLE + " WHERE server_id=:1")
//    RedisState get(int serverId);
//
//    @SQL("REPLACE INTO " + TABLE + "(" + FIELDS + ") VALUES(" +
//            ":1.serverId," +
//            ":1.state," +
//            ":1.time" +
//            ")")
//    void insertOrUpdate(RedisState st);
//
//    @SQL("INSERT INTO " + TABLE + "(" + FIELDS + ") " +
//            "SELECT 0, 'ON', NOW() FROM DUAL WHERE NOT EXISTS(SELECT server_id FROM " + TABLE + " WHERE server_id=0)")
//    void initGlobal();
//
//    @SQL("SELECT server_id,`state`,`time` FROM " + TABLE + " WHERE server_id=0")
//    RedisState getGlobal();
//
//    @SQL("UPDATE " + TABLE + " SET " +
//            "`state`='ON'," +
//            "`time`=NOW() " +
//            "WHERE server_id=0 AND `state`='OFF'")
//    void updateGlobalOn();
//
//    @SQL("UPDATE " + TABLE + " SET " +
//            "`state`='OFF'," +
//            "`time`=NOW() " +
//            "WHERE server_id=0 AND `state`='ON'")
//    void updateGlobalOff();
//
//}
