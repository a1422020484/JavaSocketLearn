package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;
import net.paoding.rose.jade.annotation.ShardBy;

@DAO(catalog="cn.saturn.web.code")
public interface GoodsConsumeDAO {
	
	String TABLE = "goodsconsume";
	String FIELDS = " goodsid,  quantity, createtime, serverid ";

	@SQL("select id " + FIELDS + " from goodsconsume where serverid=:1")
	public GoodsConsume get(@SQLParam("serverid") int serverid);


	@SQL("replace into  " + TABLE + " ("  +  FIELDS  + ") values(:1.goodsid,:1.quantity,:1.createtime,:1.serverid) ")
	public void insertOrUpdate( @SQLParam("good") GoodsConsume good);
	
	@SQL("replace into  " + TABLE + " ("  +  FIELDS  + ") values(:1.goodsid,:1.quantity,:1.createtime,:1.serverid) ")
	public void insertOrUpdate(@SQLParam("goods") List<GoodsConsume> goods);
	
	
	@SQL("replace into  " + TABLE + " ("  +  FIELDS  + ") values(:1.goodsid,:1.quantity,:1.createtime,:1.serverid) ")
	public void insertOrUpdate1(@SQLParam("goods") List<GoodsOutPut> goods);
	
	
	@SQL("select  max(createtime)  from goodsconsume where serverid=:1")
	public Date getMaxDate( @SQLParam("serverid") int serverid);
	
	
	@SQL("delete FROM " + TABLE + "  where DATE_FORMAT(createtime,'%Y-%m-%d') = DATE_FORMAT(:1,'%Y-%m-%d') AND serverid = :2 ")
	public void  Delete( @SQLParam("daystr") Date daystr ,@SQLParam("srvId") int srvId);

}
