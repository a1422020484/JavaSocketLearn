package cn.saturn.web.controllers.server.dao;

import java.util.List;

import cn.saturn.web.model.auto.ModelDAO;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog="cn.saturn.web.code")
public interface VersionDAO extends ModelDAO<VersionModel> {
	public static final String TABLE = "version";
	public static final String KEYS = "platform,version, url, notice";

	@SQL("select id," + KEYS + " from `" + TABLE + "` ")
	public List<VersionModel> getList();

	@Override
	@SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.platform,:1.version,:1.url,:1.notice) ")
	public void insertOrUpdate(VersionModel model);

	// @SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.platform,:1.version,:1.url,:1.notice) ")
	// public void insert(VersionModel model);

	@Override
	@SQL("delete from `" + TABLE + "` where id=:1 limit 1")
	public void remove(long id);

	// 读取最大ID
	@Override
	@SQL("select max(id) from `" + TABLE + "` ")
	public Long getMaxId();
}
