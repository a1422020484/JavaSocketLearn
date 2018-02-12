package cn.saturn.web.controllers.gift.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/**
 * @author xiezuojie
 */
@DAO
public interface GiftCfgDAO {

    String TABLE = "gift_cfg";
    String FIELDS = "gift_id,type,content,count";

    @SQL("select " + FIELDS + " from "+ TABLE +" where gift_id=:1")
    GiftCfg get(String giftId);

    @SQL("select " + FIELDS + " from "+TABLE)
    List<GiftCfg> getList();

}
