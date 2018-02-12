package cn.saturn.web.controllers.award.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface AwardDAO{
	public static final String TABLE = "poke_mgr.tmp_award";
	public static final String KEYS = "svrId,account_id,account,player_id,num";

	@SQL("select " + KEYS + " from poke_mgr.tmp_award")
	public List<AwardModel> getList();
}