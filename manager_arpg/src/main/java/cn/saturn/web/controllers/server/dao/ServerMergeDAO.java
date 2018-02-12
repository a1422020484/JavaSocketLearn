package cn.saturn.web.controllers.server.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog="cn.saturn.web.code")
public interface ServerMergeDAO {
	String TABLE = "server_merge";
	String FIELDS ="name,pid,state,mertime,invalid";
	
	@SQL("replace into  " + TABLE + " ( id ," +  FIELDS  + ") values(:1.id,:1.name,:1.pid,:1.state,:1.mertime,:1.invalid) ")
	public void insertOrUpdate( @SQLParam("serverMergeModel") ServerMergeModel serverMergeModel);
	
	@SQL("delete FROM " + TABLE + "  where  id = :1 ")
	public void  Delete(@SQLParam("id") int id);
	
	@SQL("select id ,name,pid,state,mertime,invalid from server_merge where id=:1")
	public ServerMergeModel getServerMergeModel( @SQLParam("id") int id);
	
	@SQL("select id ,name,pid,state,mertime,invalid from server_merge where invalid=1 and pid=:1")
	public List<ServerMergeModel> getServerMergeModelPid( @SQLParam("pid") int pid);
	
	@SQL("select id ,name,pid,state,mertime,invalid from server_merge where id =pid and id <> 0 ")
	public List<ServerMergeModel> getAllServerMerge();
	
	@SQL("select id ,name,pid,state,mertime,invalid from server_merge where id =pid ")
	public List<ServerMergeModel> getAllServerMergezone();
	
	@SQL("select id ,name,pid,state,mertime,invalid from server_merge where id <>pid ")
	public List<ServerMergeModel> getIdnPidServerMerge();
}
