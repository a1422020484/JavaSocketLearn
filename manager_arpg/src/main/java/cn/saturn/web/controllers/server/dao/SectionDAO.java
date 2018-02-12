package cn.saturn.web.controllers.server.dao;

import java.util.List;

import cn.saturn.web.model.auto.ModelDAO;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog="cn.saturn.web.code")
public interface SectionDAO extends ModelDAO<SectionModel> {
	public static final String TABLE = "section";
	public static final String KEYS = "`name`,`state`, recommend, `tag`, `platforms`";

	@SQL("select id," + KEYS + " from `" + TABLE + "` ")
	public List<SectionModel> getList();

	@Override
	@SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.name,:1.state,:1.recommend,:1.tag,:1.platforms) ")
	public void insertOrUpdate(SectionModel model);

	@Override
	@SQL("delete from `" + TABLE + "` where id=:1 limit 1")
	public void remove(long id);

	// 读取最大ID
	@Override
	@SQL("select max(id) from `" + TABLE + "` ")
	public Long getMaxId();
}
