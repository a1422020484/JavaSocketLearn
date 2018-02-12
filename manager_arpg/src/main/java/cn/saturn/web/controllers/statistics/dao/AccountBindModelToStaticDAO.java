package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog="cn.saturn.dataSource")
public interface AccountBindModelToStaticDAO {
	

	String TABLE = "account_bind_static";
	String FIELDS = "id,account_id,srv_id,player_id,player_name,player_lv,create_time,iconUrl,iconFrame,fightingCapacity,vipLv";
	
	@SQL("select create_time from account_bind_static order by  create_time desc  limit 1 ")
	public Date getMaxDay();
	
	@SQL("select " + FIELDS + " from account_bind where create_time >=:1 and create_time <= :2 ")
	public List<AccountBindModelToStatic> getAccountBindModelToStatic( @SQLParam("startTime") String startTime,@SQLParam("endTime") String endTime);
	
	@SQL("replace into `" + TABLE + "` (" + FIELDS + ")values(:1.id,:1.account_id,:1.srv_id,:1.player_id,:1.player_name,:1.player_lv,:1.create_time,:1.iconUrl,:1.iconFrame,:1.fightingCapacity,:1.vipLv) ")
	public void insertOrUpdateList(@SQLParam("accountBindModelToStatic") List<AccountBindModelToStatic>  accountBindModelToStaticList );
	
	@SQL("replace into `" + TABLE + "` (" + FIELDS + ")values(:1.id,:1.account_id,:1.srv_id,:1.player_id,:1.player_name,:1.player_lv,:1.create_time,:1.iconUrl,:1.iconFrame,:1.fightingCapacity,:1.vipLv) ")
	public void insertOrUpdate(@SQLParam("accountBindModelToStatic") AccountBindModelToStatic  accountBindModelToStatic );

}
