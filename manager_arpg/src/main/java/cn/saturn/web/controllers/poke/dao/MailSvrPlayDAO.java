package cn.saturn.web.controllers.poke.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "")
public interface MailSvrPlayDAO {
	
	String TABLE = "`mailsvrplay`";
	String FIELDS = "srvId, playerId,crystal";
	
	@SQL("select " + FIELDS + " from " + TABLE )
	public List<MailSvrPlay> getMailSvrPlay();

}
