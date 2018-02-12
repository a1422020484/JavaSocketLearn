package cn.saturn.web.controllers.statistics;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import proto.ProtocolWeb;
import proto.ProtocolWeb.GetSystemLogCS;
import zyt.spring.component.ComponentManager;
import cn.saturn.operation.OperationExt;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.GoodsConsume;
import cn.saturn.web.controllers.statistics.dao.GoodsConsumeDAO;
import cn.saturn.web.controllers.statistics.dao.GoodsOutPut;
import cn.saturn.web.controllers.statistics.dao.GoodsOutPutDAO;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.LogsReaderWriter;
import cn.saturn.web.utils.Utils;

@Path("filePathLogCon")
public class FilePathLogController {
	
	@Resource
	LogsReaderWriter logsReaderWriter;
	
	@Resource
	GoodsOutPutDAO goodsOutPutDAO;

	@Resource
	GoodsConsumeDAO goodsConsumeDAO;

	@MainView
	@LoginCheck
	@Get("show")
	public String statistics(Invocation inv) throws Throwable {
		String dayTime = TimeUtils.getTodayStr();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		//request.setAttribute("sendUrl", Utils.url(inv, "/statistics/filePathLogCon/toAdd"));
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/filePathLogCon"));
		request.setAttribute("dayTime", dayTime);

		return "goodsLog";
	}

	@Get("toAdd")
	public String toAdd(Invocation inv, @Param("dayStr") String dayStr, @Param("srvId") int srvId,
			@Param("type") int type) throws Throwable {
		
		Map<Date, Map<Integer, Integer>> goodsmap;
		Map<Integer, Integer> intmap;
		List<GoodsOutPut> goodsoutlist = new ArrayList<GoodsOutPut>();
		List<GoodsConsume> goodsconlist = new ArrayList<GoodsConsume>();
		String strMsg ;
		
		if (type == 0) {
			
			 strMsg = FilePathLogController.queryLogFile(type,srvId, dayStr);
			if (strMsg != null && strMsg != "") {
				goodsmap = logsReaderWriter.readString(strMsg);

				for (Date createtime : goodsmap.keySet()) {

					intmap = goodsmap.get(createtime);
					Set<Integer> goodsids = intmap.keySet();
					for (int goodsid : goodsids) {
						GoodsOutPut goodsout = new GoodsOutPut();
						goodsout.setCreatetime(createtime);
						int quantitys = intmap.get(goodsid);
						goodsout.setGoodsid(goodsid);
						goodsout.setQuantity(quantitys);
						//
						goodsout.setServerid(srvId);
						goodsoutlist.add(goodsout);
					}
				}
				if (goodsoutlist.size() != 0) {
					goodsOutPutDAO.insertOrUpdate(goodsoutlist);
					goodsoutlist.clear();

				}

			}
		}
		if (type == 1) {

			 strMsg = FilePathLogController.queryLogFile(type,srvId, dayStr);
			// String strMsg = FilePathLog.queryFilePath(type, srvId,
			// yesStr);
			if (strMsg != null && strMsg != "") {
				goodsmap = logsReaderWriter.readString(strMsg);

				for (Date createtime : goodsmap.keySet()) {

					intmap = goodsmap.get(createtime);
					Set<Integer> goodsids = intmap.keySet();
					for (int goodsid : goodsids) {
						GoodsConsume goodscon = new GoodsConsume();
						goodscon.setCreatetime(createtime);
						int quantitys = intmap.get(goodsid);
						goodscon.setGoodsid(goodsid);
						goodscon.setQuantity(quantitys);
						goodscon.setServerid(srvId);
						goodsconlist.add(goodscon);
					}
				}
				if (goodsconlist.size() != 0) {
					goodsConsumeDAO.insertOrUpdate(goodsconlist);
					goodsconlist.clear();

				}
			}
		}
		return "@追加成功";
		
	}
	
	
	@Get("toDelete")
	public String toDelete(Invocation inv, @Param("dayStr") String dayStr, @Param("srvId") int srvId,
			@Param("type") int type) throws Throwable {
		 //String daySt ="'"+dayStr+"'";
		if(type == 0){
			
			goodsOutPutDAO.Delete(DateUtils.parse(dayStr), srvId);
		}
		if(type == 1){
			
			goodsConsumeDAO.Delete(DateUtils.parse(dayStr), srvId);
		}
		
		return "@删除成功";
	}
	
	
	
	
	/**
	 * 查询日志路径 <br>
	 * 
	 * @param fileRelPath
	 * @param srvId
	 * @return
	 */
	public static String queryLogFile(int type, int srvId, String dayStr) {
		// 生成文本路径
		String value = SystemLogClock.getParams(type);
		if (value == null)
			return "@没有日志类型";

		String fileRelPath = MessageFormat.format(value, dayStr);
		// 查找服务器
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		ServerModel serverModel = serverComponent.find(srvId);
		if (serverModel == null)
			return "@服务器不存在";

		// 连接服务器
		IClient client = serverModel.createClient();
		if (client == null)
			return "@服务器Url配置错误";
		// 发送消息
		GetSystemLogCS.Builder cs = GetSystemLogCS.newBuilder();
		cs.setLogFileRelPath(fileRelPath);

		ProtocolWeb.GetSystemLogSC retMsg = client.call(Head.H14004, cs.build(), ProtocolWeb.GetSystemLogSC.class);
		if (retMsg == null )
			return "@请求失败:" + client.getErrStr();
		
		return retMsg.getContent();
	}
	
}
