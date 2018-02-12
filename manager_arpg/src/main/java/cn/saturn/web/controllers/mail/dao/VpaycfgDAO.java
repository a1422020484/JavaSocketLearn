package cn.saturn.web.controllers.mail.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

import cn.saturn.web.model.auto.ModelDAO;

@DAO
public interface VpaycfgDAO extends ModelDAO<ParamModel> {
	public static final String TABLE = "vpay_cfg";
	public static final String KEYS = "goodId,price,name,discription";

	// @SQL("select id," + KEYS + " from `" + TABLE + "` ")
	// public List<ParamModel> getList();

	@SQL("select " + KEYS + " from `" + TABLE + "`")
	public List<VpaycfgModel> get();

	@SQL("select " + KEYS + " from `" + TABLE + "` where name=:1")
	public VpaycfgModel get(String name);

}
