package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;
import java.util.List;

import cn.saturn.web.controllers.cdk.dao.UCGift;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;
import net.paoding.rose.jade.annotation.ShardBy;

@DAO(catalog="cn.saturn.web.code")
public interface GoodsOutPutDAO {
	String TABLE = "goodsoutput";
	String FIELDS = " goodsid,  quantity, createtime, serverid ";

	@SQL("select id " + FIELDS + " from goodsoutput where serverid=:1")
	public GoodsOutPut get( @SQLParam("serverid") int serverid);


	@SQL("replace into  " + TABLE + " ("  +  FIELDS  + ") values(:1.goodsid,:1.quantity,:1.createtime,:1.serverid) ")
	public void insertOrUpdate( @SQLParam("good") GoodsOutPut good);
	
	@SQL("replace into  " + TABLE + " ("  +  FIELDS  + ") values(:1.goodsid,:1.quantity,:1.createtime,:1.serverid) ")
	public void insertOrUpdate( @SQLParam("goods") List<GoodsOutPut> goods);
	
	@SQL("select  max(createtime)  from goodsoutput where serverid=:1")
	public Date getMaxDate( @SQLParam("serverid") int serverid);
	
	@SQL("delete FROM " + TABLE + "  where DATE_FORMAT(createtime,'%Y-%m-%d') = DATE_FORMAT(:1,'%Y-%m-%d') AND serverid = :2 ")
	public void  Delete( @SQLParam("daystr") Date daystr ,@SQLParam("srvId") int srvId);
	
	
	/*@SQL("select  max(createtime)  from goodsoutput where serverid=:1")
	public Date getMaxDate(@ShardBy @SQLParam("serverid") int serverid);*/
	
}
