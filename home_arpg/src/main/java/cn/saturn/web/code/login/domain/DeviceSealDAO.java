package cn.saturn.web.code.login.domain;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface DeviceSealDAO {
	
	String TABLE = "`deviceseal`";
    String FIELDS = "del";
    
    @SQL("select id," + FIELDS + " from " + TABLE + " where del=:1 limit 1")
    DeviceSeal get(String del);

}
