package cn.saturn.web.controllers.power.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog = "cn.saturn.web.code")
public interface AccountWarnSealDAO {
	
	String TABLE = "account_warn_seal";
    String FIELDS = "accountid,roleid,playername,level,viplevel,crystal,gold,srvid,createtime";

    @SQL("select accountid,roleid,playername,level,viplevel,crystal,gold,srvid,createtime from account_warn_seal  where 1=1  ORDER BY srvid,playername limit :1,:2")  
    List<AccountWarnSealModel> getAccountWarnModelList(int start, int size);
    
    @SQL("select accountid,roleid,playername,level,viplevel,crystal,gold,srvid,createtime from account_warn_seal  ORDER BY srvid,playername")  
    List<AccountWarnSealModel> getAccountWarnSealModelListAll();

    @SQL("select count(id) from account_warn_seal")
    int count(int id);
    
    @SQL("insert into account_warn_seal (" + FIELDS + ") values(:1.accountid,:1.roleid,:1.playername,:1.level,:1.viplevel,:1.crystal,:1.gold,:1.srvid,:1.createtime)")
    void insertOrUpdate(@SQLParam("accountWarnSealModelList") List<AccountWarnSealModel> accountWarnSealModelList);
    
    @SQL("delete from account_warn_seal where id=:1")
    void delete(int id);
    
    @SQL("delete from account_warn_seal where createtime < :1")
    void deleteBeforeYesday(Date date);

    
}
