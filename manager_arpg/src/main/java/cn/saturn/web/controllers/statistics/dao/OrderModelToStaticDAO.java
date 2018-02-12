package cn.saturn.web.controllers.statistics.dao;


import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog="cn.saturn.dataSource")
public interface OrderModelToStaticDAO {
	
	
	
	String FIELDS = "  order_id,create_time,finish_time,goods_id,goods_price,amount,server_id,account_id,account_name,player_name,platform,platform_order_no,pay_type,server_state,platform_order_no_sign,ext ";
	String TABLE ="order_static";
	
	@SQL("select finish_time from `order_static`  order by finish_time desc limit 1 ")
	public Date getMaxDay( );
	
	@SQL("select " + FIELDS + " from `order`  where finish_time >=:1 and finish_time <= :2 ")
	public List<OrderModelToStatic> getOrderModelToStatic( @SQLParam("startTime") String startTime,@SQLParam("endTime") String endTime);
	
	@SQL("replace into `" + TABLE + "` (" + FIELDS + ")values(:1.order_id,:1.create_time,:1.finish_time,:1.goods_id,:1.goods_price,:1.amount,:1.server_id,:1.account_id,:1.account_name,:1.player_name,:1.platform,:1.platform_order_no,:1.pay_type,:1.server_state,:1.platform_order_no_sign,:1.ext) ")
	public void insertOrUpdateList(@SQLParam("orderModelToStatic") List<OrderModelToStatic>  orderModelToStatic );
	
	@SQL("replace into `" + TABLE + "` (" + FIELDS + ")values(:1.order_id,:1.create_time,:1.finish_time,:1.goods_id,:1.goods_price,:1.amount,:1.server_id,:1.account_id,:1.account_name,:1.player_name,:1.platform,:1.platform_order_no,:1.pay_type,:1.server_state,:1.platform_order_no_sign,:1.ext) ")
	public void insertOrUpdate(@SQLParam("orderModelToStatic") OrderModelToStatic  orderModelToStatic );
}
