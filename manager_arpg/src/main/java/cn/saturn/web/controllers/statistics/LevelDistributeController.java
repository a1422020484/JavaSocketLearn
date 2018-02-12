package cn.saturn.web.controllers.statistics;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.saturn.operation.OperationExt;
import cn.saturn.operation.ReportSqlTemp;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerMergeDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeModel;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.LogDbConnectionDAO;
import cn.saturn.web.controllers.statistics.dao.LogDbConnectionModel;
import cn.saturn.web.utils.ReportTools;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

/**
 * @author rodking
 * @ 1-1 等级分布
 *
 */
@Path("levelDistribute")
public class LevelDistributeController {
	@Resource
	javax.sql.DataSource dataSource;

	@Resource
	LogDbConnectionDAO logDbConnDAO;
	
	@Resource
	ServerMergeDAO serverMergeDAO;
	
	@MainView
	@LoginCheck
	@Get("total")
	public String channelReportTotal(Invocation inv, @Param("type") int type) throws Throwable {

		/*ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();*/
		
		List<ServerMergeModel> servers=serverMergeDAO.getAllServerMerge();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/levelDistribute/totalInfo?type=" + type));
		request.setAttribute("servers", servers);

		return "levelDistribute";
	}

	@Get("totalInfo")
	public String channelReportTotalInfo(Invocation inv, @Param("type") int type, @Param("srvId") int srvId,
			@Param("isSelect") int isSelect) throws Throwable {

		if (isSelect != 1)
			return "";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		
		if (srvId == -1){
			return "@请选择一个服务器id{param:server_id}";
		}
			
			/*List<String[]> out=null;
			Map<Integer,Integer> map=null;
			List<LogDbConnectionModel> logDbConnectionModelList=logDbConnDAO.getList();
			for(LogDbConnectionModel logDbConnectionModel:logDbConnectionModelList){
				BasicDataSource dSource = OperationExt.createDataSource(logDbConnectionModel);
				List<String[]> levelList=ReportTools.select2DBList(template, dSource, type, srvId);
				for(String[] level:levelList){
					if(level.length>=2){
					int lev=Integer.valueOf(level[0]);
					int count=Integer.valueOf(level[1]);
					if(map !=null){
						Set<Integer>  mapkey=map.keySet();
						
						if(mapkey.contains(lev)){
							int mapcount=map.get(mapkey);
							map.remove(mapkey);
							map.put(lev, (count+mapcount));
						}else{
							map.put(lev, count);	
						}
					}else{
						map.put(lev, count);
					}
					
					}
				}
			}
			Set<Integer>  maplev=map.keySet();
			for(Integer lev:maplev){
				String levout=String.valueOf(lev);
				String  countout=String.valueOf(map.get(lev));
				String str[]= {levout,countout};
				out.add(str);
			}
			
			return OperationExt.queryToJson(temp.getTitle_ch(), out).toString();*/
		
		LogDbConnectionModel logDbConnModel = logDbConnDAO.get(srvId);
		if (logDbConnModel == null)
			return "@请配置服务器连接sql";

		BasicDataSource dSource = OperationExt.createDataSource(logDbConnDAO.get(srvId));
		return "@" + ReportTools.select2DB(template, dSource, type, srvId);
	}
}
