package cn.saturn.web.controllers.server.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "cn.saturn.web.code")
public interface ShieldSysDAO {
	String TABLE = "`shield_sys`";
	String FIELDS = "version,closedSubPlatform, redeemSys, webSite, contactCust,rankingSys,monthCard,silentDownloadRes,fbShare,abPay,weixin";

	@SQL("select id," + FIELDS + " from " + TABLE)
	List<ShieldSysModel> getList();

	@SQL("select id," + FIELDS + " from " + TABLE + " limit :1,:2")
	List<ShieldSysModel> getList(int start, int end);

	@SQL("select id," + FIELDS + " from " + TABLE + " where id=:1")
	ShieldSysModel find(long id);

	@SQL("insert into " + TABLE + " (" + FIELDS
			+ ") values(:1.version,:1.closedSubPlatform,:1.redeemSys,:1.webSite,:1.contactCust,:1.rankingSys,:1.monthCard,:1.silentDownloadRes,:1.fbShare,:1.abPay,:1.weixin) ")
	public void insert(ShieldSysModel model);

	@SQL("update " + TABLE
			+ " set version=:1.version,closedSubPlatform=:1.closedSubPlatform,redeemSys=:1.redeemSys,webSite=:1.webSite,contactCust=:1.contactCust,rankingSys=:1.rankingSys,monthCard=:1.monthCard,silentDownloadRes=:1.silentDownloadRes,fbShare=:1.fbShare,abPay=:1.abPay,weixin=:1.weixin where id=:1.id")
	public int update(ShieldSysModel model);

	@SQL("delete from " + TABLE + " where id=:1 limit 1")
	public void remove(long id);

}
