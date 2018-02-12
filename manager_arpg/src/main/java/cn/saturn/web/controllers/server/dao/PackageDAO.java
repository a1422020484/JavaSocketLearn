package cn.saturn.web.controllers.server.dao;

import java.util.List;

import cn.saturn.web.model.auto.ModelDAO;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog="cn.saturn.web.code")
public interface PackageDAO extends ModelDAO<PackageModel> {
	public static final String TABLE = "package";
	public static final String KEYS = "platform, version, resversion, resurl, notice,type";

	@SQL("select id," + KEYS + " from `" + TABLE + "` ")
	public List<PackageModel> getList();

	@Override
	@SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.platform,:1.version,:1.resversion,:1.resurl,:1.notice,:1.type) ")
	public void insertOrUpdate(PackageModel model);

	@Override
	@SQL("delete from `" + TABLE + "` where id=:1 limit 1")
	public void remove(long id);

	// 读取最大ID
	@Override
	@SQL("select max(id) from `" + TABLE + "` ")
	public Long getMaxId();
}
