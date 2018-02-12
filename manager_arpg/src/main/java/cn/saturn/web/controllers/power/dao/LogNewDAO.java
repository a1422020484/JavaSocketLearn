package cn.saturn.web.controllers.power.dao;

import java.util.Date;
import java.util.List;

import cn.saturn.web.controllers.power.BanIp;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog = "jade.dataSource.cn.saturn.web.controllers.power.dao.LogNewDAO")
public interface LogNewDAO {
	
	String FIELDS ="player_id,name,type1,type2,log_time,crystal,gold,action_power,level,vip_level,content,time_point";
	
	 /*@SQL("SELECT id,player_id,name,type1,type2,log_time,crystal,gold,action_power,level,vip_level,content,time_point FROM log_22")  
	 List<LogNewModel> getList(String table);
	
	 
	 @SQL("SELECT count(*) FROM log_22")  
	 int getCount();*/
	
	@SQL("SELECT id,player_id,name,type1,type2,log_time,crystal,gold,action_power,level,vip_level,content,time_point FROM log_:1 WHERE player_id LIKE :2 AND log_time >=:3 AND log_time <:4 limit :5,:6")  
	 List<LogNewModel> getList(@SQLParam("srvId") int srvId,@SQLParam("player_Id") int player_Id,@SQLParam("starttime")Date starttime,@SQLParam("endtime")Date endtime,@SQLParam("start")int start,@SQLParam("size") int size);
	
	 
	 @SQL("SELECT count(*) FROM log_:1 WHERE player_id LIKE :2  AND log_time >=:3 AND log_time <:4")  
	 int getCount(@SQLParam("srvId") int srvId,@SQLParam("player_Id") int player_Id,@SQLParam("starttime")Date starttime,@SQLParam("endtime")Date endtime);
}
