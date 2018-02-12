package cn.saturn.web.controllers.yybapi.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface OpRecordDAO {
	String TABLE = "yyb_oprecord";
    String KEYS = "timestamp,appid,area,openid,partition,billno,roleid,midas_billno,money,gold";
    
    @SQL("INSERT INTO yyb_oprecord(`timestamp`,`appid`,`area`,`openid`,`partition`,`billno`,`roleid`,`midas_billno`,money,gold) VALUES(" +
            ":1.timestamp," +
            ":1.appid," +
            ":1.area," +
            ":1.openid," +
            ":1.partition," +
            ":1.billno," +
            ":1.roleid," +
            ":1.midas_billno," +
            ":1.money," +
            ":1.gold" +
            ")")
    int insert(OpRecord opRecord);
    
    @SQL("SELECT COUNT(1) FROM "+ TABLE +" WHERE billno=:1")
    int count(String billno);
}
