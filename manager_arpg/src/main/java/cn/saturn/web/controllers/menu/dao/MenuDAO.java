package cn.saturn.web.controllers.menu.dao;

import java.util.List;

import cn.saturn.web.model.auto.ModelDAO;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog="cn.saturn.dataSource")
public interface MenuDAO extends ModelDAO<MenuModel> {
	public static final String TABLE = "menu";
	public static final String KEYS = "text,url,pid";

	@SQL("select id," + KEYS + " from `" + TABLE+"` where id=:id limit 1")
	public MenuModel get(@SQLParam("id") String id);

	@SQL("select id," + KEYS + " from `" + TABLE+"` ")
	public List<MenuModel> getList();

	@SQL("update `" + TABLE+"` set text=:1.text url=:1.url pid=:1.pid where id=:1.id")
	public int update(MenuModel menuModel);

	@Override
	@SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.text,:1.url,:1.pid) ")
	public void insertOrUpdate(MenuModel menuModel);

	@Override
	@SQL("delete from `" + TABLE+"` where id=:1 limit 1")
	public void remove(long id);

	// 读取最大ID
	@Override
	@SQL("select max(id) from `" + TABLE + "` ")
	public Long getMaxId();
}
