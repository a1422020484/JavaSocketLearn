package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog="cn.saturn.dataSource")
public interface LoginLogModelToStaticDAO {
	
	String FIELDS = "account_id,server_id,platform,register_time,last_log_time";
	String TABLE ="login_logstatic";
	
	@SQL("select last_log_time from `login_logstatic` order by last_log_time desc limit 1")
	public Date getMaxDate();
	
	@SQL("select " + FIELDS + " from `login_log` where last_log_time >=:1 and last_log_time <= :2 ")
	public List<LoginLogModelToStatic> getLoginLogModelToStatic( @SQLParam("startTime") String startTime,@SQLParam("endTime") String endTime);
	
	@SQL("replace into `" + TABLE + "` (" + FIELDS + ")values(:1.account_id,:1.server_id,:1.platform,:1.register_time,:1.last_log_time) ")
	public void insertOrUpdateList(@SQLParam("loginLogModelToStaticList") List<LoginLogModelToStatic>  LoginLogModelToStaticList );
	
	@SQL("replace into `" + TABLE + "` (" + FIELDS + ")values(:1.account_id,:1.server_id,:1.platform,:1.register_time,:1.last_log_time) ")
	public void insertOrUpdate(@SQLParam("loginLogModelToStatic") LoginLogModelToStatic  LoginLogModelToStatic );

}
