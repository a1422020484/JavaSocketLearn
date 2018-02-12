package cn.saturn.web.controllers.statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.saturn.operation.TimeUtils;
import cn.saturn.web.controllers.statistics.dao.AccountModelToStaticDAO;
import cn.saturn.web.controllers.statistics.dao.LoginLogModelToStatic;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.controllers.server.dao.ServerMergeDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeModel;
import cn.saturn.web.controllers.statistics.dao.AccountModelToStatic;
import cn.saturn.web.controllers.statistics.dao.AccountModelToStaticDAO;

@Component
public class AccountToStatic {
	
	@Resource
	ServerMergeDAO serverMergeDAO;
	
	@Resource
	AccountModelToStaticDAO  accountToStaticModelDAO;

	
	public void queryAccountToStatic() throws ParseException{
		
		//SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd");
		//Date Dateday=sdf.parse("2014-01-01");
		
		//数据拉取，无数据则从当前时间的365天前作为初始日期
		Date Dateday =DateUtils.getAddDay(new Date(), -365);
		
		Date  maxDate=accountToStaticModelDAO.getMaxDate()==null?Dateday:accountToStaticModelDAO.getMaxDate();
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
			
			List<AccountModelToStatic>  accountToStaticList = accountToStaticModelDAO.getAccountsToStatic(startTime, endTime);
			
			
			for(AccountModelToStatic accountToStatic:accountToStaticList){
	
				//编码问题，暂不取SystemInfo字段
				accountToStatic.setSystemInfo(null);
			  int  Prevsrvid=accountToStatic.getPrev_srv_id();
			  Date  createtime =accountToStatic.getCreate_time();
			  ServerMergeModel serverMergeModel = serverMergeDAO.getServerMergeModel(Prevsrvid);
			  if (serverMergeModel != null) {
			  if(serverMergeModel.getId() !=serverMergeModel.getPid() && (serverMergeModel.getMertime().before(createtime))){
				  accountToStatic.setPrev_srv_id(serverMergeModel.getPid());
			  }
			  }
			  //accountToStaticModelDAO.insertOrUpdateSingel(accountToStatic);
			}
			
			for(int i=0; i<accountToStaticList.size();){
				
				int j=(i+10000)>accountToStaticList.size()?accountToStaticList.size():(i+10000);
				List<AccountModelToStatic> subaccountToStaticList=accountToStaticList.subList(i, j);
			
				accountToStaticModelDAO.insertOrUpdate(subaccountToStaticList);
			
				i=i+10000;
			}
			
			//System.out.println("account" +startTime);
			
			nextDay= DateUtils.getAddDay(nextDay, 1);
			
		}
	}
}
