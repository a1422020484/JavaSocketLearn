package cn.saturn.web.controllers.power.dao;

import cn.saturn.web.controllers.power.BanIp;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/**
 * @author xiezuojie
 */
@DAO(catalog = "cn.saturn.web.code")
public interface BanIpDAO {

    String TABLE = "ban_ip";
    String FIELDS = "ip,note";

    @SQL("select ip,note from ban_ip where 1=1  {  and ip=:3 }?  #if(:4) {and note like '%##(:4)%' }   limit :1,:2")  //like '%##(:4)##%'
    List<BanIp> getList(int start, int size,String ip,String note);

    @SQL("select count(ip) from ban_ip")
    int count(String ip);
    
    @SQL("select count(*) from ban_ip where 1=1 { and  ip=:1 }?  #if(:2) {and note like '%##(:2)%' } ")
    int queryCount(String ip,String note);

    @SQL("replace into ban_ip(" + FIELDS + ") values(" +
            ":1," +
            ":2" +
            ")")
    int insertOrUpdate(String ip, String note);

    @SQL("delete from ban_ip where ip=:1")
    int delete(String ip);

    @SQL("delete from ban_ip where ip in :1")
    int delete(List<String> ip);

}
