package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog="cn.saturn.dataSource")
public interface AccountModelToStaticDAO {
	
	String TABLE = "account_static";
	String FIELDS = "id,account,password,platform,create_time,prev_srv_id,login_key,vindicator,systemInfo,login_time,lastip,version,platform_ext,accountActived,subPlatform,third_user_id,cdk_types ";
	
	@SQL("select create_time from account_static order by create_time desc limit 1 ")
	public Date getMaxDate();
	
	@SQL("select " + FIELDS + " from account where create_time >=:1 and create_time <= :2 ")
	public List<AccountModelToStatic> getAccountsToStatic( @SQLParam("startTime") String startTime,@SQLParam("endTime") String endTime);
	
	@SQL("replace into `" + TABLE + "` (" + FIELDS + ")values(:1.id,:1.account,:1.password,:1.platform,:1.create_time,:1.prev_srv_id,:1.login_key,:1.vindicator,:1.systemInfo,:1.login_time,:1.lastip,:1.version,:1.platform_ext,:1.accountActived,:1.subPlatform,:1.third_user_id,:1.cdk_types) ")
	public void insertOrUpdate(@SQLParam("accountToStaticModelList") List<AccountModelToStatic>  accountToStaticModelList );
	
	@SQL("replace into `" + TABLE + "` (" + FIELDS + ")values(:1.id,:1.account,:1.password,:1.platform,:1.create_time,:1.prev_srv_id,:1.login_key,:1.vindicator,:1.systemInfo,:1.login_time,:1.lastip,:1.version,:1.platform_ext,:1.accountActived,:1.subPlatform,:1.third_user_id,:1.cdk_types) ")
	public void insertOrUpdateSingel(@SQLParam("accountToStaticModel") AccountModelToStatic  accountToStaticModel );
	
}
