package cn.saturn.web.controllers.poke.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "")
public interface AccountModelDAO {
	
	@SQL("SELECT  player_id,srv_id FROM   account_bind ab LEFT JOIN  account a  ON a.id=ab.account_id where account like :1 and platform like :2 order by prev_srv_id, player_lv desc limit 1" )
	public AccountModel getMailAccountModel(String account,String platform);

}
