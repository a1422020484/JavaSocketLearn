package cn.saturn.web.controllers.power.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface AccountWarnDAO {
	
	

    /**
     * 查询自动封号的账号信息
     * @return
     */
    @SQL("SELECT * FROM account_warn_all a WHERE  a.viplevel=0 AND a.crystal >200000 AND a.level <10   AND NOT EXISTS (SELECT 1 FROM (SELECT roleid,srvid FROM account_warn_seal UNION  (SELECT roleid,srvid FROM account_warn_white)) b WHERE a.srvid = b.srvid AND a.roleid = b.roleid )")  
    List<AccountWarnSealModel> getAutoAccountWarnAllModelListAll();
    
    
    /**
     * 查询白名单报警账号
     * @return
     */
    @SQL("SELECT  *  FROM account_warn_white a  WHERE   (a.todcrystal-a.yesdcrystal)>100000  OR (a.yesdcrystal-a.todcrystal)>200000")  
    List<AccountWarnWhiteModel> getAccountWarnWhiteModelListAll();
    
    
    
    /**
     * 查询异常账号信息(不包括封号和白名单内账号)
     * @return
     */
    @SQL("SELECT * FROM account_warn_all a WHERE  a.`srvid` not in(select id from server_merge where id <>pid and invalid=1)  AND NOT EXISTS (SELECT 1 FROM (SELECT roleid,srvid FROM account_warn_seal UNION  (SELECT roleid,srvid FROM account_warn_white) UNION(SELECT roleid,srvid FROM account_warn_all d WHERE  d.viplevel=0 AND d.crystal >200000 AND d.level <10 )) b WHERE a.srvid = b.srvid AND a.roleid = b.roleid) ORDER BY a.srvid")  
    List<AccountWarnSealModel> getAccountWarnModelAll();
    
    /**
     * 查询解封账号信息(昨天账号在封存，今天账号没封存的账号)
     * @return
     */
    @SQL("SELECT * FROM account_warn_seal a WHERE a.createtime >= :1 AND a.createtime < :2   AND NOT EXISTS (SELECT 1 FROM (SELECT roleid,srvid FROM account_warn_seal c WHERE c.createtime >= :3 AND c.createtime < :4 ) b WHERE a.srvid = b.srvid AND a.roleid = b.roleid )")  
    List<AccountWarnSealModel> getUnsetAccountWarnSealModelAll(Date yesdayStart,Date yesdayend,Date todayStart,Date todayend);
    

}
