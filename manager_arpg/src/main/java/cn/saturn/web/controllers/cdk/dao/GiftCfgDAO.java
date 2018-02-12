package cn.saturn.web.controllers.cdk.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import java.util.List;

/**
 * @author xiezuojie
 */
@DAO(catalog="cn.saturn.web.code")
public interface GiftCfgDAO {

	String TABLE = "gift_cfg";
	String FIELDS = "gift_id,type,content,count";

	@SQL("select " + FIELDS + " from gift_cfg where gift_id=:1")
	UCGift get(String giftId);

	@SQL("select " + FIELDS + " from gift_cfg")
	List<UCGift> getList();

	@SQL("replace into " + TABLE + " (" + FIELDS + ") values(:1.giftId,:1.type,:1.content,:1.count) ")
	public Integer insertOrUpdate(UCGift model);

	@SQL("delete from `" + TABLE + "` where gift_id=:1 limit 1")
	public void remove(String id);
}
