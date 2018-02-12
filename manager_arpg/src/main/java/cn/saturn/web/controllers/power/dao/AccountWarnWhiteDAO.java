package cn.saturn.web.controllers.power.dao;

import java.util.List;

import cn.saturn.web.controllers.power.BanIp;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog = "cn.saturn.web.code")
public interface AccountWarnWhiteDAO {
	
	 	String TABLE = "account_warn_white";
	    String FIELDS = "accountid,roleid,playername,level,viplevel,yesdcrystal,todcrystal,gold,srvid";
	    
	    String FIELDSID = "id,accountid,roleid,playername,level,viplevel,yesdcrystal,todcrystal,gold,srvid";

	    @SQL("select id,accountid,roleid,playername,level,viplevel,yesdcrystal,todcrystal,gold,srvid from account_warn_white  where 1=1  ORDER BY srvid,playername limit :1,:2")  
	    List<AccountWarnWhiteModel> getAccountWarnModelList(int start, int size);
	    
	    @SQL("select id,accountid,roleid,playername,level,viplevel,yesdcrystal,todcrystal,gold,srvid from account_warn_white  ORDER BY srvid,playername")  
	    List<AccountWarnWhiteModel> getAccountWarnModelListAll();

	    @SQL("select count(id) from account_warn_white")
	    int count(int id);
	    
	    @SQL("replace into account_warn_white (" + FIELDS + ") values(:1.accountid,:1.roleid,:1.playername,:1.level,:1.viplevel,:1.yesdcrystal,:1.todcrystal,:1.gold,:1.srvid)")
	    void insertOrUpdate(@SQLParam("accountWarnModel") AccountWarnWhiteModel accountWarnModel);
	    
	    @SQL("replace into account_warn_white (" + FIELDSID + ") values(:1.id,:1.accountid,:1.roleid,:1.playername,:1.level,:1.viplevel,:1.yesdcrystal,:1.todcrystal,:1.gold,:1.srvid)")
	    void insertOrUpdateList(@SQLParam("accountWarnModelList") List<AccountWarnWhiteModel> accountWarnModelList);
	    
	    @SQL("delete from account_warn_white where id=:1")
	    int delete(int id);

	    @SQL("delete from account_warn_white where id in :1")
	    int delete(List<Integer> id);

}
