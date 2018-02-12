package cn.saturn.web.controllers.statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.saturn.operation.TimeUtils;
import cn.saturn.web.controllers.server.dao.ServerMergeDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeModel;
import cn.saturn.web.controllers.statistics.dao.AccountModelToStatic;
import cn.saturn.web.controllers.statistics.dao.OrderModelToStatic;
import cn.saturn.web.controllers.statistics.dao.OrderModelToStaticDAO;
import cn.saturn.web.utils.DateUtils;

@Component
public class OrderToStatic {

	@Resource
	ServerMergeDAO serverMergeDAO;

	@Resource
	OrderModelToStaticDAO orderModelToStaticDAO;

	public void queryOrderModelToStatic() throws ParseException {

		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//Date Dateday = sdf.parse("2014-01-01");
		
		//数据拉取，无数据则从当前时间的365天前作为初始日期
		Date Dateday =DateUtils.getAddDay(new Date(), -365);
		
		Date maxDate = orderModelToStaticDAO.getMaxDay() == null ? Dateday : orderModelToStaticDAO.getMaxDay();
		Date maxDate0=DateUtils.getZeroPoint(maxDate);
		Date nextDay = DateUtils.getAddDay(maxDate0, 1);

		while (DateUtils.isBeforeToday(nextDay)) {

			// String yesterdayStr = TimeUtils.getYesterdayStr();
			String startTime = "";
			String endTime = "";
			try {
				startTime = TimeUtils.getParamHmsst(DateUtils.getDayStr(nextDay));
				endTime = TimeUtils.getParamHmsend(DateUtils.getDayStr(nextDay));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<OrderModelToStatic> orderModelToStaticList = orderModelToStaticDAO.getOrderModelToStatic(startTime,
					endTime);

			
			for (OrderModelToStatic orderModelToStatic : orderModelToStaticList) {
					// 去除玩家名字
					orderModelToStatic.setPlayer_name(null);
					int srvId = orderModelToStatic.getServer_id();
					Date finishtime = orderModelToStatic.getFinish_time();
					ServerMergeModel serverMergeModel = serverMergeDAO.getServerMergeModel(srvId);
					if (serverMergeModel != null) {
						if (serverMergeModel.getId() != serverMergeModel.getPid()&& (serverMergeModel.getMertime().before(finishtime))) {
							orderModelToStatic.setServer_id(srvId);
						}
					}
					//orderModelToStaticDAO.insertOrUpdate(orderModelToStatic);
				}
				//批量插入
				for(int i=0; i<orderModelToStaticList.size();){
				
				int j=(i+10000)>orderModelToStaticList.size()?orderModelToStaticList.size():(i+10000);
				List<OrderModelToStatic> suborderModelToStaticList=orderModelToStaticList.subList(i, j);
			
				orderModelToStaticDAO.insertOrUpdateList(suborderModelToStaticList);
			
				i=i+10000;
				}	
			
				// System.out.println("order" +startTime);
				nextDay = DateUtils.getAddDay(nextDay, 1);
			

		}
	}

}
