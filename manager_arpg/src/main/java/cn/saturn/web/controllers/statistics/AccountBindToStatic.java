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
import cn.saturn.web.controllers.statistics.dao.AccountBindModelToStatic;
import cn.saturn.web.controllers.statistics.dao.AccountBindModelToStaticDAO;
import cn.saturn.web.controllers.statistics.dao.LoginLogModelToStatic;
import cn.saturn.web.utils.DateUtils;

@Component
public class AccountBindToStatic {
	
	@Resource
	ServerMergeDAO serverMergeDAO;
	
	@Resource
	AccountBindModelToStaticDAO accountBindModelToStaticDAO;
	
	public void queryAccountBindToStatic() throws ParseException{
		
		//SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd");
		//数据拉取，无数据则从当前时间的365天前作为初始日期
		Date Dateday =DateUtils.getAddDay(new Date(), -365);
		
		//Date Dateday=sdf.parse("2014-01-01");
		
		Date maxDate = accountBindModelToStaticDAO.getMaxDay()==null?Dateday:accountBindModelToStaticDAO.getMaxDay();
		Date maxDate0=DateUtils.getZeroPoint(maxDate);
		Date nextDay=DateUtils.getAddDay(maxDate0, 1);
		
		while(DateUtils.isBeforeToday(nextDay)){
		
		//String   yesterdayStr= TimeUtils.getYesterdayStr();
		String startTime ="" ;
		String  endTime ="" ;
		try {
			startTime = TimeUtils.getParamHmsst(DateUtils.getDayStr(nextDay));
			endTime= TimeUtils.getParamHmsend(DateUtils.getDayStr(nextDay));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<AccountBindModelToStatic> accountBindModelToStaticList  =accountBindModelToStaticDAO.getAccountBindModelToStatic(startTime, endTime);
	
		for(AccountBindModelToStatic  accountBindModelToStatic:accountBindModelToStaticList){
			int srvId =accountBindModelToStatic.getSrv_id();
			Date createTime= accountBindModelToStatic.getCreate_time();
			ServerMergeModel serverMergeModel = serverMergeDAO.getServerMergeModel(srvId);
			if (serverMergeModel != null) {
			if(serverMergeModel.getId() != serverMergeModel.getPid() && (serverMergeModel.getMertime().before(createTime))){
				accountBindModelToStatic.setSrv_id(serverMergeModel.getPid());
			}
			}
			//accountBindModelToStaticDAO.insertOrUpdate(accountBindModelToStatic);
		}
		
		//批量插入
		for(int i=0; i<accountBindModelToStaticList.size();){
					
			int j=(i+10000)>accountBindModelToStaticList.size()?accountBindModelToStaticList.size():(i+10000);
			List<AccountBindModelToStatic> subaccountBindModelToStaticList=accountBindModelToStaticList.subList(i, j);
				
			accountBindModelToStaticDAO.insertOrUpdateList(subaccountBindModelToStaticList);
				
			i=i+10000;
		}	
		
		//System.out.println("accountbind" +startTime);
		nextDay= DateUtils.getAddDay(nextDay, 1);
		}
	}

}
