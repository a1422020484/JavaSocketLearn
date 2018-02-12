package cn.saturn.web.code.login.domain;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface LoginLogDAO {
	String TABLE = "`login_log`";
	String FIELDS = "account_id, server_id, platform, register_time,last_log_time";

	@SQL("replace into " + TABLE + " (" + FIELDS
			+ ") values(:1.account_id,:1.server_id,:1.platform,:1.register_time,:1.last_log_time) ")
	public int insertOrUpdate(LoginLogModel accountModel);

	@ReturnGeneratedKeys
	@SQL("insert into " + TABLE + " (" + FIELDS + ")  values(:1.account_id,:1.server_id,:1.platform,:1.register_time,:1.last_log_time)")
	public Long insert(LoginLogModel accountModel);
	
	@SQL("delete from " + TABLE + " where datediff(now(),log_time) > :1")
	int deleteOverdue(int day);

}
