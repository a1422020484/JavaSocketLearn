package cn.saturn.web.controllers.statistics.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

/**
 * @author rodking
 * 存储过程
 */
@DAO//(catalog="cn.saturn.web.code")
public interface NextDayTimerDAO {

	@SQL("call ##(:1).player_acu();")
	public void playerACU(String homemgr);

	@SQL("call ##(:1).start_f_pay_retention();")
	public void firstPayRetention(String homemgr);
	
	@SQL("call ##(:1).start_ltv();")
	public void startLTV30(String homemgr);
	
	@SQL("call ##(:1).start_new_pay();")
	public void newPay(String homemgr);
	
	@SQL("call ##(:1).start_dau_summary();")
	public void serverDAU(String homemgr);
	
	@SQL("call ##(:1).start_ns_dau_summary();")
	public void newServerDAU(String homemgr);
	
	@SQL("call ##(:1).start_ns_income_summary();")
	public void newServerIncome(String homemgr);
	
	@SQL("call ##(:1).start_retention_player();")
	public void startRetentionPlayer(String homemgr);
			   
	@SQL("call ##(:1).start_new_user_change();")
	public void startNewUserChange(String homemgr);
	
	@SQL("call ##(:1).start_game_per_hour_online();")
	public void startGamePerHourOnline(String homemgr);
	
	@SQL("call ##(:1).stat_subChannel_task();")
	public void startSubChannel(String homemgr);
	
	@SQL("call ##(:1).stat_comp_task(null);")
	public void startCompTask(String homemgr);
	
	@SQL("call ##(:1).stat_comp(null);")
	public void startComp(String homemgr);
	
	@SQL("call ##(:1).stat_retention(:2);")
	public void statRetention(String homemgr,String dateStr);
	
	@SQL("call ##(:1).stat_retentionplatform(:2);")
	public void statRetentionplatform(String homemgr,String dateStr);
	
	@SQL("call ##(:1).stat_pay_retention(:2);")
	public void statPayRetention(String homemgr,String dateStr);
}
