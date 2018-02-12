package cn.saturn.web.controllers.ucapi.gift;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/**
 * @author xiezuojie
 */
@DAO
public interface UCGiftDAO {

    String TABLE = "gift_uc";
    String FIELDS = "gift_id,type,content,count,title";

    @SQL("select " + FIELDS + " from gift_uc where gift_id=:1")
    UCGift get(String giftId);

    @SQL("select " + FIELDS + " from gift_uc")
    List<UCGift> getList();

}
