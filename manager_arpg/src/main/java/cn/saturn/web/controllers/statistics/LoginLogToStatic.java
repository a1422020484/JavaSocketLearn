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
import cn.saturn.web.controllers.statistics.dao.LoginLogModelToStatic;
import cn.saturn.web.controllers.statistics.dao.LoginLogModelToStaticDAO;
import cn.saturn.web.utils.DateUtils;

@Component
public class LoginLogToStatic {
	
	@Resource
	ServerMergeDAO serverMergeDAO;
	
	@Resource
	LoginLogModelToStaticDAO loginLogModelToStaticDAO;
	
	public void queryLoginLogToStatic() throws ParseException{
		
		//SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd");
		//Date Dateday=sdf.parse("2014-01-01");
		
		//数据拉取，无数据则从当前时间的30天前作为初始日期
		Date Dateday =DateUtils.getAddDay(new Date(), -30);
		
		Date maxDate=loginLogModelToStaticDAO.getMaxDate()==null?Dateday:loginLogModelToStaticDAO.getMaxDate();
		Date maxDate0=DateUtils.getZeroPoint(maxDate);
		Date nextDay=DateUtils.getAddDay(maxDate0, 1);
		
		while(DateUtils.isBeforeToday(nextDay)){
		
		System.out.println("loginlog:"+nextDay);
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
		
		List<LoginLogModelToStatic> loginLogModelToStaticList  =loginLogModelToStaticDAO.getLoginLogModelToStatic(startTime, endTime);
		
		for(LoginLogModelToStatic  loginLogModelToStatic:loginLogModelToStaticList){
			int srvId =loginLogModelToStatic.getServer_id();
			Date lastLogTime= loginLogModelToStatic.getLast_log_time();
			ServerMergeModel serverMergeModel = serverMergeDAO.getServerMergeModel(srvId);
			if (serverMergeModel != null) {
			if(serverMergeModel.getId() != serverMergeModel.getPid() && (serverMergeModel.getMertime().before(lastLogTime))){
				loginLogModelToStatic.setServer_id(serverMergeModel.getPid());
			}
			}
			//loginLogModelToStaticDAO.insertOrUpdate(loginLogModelToStatic);
		}
		
		//批量插入
		for(int i=0; i<loginLogModelToStaticList.size();){
			
			int j=(i+10000)>loginLogModelToStaticList.size()?loginLogModelToStaticList.size():(i+10000);
			List<LoginLogModelToStatic> subloginLogModelToStaticList=loginLogModelToStaticList.subList(i, j);
		
			loginLogModelToStaticDAO.insertOrUpdateList(subloginLogModelToStaticList);
		
			i=i+10000;
		}
		//System.out.println("login" +startTime);
		nextDay= DateUtils.getAddDay(nextDay, 1);
		
		}	
	}
}
