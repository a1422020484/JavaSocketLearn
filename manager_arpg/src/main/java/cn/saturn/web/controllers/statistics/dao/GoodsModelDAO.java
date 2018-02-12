package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog="")
public interface GoodsModelDAO {
	
	String TABLE = "goods";
	String FIELDS = "playerid, goodsid,  quantity, createtime, serverid,type,ext ";

	@SQL("select id " + FIELDS + " from goods where serverid=:1")
	public GoodsModel get( @SQLParam("serverid") int serverid);
	

	@SQL("replace into  " + TABLE + " ("  +  FIELDS  + ") values(:1.playerid,:1.goodsid,:1.quantity,:1.createtime,:1.serverid,:1.type,:1.ext) ")
	public void insertOrUpdate( @SQLParam("good") GoodsModel good);
	
	@SQL("replace into  " + TABLE + " ("  +  FIELDS  + ") values(:1.playerid,:1.goodsid,:1.quantity,:1.createtime,:1.serverid,:1.type,:1.ext) ")
	public void insertOrUpdate( @SQLParam("goods") List<GoodsModel> goods);
	
	@SQL("select  max(createtime)  from goods where serverid=:1")
	public Date getMaxDate( @SQLParam("serverid") int serverid);
	
	@SQL("delete FROM " + TABLE + "  where DATE_FORMAT(createtime,'%Y-%m-%d') = DATE_FORMAT(:1,'%Y-%m-%d') AND serverid = :2 ")
	public void  Delete( @SQLParam("daystr") Date daystr ,@SQLParam("srvId") int srvId);
	

}
