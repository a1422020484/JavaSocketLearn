package cn.saturn.web.controllers.gift.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

/**
 * @author xiezuojie
 */
@DAO
public interface GiftRecordDAO {

    String TABLE = "gift_records";
    String FIELDS = "id,gift_id,server_id,player_id,account_id,receive_time,unique_key";

    @SQL("SELECT COUNT(id) FROM gift_records WHERE unique_key=:1")
    int count(String uniqueKey);

    @SQL("INSERT INTO gift_records (gift_id,server_id,player_id,account_id,receive_time,unique_key) VALUES(" +
            ":1.giftId," +
            ":1.serverId," +
            ":1.playerId," +
            ":1.accountId," +
            ":1.receiveTime," +
            ":1.uniqueKey" +
            ")")
    int insert(GiftRecord record);
}
