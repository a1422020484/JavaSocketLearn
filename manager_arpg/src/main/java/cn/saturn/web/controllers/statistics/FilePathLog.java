package cn.saturn.web.controllers.statistics;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeModel;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.dao.GoodsConsume;
import cn.saturn.web.controllers.statistics.dao.GoodsConsumeDAO;
import cn.saturn.web.controllers.statistics.dao.GoodsModel;
import cn.saturn.web.controllers.statistics.dao.GoodsModelDAO;
import cn.saturn.web.controllers.statistics.dao.GoodsOutPut;
import cn.saturn.web.controllers.statistics.dao.GoodsOutPutDAO;
import cn.saturn.web.controllers.statistics.dao.OrderModelToStatic;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.LogsReaderWriter;
import proto.ProtocolWeb;
import proto.ProtocolWeb.GetSystemLogCS;
import zyt.spring.component.ComponentManager;

@Component
public class FilePathLog {

	@Resource
	ServerDAO serverDAO;
	
	@Resource
	ServerMergeDAO serverMergeDAO;

	@Resource
	GoodsOutPutDAO goodsOutPutDAO;

	@Resource
	GoodsConsumeDAO goodsConsumeDAO;
	
	@Resource
	LogsReaderWriter logsReaderWriter;
	
	@Resource
	GoodsModelDAO goodsModelDAO;
	
	protected static final Logger logger = LoggerFactory.getLogger(LogType.goods);
	/**
	 * 
	 * @param 根据type类型，遍历svrId查询
	 * @return
	 * @throws Throwable
	 */

	// @Get("send")
	// Invocation inv, @Param("type") int type
	public void queryGoodsInfo(int type) throws Throwable {

		Map<Date, Map<Integer, Integer>> goodsmap;
		Map<Integer, Integer> intmap;
		List<GoodsOutPut> goodsoutlist = new ArrayList<GoodsOutPut>();
		List<GoodsConsume> goodsconlist = new ArrayList<GoodsConsume>();
		//LogsReaderWriter logsReaderWriter = new LogsReaderWriter();
		
		//List<ServerModel> servers = serverDAO.getList();
		List<ServerMergeModel> servers=serverMergeDAO.getAllServerMerge();
		
		String yesStr = TimeUtils.getYesterdayStr();
		// String todayStr= TimeUtils.getTodayStr();

		for (ServerMergeModel server : servers) {
			
			long seridlong = server.getId();
			int srvId = new Long(seridlong).intValue();
			
			if (type == 0) {
				//
				String strMsg = FilePathLog.queryFilePath(type, srvId, yesStr);
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

				String strMsg = FilePathLog.queryFilePath(type, srvId, yesStr);
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
		}
	}
	
	
	public void queryGoodsInfoString(String type) throws Throwable {

		Map<Date, Map<Integer, Integer>> goodsmap;
		Map<Integer, Integer> intmap;
		List<GoodsModel> goodsModellist;
		//LogsReaderWriter logsReaderWriter = new LogsReaderWriter();
		//判断类型
		int typeStr=0;
		//活动领取 activityId|playerId|awardIndex
		if(type.equals("behavior/get_{0}.log")){
			typeStr=1;
		}
		//引导 guideId|playerId|1/2
		if(type.equals("behavior/guide_{0}.log")){
			typeStr=2;
		}
		//首冲  level|playerId|amount
		if(type.equals("behavior/pay_{0}.log")){
			typeStr=3;
		}
		//任务 taskId|playerId|1
		if(type.equals("behavior/task_{0}.log")){
			typeStr=4;
		}
		
		//List<ServerModel> servers = serverDAO.getList();
		List<ServerMergeModel> servers=serverMergeDAO.getAllServerMerge();
		
		String yesStr = TimeUtils.getYesterdayStr();
		// String todayStr= TimeUtils.getTodayStr();

		for (ServerMergeModel server : servers) {
			
			long seridlong = server.getId();
			int srvId = new Long(seridlong).intValue();
			
				String strMsg = FilePathLog.queryFilePathString(type, srvId, yesStr);
				//System.out.println(strMsg);
				if (strMsg != null && strMsg != "") {
					goodsModellist = logsReaderWriter.readStringToList(strMsg,typeStr,srvId);

					if (goodsModellist.size() != 0) {
						//goodsModelDAO.insertOrUpdate(goodsModellist);
						//goodsModellist.clear();
						
						//批量插入
						for(int i=0; i<goodsModellist.size();){
						
						int j=(i+10000)>goodsModellist.size()?goodsModellist.size():(i+10000);
						List<GoodsModel> subgoodsModellist=goodsModellist.subList(i, j);
					
						goodsModelDAO.insertOrUpdate(subgoodsModellist);
					
						i=i+10000;
						}	
						goodsModellist.clear();
					}
				}

		}
	}

	// Date maxData= (goodsOutPutDAO.getMaxDate(srvId) != null) ?
	// goodsOutPutDAO.getMaxDate(srvId) : monthAgo;

	@SuppressWarnings("unused")
	public static String queryFilePath(int type, int srvId, String dayStr) {
		 String goods=null;
		 String strMsg = null;
		// 查找服务器
		try {
			
			// 生成文本路径
			String value = SystemLogClock.getParams(type);
			String fileRelPath = MessageFormat.format(value, dayStr);
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			ServerModel serverModel = serverComponent.find(srvId);

			// 连接服务器
			IClient client = serverModel.createClient();

			// 发送消息
			GetSystemLogCS.Builder cs = GetSystemLogCS.newBuilder();
			cs.setLogFileRelPath(fileRelPath);

			ProtocolWeb.GetSystemLogSC retMsg = client.call(Head.H14004, cs.build(), ProtocolWeb.GetSystemLogSC.class);
			strMsg = retMsg.getContent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(strMsg==null){
			 if(type == 0 ){ 
				 goods = "goodsoutput";
			 }
			 if(type == 1){
				 goods = "goodsconsume"; 
			 }
			 String errStr= goods+":: " + srvId+":: " + dayStr;
			 logger.error(errStr); 
		}
		
		return strMsg;
	}
	
	@SuppressWarnings("unused")
	public static String queryFilePathString(String type, int srvId, String dayStr) {
		 String goods=null;
		 String strMsg = null;
		 String fileRelPath=null;
		// 查找服务器
		try {
			
			// 生成文本路径
			 fileRelPath = MessageFormat.format(type, dayStr);
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			ServerModel serverModel = serverComponent.find(srvId);

			// 连接服务器
			IClient client = serverModel.createClient();

			// 发送消息
			GetSystemLogCS.Builder cs = GetSystemLogCS.newBuilder();
			cs.setLogFileRelPath(fileRelPath);

			ProtocolWeb.GetSystemLogSC retMsg = client.call(Head.H14004, cs.build(), ProtocolWeb.GetSystemLogSC.class);
			strMsg = retMsg.getContent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(strMsg==null){
	
			 String errStr=fileRelPath+":: " + srvId+":: " + dayStr;
			 logger.error(errStr); 
		}
		
		return strMsg;
	}
	
}
