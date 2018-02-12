package cn.saturn.web.controllers.statistics.dao;

import java.util.List;

import cn.saturn.web.model.auto.ModelDAO;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface SummarySqlDAO extends ModelDAO<SummarySqlModel> {
	public static final String TABLE = "summary_sql";
	public static final String KEYS = "`sql`,title,`table`";

	@SQL("select id," + KEYS + " from `" + TABLE + "` ")
	public List<SummarySqlModel> getList();
	
	@SQL("select id," + KEYS + " from `" + TABLE + "` where id=:1")
	public SummarySqlModel get(long id);

	// 读取最大ID
	@Override
	@SQL("select max(id) from `" + TABLE + "` ")
	public Long getMaxId();
}
