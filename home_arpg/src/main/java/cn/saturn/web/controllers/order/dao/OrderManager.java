package cn.saturn.web.controllers.order.dao;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cn.saturn.web.utils.OrderIdGen;

@Component
public class OrderManager implements ApplicationContextAware{
	private static OrderDAO orderDAO;
	
	public static void insert(Order order){
		orderDAO.insert(order);
	}
	
	/**
	 * 生成orderId
	 * @return
	 */
	public static long createOrderId(){
		long id = OrderIdGen.generate();
		if(orderDAO.countByOrderId(id)<=0)
			return id;
		for(int i=0; i<3; i++){
			id = OrderIdGen.generate();
			if(orderDAO.countByOrderId(id)<=0)
				return id;
		}
		return 0;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		orderDAO = ctx.getBean(OrderDAO.class);
	}

}
