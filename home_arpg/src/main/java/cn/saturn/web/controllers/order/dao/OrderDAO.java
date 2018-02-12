package cn.saturn.web.controllers.order.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLType;

/**
 * @author xiezuojie
 *
 */
@DAO
public interface OrderDAO {
	String TABLE = "`order`";
	String FIELDS = "order_id,create_time,finish_time,"
			+ "goods_id,goods_price,amount,"
			+ "server_id,"
			+ "account_id,account_name,player_name,"
			+ "platform,platform_order_no,platform_order_no_sign,"
			+ "pay_type,server_state,"
			+ "ext";

	@SQL("insert into " + TABLE + " (" + FIELDS + ") values("
			+ ":1.orderId,"
			+ ":1.createTime,"
			+ ":1.finishTime,"
			+ ":1.goodsId,"
			+ ":1.goodsPrice,"
			+ ":1.amount,"
			+ ":1.serverId,"
			+ ":1.accountId,"
			+ ":1.accountName,"
			+ ":1.playerName,"
			+ ":1.platform,"
			+ ":1.platformOrderNo,"
			+ ":1.platformOrderNoSign,"
			+ ":1.payType,"
			+ ":1.serverState,"
			+ ":1.ext"
			+ ")")
	int insert(Order order);
	
	@SQL(value = "/*master*/ select " + FIELDS + " from " + TABLE + " where order_id=:1", type = SQLType.READ)
	Order getOrder(long orderId);
	
	/**
	 * @param platformOrderNoSign 平台订单签名,平台标识+订单号
	 * @return
	 */
	@SQL(value = "/*master*/ select count(order_id) from" + TABLE + " where order_id=:1", type = SQLType.READ)
	int countByOrderId(long orderId);
	
	/**
	 * @param platformOrderNoSign 平台订单签名,平台标识+订单号
	 * @return
	 */
	@SQL(value = "/*master*/ select count(order_id) from" + TABLE + " where platform_order_no_sign=:1", type = SQLType.READ)
	int countByOrderNoSign(String platformOrderNoSign);
	
}
