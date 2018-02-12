package cn.saturn.web.controllers.statistics;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.saturn.operation.OperationExt;
import cn.saturn.operation.ReportSqlTemp;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeModel;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import zyt.spring.component.ComponentManager;

@Path("order")
public class OrderController {

	
	@Resource
	javax.sql.DataSource dataSource;
	
	@Resource
	ServerDAO serverDAO;
	
	@MainView
	@LoginCheck
	@Get("total")
	public String serverReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		String dayTime = TimeUtils.getTodayStr();
		List<ServerModel> servers = serverDAO.getList();
		
		//List<ServerMergeModel>  servers =serverMergeDAO.getAllServerMerge();
		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/order/totalInfo?type=" + type));
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("servers", servers);

		return "order";
	}
	
	@LoginCheck
	@Get("totalInfo")
	public String serverReportTotalInfo(Invocation inv,@Param("type") int type,@Param("srvId") int srvId, @Param("thirdId") String thirdId,
			@Param("orderId") String orderId,@Param("accountId") String accountId,@Param("playerName") String playerName,
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("isSelect") int isSelect) throws Throwable {
		
		//"${sendUrl}&srvId="+ srvId + "&thirdId=" + thirdId+ "&orderId=" +orderId+ "&accountId=" + accountId+ "&playerName=" + playerName 
		//+ "&startTime=" + startTime + "&endTime=" + endTime+ "&isSelect=1";
		
		if (isSelect != 1)
			return "";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		DateParam param = new DateParam();
		String resultTime = "";
		resultTime = param.getParamHms(startTime, endTime);

		if (param.isError(resultTime))
			return resultTime;

		//return "@" + selectTable(template, type, channelId, srvId, resultTime);
		return "@" + selectTable(template, type, srvId,thirdId, orderId,accountId,playerName,resultTime);
	}

	
	private String selectTable(JdbcTemplate template,  int type, int serverId, String thirdId, String orderId,String accountId,String playerName ,String resultTime) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String thirdIdStr =null;
		String orderIdStr=null;
		String accountIdStr=null;
		String playerNameStr=null;
		String server_id = serverId == -1 ? "%" : serverId + "";
	
		if(thirdId.trim().equals(null) || thirdId.trim().equals("") ){
			thirdIdStr = "%" ;
		}else{
			thirdIdStr =thirdId.trim();
		}
		
		if(orderId.trim().equals(null) || orderId.trim().equals("")){
			orderIdStr="%" ;
		}else{
			orderIdStr=orderId.trim();
		}
		
		if(accountId.trim().equals(null) || accountId.trim().equals("")){
			accountIdStr="%" ;
		}else{
			accountIdStr=accountId.trim();
		}
		
		if(playerName.trim().equals(null) || playerName.trim().equals("")){
			playerNameStr="%" ;
		}else{
			playerNameStr="%"+playerName.trim()+"%";
		}
		
		if((thirdId.trim().equals(null) || thirdId.trim().equals(""))&& (orderId.trim().equals(null) || orderId.trim().equals("")) && 
				( accountId.trim().equals(null) || accountId.trim().equals("") ) && (playerName.trim().equals(null) || playerName.trim().equals("") )){
			return "@第三方订单ID, 订单ID,玩家账号,玩家名字必须输入一个查询!";
		}
		
		String sql = "";

		sql = MessageFormat.format(temp.getSql(),server_id ,thirdIdStr,orderIdStr,accountIdStr,playerNameStr,resultTime);
		//System.out.println(sql);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		
		return OperationExt.queryToJson(temp.getTitle_ch(), out);
	}

}
