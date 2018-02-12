package cn.saturn.web.controllers.poke;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.poke.dao.AccountModel;
import cn.saturn.web.controllers.poke.dao.MailAccount;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.gamedata.ItemMsg;
import cn.saturn.web.utils.Config;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.ReadFilePath;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.Protocol;
import proto.ProtocolWeb.SendMailWCS;
import proto.ProtocolWeb.SendMailWSC;
import zyt.spring.component.ComponentManager;

@Path("sendMail")
public class SendMailController {
	
	String text = "System Compensation";
	protected static final Logger logger = LoggerFactory.getLogger(LogType.allMailSend);
	
	private static String mailtext = Config.val("mailtext");
	
	@SuppressWarnings("unchecked")
	@Get("toSend")
	public String toSendMail(Invocation inv) throws Throwable {
		
		System.out.println("start send mail");
		
		String textStr=ReadFilePath.readTxtFile(mailtext);
		//Map<String,String> maps =(Map<String, String>) JSON.parse(textStr);
		Map<String, JSONObject> maps = JSONObject.parseObject(textStr,Map.class); 
		
		Set<String> srvIds=maps.keySet();
		for(String  srvId:srvIds){
			JSONObject  receMapStr=(JSONObject)maps.get(srvId);
			//解析playerId
			Map<String,JSONObject>  receMaps=(Map<String,JSONObject>)JSON.parse(receMapStr.toJSONString());
			Set<String>  playerIds=receMaps.keySet();
			for(String playerId:playerIds){
				JSONObject giftsStr=receMaps.get(playerId);
				 //解析礼物
				Map<String,Integer>  giftMaps=(Map<String,Integer>)JSON.parse(giftsStr.toJSONString());
				Set<String> giftIds=giftMaps.keySet();
				
				List<Protocol.ItemMsg> itemList = new ArrayList<>();
				SendMailWSC retMsg = null;
				
				for(String giftIdstr:giftIds){
					
					int giftId=Integer.valueOf(giftIdstr);
					 int idd=giftMaps.get(giftIdstr);
					int  conts=(giftId==100000001)?giftMaps.get(giftIdstr)*10:giftMaps.get(giftIdstr);
					
					Protocol.ItemMsg itemMsg = Protocol.ItemMsg.newBuilder().setItemId(giftId).setItemCount(conts)
							.build();
					itemList.add(itemMsg);
					
				}
				
				
				try {
					// 查找服务器
					ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
					ServerModel serverModel = serverComponent.find(Integer.valueOf(srvId));
					// 连接服务器
					IClient client = serverModel.createClient();
					
					SendMailWCS.Builder msgb = SendMailWCS.newBuilder();
					msgb.setMsgStr(text); // 邮件消息
					msgb.setRecvId(Integer.valueOf(playerId) + "");

					msgb.addAllItemList(itemList); // 物品ID

					// 发送并等待回馈
					retMsg = client.call(Head.H11001, msgb.build(), SendMailWSC.class);
					if (retMsg != null) {
						logger.info( String.valueOf(srvId)+"-"+String.valueOf(playerId));
					}

				} catch (Exception e) {

				}
				
				
			}
			
		}
		System.out.println("end send mail");
       return  "@"+"send ok"; 
	}
}
