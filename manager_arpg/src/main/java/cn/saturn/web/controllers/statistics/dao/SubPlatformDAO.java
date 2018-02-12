package cn.saturn.web.controllers.statistics.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface SubPlatformDAO {
	public static final String TABLE = "subplatform";
	public static final String KEYS = "subPlatformId,name";

	@SQL("select " + KEYS + " from `" + TABLE + "` ")
	public List<SubPlatformModel> getList();
}
