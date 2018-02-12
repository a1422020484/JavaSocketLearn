package cn.saturn.web.controllers.server.dao;

import java.util.List;

import cn.saturn.web.model.auto.ModelDAO;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog="cn.saturn.web.code")
public interface ServerDAO extends ModelDAO<ServerModel> {
	public static final String TABLE = "server";
	public static final String KEYS = "`name`, `url`, `state`, `remark`, `section`, `recommend`, `priority`, `operate`, maintainText, platforms, openTime, `open_time`";

	@SQL("select id," + KEYS + " from `" + TABLE + "` ")
	public List<ServerModel> getList();

	@SQL("select id," + KEYS + " from `" + TABLE + "` where id=:1 limit 1")
	public ServerModel get(int id);

	@SQL("replace into `" + TABLE + "` (`id`," + KEYS + " ) values(:1.id,:1.name,:1.url,:1.state,:1.remark, :1.section, :1.recommend, :1.priority, :1.operate, :1.maintainText, :1.platforms, :1.openTime, :1.open_time) ")
	public void insertOrUpdate(ServerModel server);

	@SQL("delete from `server` where id=:1 limit 1")
	public void remove(long id);

	// 读取最大ID
	@SQL("select max(id) from `" + TABLE + "` ")
	public Long getMaxId();
}
