package cn.saturn.web.controllers.power.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

	/**
	 * 查询玩家
	 * @author zh
	 *
	 */

@DAO(catalog = "")
public interface TitleAccountDAO {
	
	@SQL("SELECT account_id, s.pid,`name`,player_id,player_name,player_lv FROM account_bind a LEFT JOIN server_merge  s ON a.srv_id=s.id  WHERE   player_name like :1 and s.pid like :2 limit :3,:4 ")
	public List<TitleAccount> getTitleAccountPage(String player_ame,String srvId,int page,int row);
	
	@SQL("SELECT account_id, s.id,`name`,player_id,player_name,player_lv FROM account_bind a LEFT JOIN server_merge  s ON a.srv_id=s.id  WHERE   player_name like :1 and s.pid like :2 ")
	public List<TitleAccount> getTitleAccount(String player_ame,String srvId);
	
	@SQL("SELECT count(*) FROM account_bind a LEFT JOIN server_merge  s ON a.srv_id=s.id  WHERE   player_name like :1 and s.pid like :2 ")
	public int getCount(String player_ame,String srvId);

}
