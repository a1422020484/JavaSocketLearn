package cn.saturn.web.controllers.logs.dao;

import java.util.List;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog="cn.saturn")
public interface LogDAO {
	String TABLE = "`logs`";
	String FIELDS = "user_id, user_name, type, log_time, content";

	@SQL("select id," + FIELDS + " from " + TABLE)
	public List<LogModel> getList();

	@SQL("select id," + FIELDS + " from " + TABLE + " where type=:type")
	public List<LogModel> getByType(@SQLParam("type") int type);

	@SQL("select id," + FIELDS + " from " + TABLE + " where type like :1 and log_time ##(:2) order by log_time desc")
	public List<LogModel> getByType(int type, String timeParam);

	@SQL("insert into " + TABLE + " ("+ FIELDS + ") values(" + ":1.user_id," + ":1.user_name," + ":1.type," + "NOW(),"
			+ ":1.content" + ")")
	public void insert(LogModel logs);
	
	@SQL("SELECT id,"+FIELDS+" FROM "+ TABLE +" WHERE type =:1 ORDER BY log_time DESC limit 1")
	public LogModel getLastLog(int type);
	
	@SQL("delete from " + TABLE+" where id=:1 limit 1")
	public void remove(int id);

}
