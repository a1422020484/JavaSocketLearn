<%@page import="cn.saturn.web.controllers.award.dao.AwardManager"%>
<%@page import="cn.saturn.web.controllers.award.dao.AwardModel"%>
<%@page import="cn.saturn.web.gamedata.ItemMsg"%>
<%@page import="proto.ProtocolWeb.SendMailWSC"%>
<%@page import="cn.saturn.web.controllers.server.dao.IClient"%>
<%@page import="proto.ProtocolWeb.SendMailWCS"%>
<%@page import="cn.saturn.web.controllers.server.dao.ServerModel"%>
<%@page import="zyt.spring.component.ComponentManager"%>
<%@page import="cn.saturn.web.controllers.server.dao.ServerComponent"%>
<%@page import="java.util.ArrayList"%>
<%@page import="proto.Protocol"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
AwardManager awardManager = ComponentManager.getComponent(AwardManager.class);
List<AwardModel> models = awardManager.getAwardList();

List<ItemMsg> items = new ArrayList<ItemMsg>();
ItemMsg imsg = new ItemMsg();
imsg.setId(100000001);
imsg.setCount(88888);
items.add(imsg);

imsg = new ItemMsg();
imsg.setId(100000002);
imsg.setCount(188);
items.add(imsg);

imsg = new ItemMsg();
imsg.setId(100000026);
imsg.setCount(68);
items.add(imsg);

imsg = new ItemMsg();
imsg.setId(100000003);
imsg.setCount(20);
items.add(imsg);

imsg = new ItemMsg();
imsg.setId(169000034);
imsg.setCount(5);
items.add(imsg);

String msg = "亲爱的玩家，邮件为登陆奖励，祝您游戏愉快！";
for(AwardModel model : models){
	List<Protocol.ItemMsg> lists = new ArrayList<>();
	for(ItemMsg it:items){
		Protocol.ItemMsg itemMsg = Protocol.ItemMsg.newBuilder().setItemId(it.getId()).setItemCount(it.getCount()).build();
		lists.add(itemMsg);
	}
	try {
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		ServerModel serverModel = serverComponent.find(model.getSvrId());
		
		IClient client = serverModel.createClient();
		
		SendMailWCS.Builder msgb = SendMailWCS.newBuilder();
		msgb.setMsgStr(msg); // 邮件消息
		msgb.setRecvId(model.getPlayer_id()+"");
		msgb.addAllItemList(lists); // 物品ID

		// 发送并等待回馈
		SendMailWSC retMsg = client.call(11001, msgb.build(), SendMailWSC.class);
		if (retMsg != null) {
			out.print(model.getSvrId() +"  "+ model.getPlayer_id() +" "+items +" "+ " 发送成功");
			out.flush();
		} 
		} catch (Exception e) {
		e.printStackTrace();
		// 按UC需求,输出返回内容
		out.print(model.getSvrId() +"  "+ model.getPlayer_id() +" "+items +" "+ " 发送失败");
		out.flush();
		
	}
}
%>

