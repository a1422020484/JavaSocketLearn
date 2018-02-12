package cn.saturn.web.controllers.statistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.saturn.operation.OperationExt;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.login.domain.AccountManager;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.LogDbConnectionDAO;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import zyt.spring.cache.entity.EntityFactorys;

/**
 * 1.事实查询游戏中的数据 , 比较消耗游戏性能.<br>
 * 2.匹配服务器id,server_id.<br>
 * 3.支持选择多个服务器数据 <br>
 * 
 * @author rodking @ 时时活跃表
 */
@Path("activeByServerReport")
public class ActiveByServerReportController {
	@Resource
	javax.sql.DataSource dataSource;

	@Resource
	LogDbConnectionDAO logDbConnDAO;

	@MainView
	@LoginCheck
	@Get("total")
	public String channelReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();

//		AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
//		List<String> platforms = accountManager.getDAO().getPlatforms();

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/activeByServerReport/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
//		request.setAttribute("platforms", platforms);
		request.setAttribute("servers", servers);

		return "activeByServerReport";
	}
	
	@MainView
	@LoginCheck
	@Get("online")
	public String onlineReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();

//		AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
//		List<String> platforms = accountManager.getDAO().getPlatforms();

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/activeByServerReport/onlineInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
//		request.setAttribute("platforms", platforms);
		request.setAttribute("servers", servers);

		return "onlineReportTotal";
	}

	@Get("totalInfo")
	public String channelReportTotalInfo(Invocation inv, @Param("srvIds") String srvIds,
			@Param("isSelect") int isSelect) throws Throwable {
		if (isSelect != 1)
			return "";
		if (null == srvIds)
			return "@请选择一个服务器id{param:server_id}";
		
		
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();
		int   serSize=servers.size();
		String[] srvStr= new String[serSize];
		
		for(int i=0;i<servers.size();i++){
			ServerModel  server=servers.get(i);
			String srvIdstr =  String.valueOf(server.getId()); 
			srvStr[i] = srvIdstr;
		}
		// 服务器ID
		String[] srvIdsOut =srvIds.split(",");
		
		// int c= a>b?1:0;
		String[] srvIds0 = (srvIdsOut.length == 1 && Integer.parseInt(srvIdsOut[0]) == -1) ? srvStr :srvIdsOut ;
		


		int key = 0;
		for (int i = 0; i < SystemLogParams.getKeys().length; i++) {
			if (SystemLogParams.getKeys()[i].equals("系统登录日志"))
				key = i;
		}
		List<String[]> resultOut = new ArrayList<>();

		String nowDay = DateUtils.getNowDay();
		String yesterday = DateUtils.getYesterdayStr();
		for (int i = 0; i < srvIds0.length; i++) {
			int sId = Integer.parseInt(srvIds0[i]);
			if(sId != -1){
				
			String result = OperationExt.queryLogFile(key, sId, nowDay);
			
			String resultYesterday = OperationExt.queryLogFile(key, sId, yesterday);
			// 如果包含@ 直接返回
			if (result.contains("@"))
				return result;

			String[] results = OperationExt.getResultMsgLast(result);
			
			String[] resultsYesterday = null;

			ServerModel sModel = ServerUtils.getServer(sId);
			String sName = "";
			if (sModel != null)
				sName = sModel.getName();

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (results.length == 2) {			
				resultsYesterday = OperationExt.getResultMsgLast(resultYesterday, format.format(new Date(format.parse(results[0]).getTime()-86400000)));
				
				if(resultsYesterday != null && resultsYesterday.length == 2)
					resultOut.add(new String[] { sName, results[0], results[1], resultsYesterday[1] });
				else
					resultOut.add(new String[] { sName, results[0], results[1], "0" });
				
			}else{
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(Calendar.SECOND, 0);
				String dateStr = format.format(calendar.getTime());
				
				resultsYesterday = OperationExt.getResultMsgLast(resultYesterday, dateStr);
				if(resultsYesterday != null && resultsYesterday.length == 2)
					resultOut.add(new String[] { sName, nowDay, "0", resultsYesterday[1] });
			}
		}
		}
		return "@" + OperationExt.queryToJson(new String[] {"服务器名称", "时间", "当前在线", "昨天在线" }, resultOut).toString();
	}
	
	@Get("onlineInfo")
	public String channelReportOnlineInfo(Invocation inv, @Param("srvId") int svrId , 
			@Param("startTime") String startTime, @Param("endTime") String endTime) throws Throwable {
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = null;
		if (svrId == -1){
			servers = serverComponent.getDAO().getList();
		}else{
			servers = new ArrayList<>();
			ServerModel model = new ServerModel();
			model.setId(svrId);
			servers.add(model);
		}
		int key = 0;
		for (int i = 0; i < SystemLogParams.getKeys().length; i++) {
			if (SystemLogParams.getKeys()[i].equals("系统登录日志"))
				key = i;
		}
		
		List<String> dateList = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = dateFormat.parse(startTime);
		Date endDate = dateFormat.parse(endTime);
		Date theDate = new Date(startDate.getTime());
		dateList.add(startTime);
		while(theDate.before(endDate)){
			theDate = new Date(theDate.getTime()+86400000l);
			if(theDate.before(endDate)){
				dateList.add(dateFormat.format(theDate));
			}
		}
		if(!endDate.equals(startDate))
			dateList.add(endTime);
		
		String[] titles = new String[]{"日期","00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00",
				"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00",
				"16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00",};
		List<String[]> resultOut = new ArrayList<>();
		String[] resultData;
		Map<String, Integer> resultMap = null;
		System.out.println("dateList->"+dateList+",svrId->"+svrId);
		for(String str : dateList){
			resultMap = new HashMap<>();
			resultData = new String[titles.length];
			resultData[0] = str;
			for(ServerModel model : servers){
				String result = OperationExt.queryLogFile(key, (int)model.getId(), str);
				List<String[]> list = OperationExt.getResultMsgList(result);
				for(String[] strArray :  list){
					try {
						String mapKey = strArray[0].split(" ")[1];
						if(resultMap.containsKey(mapKey))
							resultMap.put(mapKey, resultMap.get(mapKey) + Integer.parseInt(strArray[1]));
						else
							resultMap.put(mapKey, Integer.parseInt(strArray[1]));
					} catch (Exception e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
				}
			}
			
			int num = 0;
			for (int i=0; i< titles.length; i++) {
				Iterator<String> it = resultMap.keySet().iterator();
				while (it.hasNext()) {
					String itKey = it.next();
					if (itKey.equals(titles[i] + ":00")) {
						num = resultMap.get(itKey);
					}
				}
				if(i != 0)
					resultData[i] = num+"";
				num = 0;
			}
            resultOut.add(resultData);
			
		}
		
		return "@" + OperationExt.queryToJson(titles, resultOut).toString();
	}

}
