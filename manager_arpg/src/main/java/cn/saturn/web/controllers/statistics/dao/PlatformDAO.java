package cn.saturn.web.controllers.statistics.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface PlatformDAO {
	public static final String TABLE = "platform";
	public static final String KEYS = "platform,subPlatform_code,subPlatform_name";

	@SQL("select id," + KEYS + " from `" + TABLE + "` ")
	public List<PlatformModel> getList();

	@SQL("select id," + KEYS + " from `" + TABLE + "` where platform=:1")
	public List<PlatformModel> getListByPlaform(String platform);

	@SQL("select id," + KEYS + " from `" + TABLE + "` group by platform")
	public List<PlatformModel> getListPlaform();
}
