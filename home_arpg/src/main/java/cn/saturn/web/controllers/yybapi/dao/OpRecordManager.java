package cn.saturn.web.controllers.yybapi.dao;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class OpRecordManager implements ApplicationContextAware{
	private static OpRecordDAO opRecordDAO;
	
	/**
	 * 插入操作记录
	 * @param opRecord
	 */
	public static void insertRecord(OpRecord opRecord){
		opRecordDAO.insert(opRecord);
	}
	
	/**
	 * 判断记录是否存在
	 * @param billno
	 * @return
	 */
	public static boolean isExists(String billno){
		return opRecordDAO.count(billno)>0;
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		opRecordDAO = ctx.getBean(OpRecordDAO.class);
	}

}
