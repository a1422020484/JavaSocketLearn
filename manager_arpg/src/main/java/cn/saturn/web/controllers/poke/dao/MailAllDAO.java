package cn.saturn.web.controllers.poke.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "")
public interface MailAllDAO {
	
	String TABLE = "`mailall`";
	String FIELDS = "srvId, playerId,award,createtime";
	
	@SQL("select " + FIELDS + " from " + TABLE + " where  BINARY   award=:1 and createtime >=:2 " )
	public List<MailAll> getMailAll(String award,Date createtime);
	
	//and createtime >=:2
	
	//,Date createtime

}
