package cn.saturn.web.controllers.mail.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import cn.saturn.web.model.auto.ModelDAO;

@DAO(catalog="cn.saturn.web.code")
public interface ParamDAO extends ModelDAO<ParamModel> {
	public static final String TABLE = "param";
	public static final String KEYS = "`type`, `info`";

	// @SQL("select id," + KEYS + " from `" + TABLE + "` ")
	// public List<ParamModel> getList();

	@SQL("select id," + KEYS + " from `" + TABLE + "` where id=:1")
	public ParamModel get(long id);

	@SQL("select id," + KEYS + " from `" + TABLE + "` where id=:1 and type=:2")
	public ParamModel get(long id, String type);

	@Override
	@SQL("replace `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.type,:1.info) ")
	public void insertOrUpdate(ParamModel model);

	@Override
	@SQL("delete from `" + TABLE + "` where id=:1 limit 1")
	public void remove(long id);

	// 读取最大ID
	@Override
	@SQL("select max(id) from `" + TABLE + "` ")
	public Long getMaxId();
}
