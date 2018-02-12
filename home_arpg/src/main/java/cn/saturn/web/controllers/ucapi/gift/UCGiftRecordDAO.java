package cn.saturn.web.controllers.ucapi.gift;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

/**
 * @author xiezuojie
 */
@DAO
public interface UCGiftRecordDAO {

    String TABLE = "gift_uc_records";
    String FIELDS = "id,gift_id,server_id,player_id,uc_account_id,receive_time,unique_key";

    @SQL("SELECT COUNT(id) FROM gift_uc_records WHERE unique_key=:1")
    int count(String uniqueKey);

    @SQL("INSERT INTO gift_uc_records (gift_id,server_id,player_id,uc_account_id,receive_time,unique_key) VALUES(" +
            ":1.giftId," +
            ":1.serverId," +
            ":1.playerId," +
            ":1.ucAccountId," +
            ":1.receiveTime," +
            ":1.uniqueKey" +
            ")")
    int insert(UCGiftRecord record);
}
