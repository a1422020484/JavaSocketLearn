package cn.saturn.web.controllers.poke.dao;

import java.util.List;


import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "")
public interface MailAcountDAO {
	
	String TABLE = "`sendaccount`";
	String FIELDS = "id, account,platform";
	
	@SQL("select " + FIELDS + " from " + TABLE )
	public List<MailAccount> getMailAccountList();
	

}
