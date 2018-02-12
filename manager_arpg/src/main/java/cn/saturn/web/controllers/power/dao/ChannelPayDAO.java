package cn.saturn.web.controllers.power.dao;

import java.util.Date;
import java.util.List;

import cn.saturn.web.code.login.domain.AccountModel;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog = "cn.saturn.web.code")
public interface ChannelPayDAO {
	
	String TABLE = "ban_pay_pt";
	String FIELDS = "  platform,sub_platform,creattime,remark  ";
  

    @SQL("select id , " + FIELDS + " from " + TABLE + " where 1=1  limit :1,:2" )  
    List<ChannelPayModel> getList(int start,int rows);
    
    @SQL("select count(1) from " + TABLE  )  
    int getCount();
    
    @SQL("replace into " + TABLE + " (`id`, " + FIELDS + ") values(:1.id,:1.platform,:1.sub_platform,:1.creattime,:1.remark) ")
	public int insertOrUpdate(ChannelPayModel channelPayModel );

    
    @SQL("delete from " + TABLE + " where id=:1")
    int delete(String id);

    @SQL("delete from " + TABLE + " where ip in :1")
    int delete(List<Integer> id);
	
}
