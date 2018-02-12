package cn.saturn.web.controllers.activity.dao;

import java.util.List;

import cn.saturn.web.model.auto.ModelDAO;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO
public interface ActivityDAO extends ModelDAO<ActivityModel> {
	public static final String TABLE = "activity";
	public static final String KEYS = "name,condition_desc,info,tips,type,activitySpeArgs,icon,activityOrder";

	@SQL("select id," + KEYS + " from `" + TABLE + "` where id=:1 limit 1")
	public ActivityModel get(@SQLParam("id") long id);

	@SQL("select id," + KEYS + " from `" + TABLE + "` ")
	public List<ActivityModel> getList();

	@SQL("update `" + TABLE
			+ "` set name=:1.name condition_desc=:1.condition_desc info=:1.info tips=1:tips type=1:type activitySpeArgs=1:activitySpeArgs icon=1:icon activityOrder=1:activityOrder where id=:1.id")
	public int update(ActivityModel itemModel);

	@Override
	@SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.name,1:condition_desc,1:info,1:tips,1:type,1:activitySpeArgs,1:icon,1:activityOrder) ")
	public void insertOrUpdate(ActivityModel itemModel);

	@Override
	@SQL("delete from `" + TABLE + "` where id=:1 limit 1")
	public void remove(long id);

	// 读取最大ID
	@Override
	@SQL("select max(id) from `" + TABLE + "` ")
	public Long getMaxId();
}