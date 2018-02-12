package cn.saturn.web.controllers.power.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

/**
 * @author xiezuojie
 */
@DAO(catalog = "cn.saturn.web.code")
public interface VindicatorIpDAO {
	String TABLE = "vindicator_ip";
	String FIELDS = "ip,note";

	@SQL("select " + FIELDS + " from `" + TABLE + "` ")
	List<VindicatorIpModel> getList();
	
	@SQL("select " + FIELDS + " from " + TABLE + "  limit :1,:2")
	public List<VindicatorIpModel> getVindicatorIp(int start, int size);

	@SQL("replace into `" + TABLE + "` (" + FIELDS + ") values(:1.ip,:1.note) ")
	public void insertOrUpdate(VindicatorIpModel model);

	@SQL("delete from `" + TABLE + "` where ip=:1 limit 1")
	public void remove(String ip);
}
