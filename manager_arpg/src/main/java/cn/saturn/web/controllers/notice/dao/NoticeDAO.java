package cn.saturn.web.controllers.notice.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "cn.saturn.web.code")
public interface NoticeDAO {
	public static final String TABLE = "notice";
	public static final String KEYS = "`s_id`,`notice`, `enable`, `imgs`";

	@SQL("select count(*) from `" + TABLE + "`")
	public int getCount();
	
	@SQL("delete from `notice` where id=:1")
	public void delete(int id);
	
	@SQL("select * from `" + TABLE + "` limit :1,:2")
	public List<NoticeModel> get(int startRow, int pageSize);

	@SQL("select * from `" + TABLE + "` where id=:1 limit 1")
	public NoticeModel get(int id);
	
	@SQL("select * from `" + TABLE + "` where `enable` like '1'")
	public List<NoticeModel> getEnable();

	@SQL("select * from `" + TABLE + "`")
	public List<NoticeModel> get();

	@SQL("replace into `" + TABLE + "` (`id`, " + KEYS + ") values(:1.id,:1.s_id,:1.notice,:1.enable,:1.imgs) ")
	public int insertOrUpdate(NoticeModel data);

	@SQL("delete from `server` where id=:1 limit 1")
	public int remove(long id);

	// 读取最大ID
	@SQL("select max(id) from `" + TABLE + "` ")
	public Long getMaxId();

}
