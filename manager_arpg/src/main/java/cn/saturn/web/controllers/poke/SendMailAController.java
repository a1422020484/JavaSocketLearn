package cn.saturn.web.controllers.poke;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.poke.dao.MailAcountDAO;
import cn.saturn.web.controllers.poke.dao.SendMailA;
import cn.saturn.web.controllers.poke.dao.SendMailADAO;
import cn.saturn.web.controllers.poke.dao.TempPreset;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.Config;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.ReadFilePath;
import cn.saturn.web.utils.ReadText;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import proto.Protocol;
import proto.ProtocolWeb.SendMailWCS;
import proto.ProtocolWeb.SendMailWSC;
import zyt.spring.component.ComponentManager;

@Path("sendMailA")
public class SendMailAController {
	
	@Resource
	SendMailADAO sendMailADAO;
	
	String text = "Dear tamer, please kindly check the missing Crystal Consumption Rankings rewards. Thank you!";
	protected static final Logger logger = LoggerFactory.getLogger(LogType.allMailSend);
	
	private static String mailtext = Config.val("mailtext");
	
	@SuppressWarnings("unchecked")
	@Get("toSend")
	public String toSendMail(Invocation inv) throws Throwable {
		
		System.out.println("start send mail");
		
		String textStr=ReadFilePath.readTxtFile(mailtext);
		
		List<SendMailA> sendMailAList =ReadText.readLine(textStr);
		List<TempPreset> tempPresetList= sendMailADAO.getTempPreset();
		
		Map<Integer,TempPreset> tempPresetMap= new HashMap<Integer,TempPreset>();
		
		Collections.sort(tempPresetList, new Comparator<TempPreset>() {  
			  
            @Override  
            public int compare(TempPreset o1, TempPreset o2) {  
                int i = o1.getPresetid() - o2.getPresetid();  
                return i;  
            }  
        });  
		 
		for(SendMailA  sendMailA:sendMailAList){
			
			int serverId=sendMailA.getSrvId();
			 int playerId= sendMailA.getPlayerId();
			int sId=sendMailA.getsId();
			
			
			TempPreset tempPreset=null;
			
			for(TempPreset  tp:tempPresetList){
				if(tp.getPresetid()>=sId){
					tempPreset=tp;
					break;
				}
			}
			
			List<Protocol.ItemMsg> itemList = new ArrayList<Protocol.ItemMsg>();
			SendMailWSC retMsg = null;
			Protocol.ItemMsg itemMsg1 = Protocol.ItemMsg.newBuilder().setItemId(tempPreset.getPreset1()).setItemCount(tempPreset.getNum1()).build();
			itemList.add(itemMsg1);
				
			Protocol.ItemMsg itemMsg2 = Protocol.ItemMsg.newBuilder().setItemId(tempPreset.getPreset2()).setItemCount(tempPreset.getNum2()).build();
			itemList.add(itemMsg2);	
			
			Protocol.ItemMsg itemMsg3 = Protocol.ItemMsg.newBuilder().setItemId(tempPreset.getPreset3()).setItemCount(tempPreset.getNum3()).build();
			itemList.add(itemMsg3);	
			
			Protocol.ItemMsg itemMsg4 = Protocol.ItemMsg.newBuilder().setItemId(tempPreset.getPreset4()).setItemCount(tempPreset.getNum4()).build();
			itemList.add(itemMsg4);	
			
			try {
				// 查找服务器
				ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
				ServerModel serverModel = serverComponent.find(Integer.valueOf(serverId));
				// 连接服务器
				IClient client = serverModel.createClient();
				
				SendMailWCS.Builder msgb = SendMailWCS.newBuilder();
				msgb.setMsgStr(text); // 邮件消息
				msgb.setRecvId(Integer.valueOf(playerId) + "");

				msgb.addAllItemList(itemList); // 物品ID

				// 发送并等待回馈
				retMsg = client.call(Head.H11001, msgb.build(), SendMailWSC.class);
				if (retMsg != null) {
					logger.info("服务器ID："+serverId+"-"+"玩家ID:"+playerId+"-"+"Sid::"+sId+"礼物档位:"+tempPreset.getPresetid()+"==="+tempPreset.toString());
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("sendfail服务器ID："+serverId+"-"+"玩家ID:"+playerId+"-"+"Sid::"+sId+"礼物档位:"+tempPreset.getPresetid()+"==="+tempPreset.toString());
			}
			
			
		}
		System.out.println("end send mail");
		return  "@"+"send ok";
	}
}
