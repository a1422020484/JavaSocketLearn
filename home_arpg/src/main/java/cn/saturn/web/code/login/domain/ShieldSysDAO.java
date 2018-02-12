package cn.saturn.web.code.login.domain;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface ShieldSysDAO {
    String TABLE = "`shield_sys`";
    String FIELDS = "version, closedSubPlatform, redeemSys, webSite, contactCust,rankingSys,monthCard,silentDownloadRes,fbShare,abPay,weixin";

    @SQL("select id," + FIELDS + " from " + TABLE )
    List<ShieldSysModel> getList();
}
