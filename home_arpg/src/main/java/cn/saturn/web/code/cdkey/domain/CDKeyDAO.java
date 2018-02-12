package cn.saturn.web.code.cdkey.domain;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface CDKeyDAO {
	String TABLE = "cdkey";
	String FIELDS = "`key`,`award`, `global`, `enable`, `serverid`, `playerid`,`overTime`,`platformLimit`,`type`,`useCount`,`useLimit`,`usedNum`";

	@SQL("select " + FIELDS + " from " + TABLE + " where `key`=:1 limit 1")
	public CDKey get(String key);

	@SQL("replace into " + TABLE + " (" + FIELDS + ") values(:1.key,:1.award,:1.global,:1.enable,:1.serverid,:1.playerid,:1.overTime,:1.platformLimit,:1.type, :1.useCount, :1.useLimit, :1.usedNum) ")
	public Integer insertOrUpdate(CDKey model);

	@SQL("replace into " + TABLE + " (" + FIELDS + ") values(:1.key,:1.award,:1.global,:1.enable,:1.serverid,:1.playerid,:1.overTime,:1.platformLimit,:1.type, :1.useCount, :1.useLimit, :1.usedNum) ")
	public void insertOrUpdate(List<CDKey> models);
	
	@SQL("select count(0) from "+TABLE +" where type=:1 and playerid=:2 and serverid=:3")
	public int getUsedNum(int type, int playerId, int svrId);
	@SQL("select "+ FIELDS +" from "+TABLE +" where type=:1 and playerid=:2 and serverid=:3")
	public List<CDKey> getUsedList(int type, int playerId, int svrId);
}
