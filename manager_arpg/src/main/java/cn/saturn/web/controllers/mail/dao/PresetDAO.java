package cn.saturn.web.controllers.mail.dao;

import java.util.List;

import cn.saturn.web.model.auto.ModelDAO;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog="cn.saturn.web.code")
public interface PresetDAO extends ModelDAO<PresetModel> {
	public static final String TABLE = "preset";
	public static final String KEYS = "type, info, remark";

	// @SQL("select id," + KEYS + " from `" + TABLE + "` ")
	// public List<ParamModel> getList();

	@SQL("select id," + KEYS + " from `" + TABLE + "` where id=:1 limit 1")
	public PresetModel get(long id);

	// @SQL("select id," + KEYS + " from `" + TABLE + "` where id=:1 and type=:2")
	// public List<PresetModel> get(long id, int type);

	@SQL("select id," + KEYS + " from `" + TABLE + "` where type=:1 limit :2,:3")
	public List<PresetModel> get(int type, int start, int count);

	@SQL("select count(id) from `" + TABLE + "` where type=:1 ")
	public int getCount(int type);

	@Override
	@SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.type,:1.info,:1.remark) ")
	public void insertOrUpdate(PresetModel model);

	@Override
	@SQL("delete from `" + TABLE + "` where id=:1 limit 1")
	public void remove(long id);

	// 读取最大ID
	@Override
	@SQL("select max(id) from `" + TABLE + "` ")
	public Long getMaxId();
	
	@ReturnGeneratedKeys
	@SQL("insert into `" + TABLE + "` (" + KEYS + ") values(:1.type,:1.info,:1.remark) ")
	public int insertNew(PresetModel model);
	
	@SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.type,:1.info,:1.remark) ")
	public void insertOrUpdateNew(PresetModel model);
	
	@SQL("select * from `" + TABLE + "` where type=:1 ")
	public List<PresetModel> getByType(int type);
	
}
