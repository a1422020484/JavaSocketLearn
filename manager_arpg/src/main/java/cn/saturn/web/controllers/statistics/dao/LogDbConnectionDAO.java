package cn.saturn.web.controllers.statistics.dao;

import java.util.List;

import cn.saturn.web.model.auto.ModelDAO;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface LogDbConnectionDAO extends ModelDAO<LogDbConnectionModel> {
	public static final String TABLE = "log_db";
	public static final String KEYS = "`url`,`root`,`pwd`";

	@SQL("select id," + KEYS + " from `" + TABLE + "` ")
	public List<LogDbConnectionModel> getList();
	
	@SQL("select id," + KEYS + " from `" + TABLE + "` where id=:1")
	public LogDbConnectionModel get(long id);

	// 读取最大ID
	@Override
	@SQL("select max(id) from `" + TABLE + "` ")
	public Long getMaxId();
}
