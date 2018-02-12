<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="cn.saturn.web.controllers.server.dao.ServerModel,zyt.spring.component.ComponentManager,cn.saturn.web.controllers.server.dao.ServerComponent,cn.saturn.web.controllers.server.dao.IClient,cn.saturn.web.utils.*,xzj.core.util.*,org.slf4j.*,com.alibaba.fastjson.*,org.apache.commons.io.*,java.util.*,org.apache.commons.lang.*,proto.ProtocolWeb.*,cn.saturn.web.gamedata.*,proto.Protocol"
	pageEncoding="UTF-8"%>

<%
	out.clear();
	request.setCharacterEncoding("utf-8");

	final Logger paylog = LoggerFactory.getLogger("pay");
	String roles = request.getParameter("roles");
	int srv = Integer.parseInt(request.getParameter("srv"));
	String item = request.getParameter("item");
	String msg = request.getParameter("msg");
	List<ItemMsg> items = ItemMsg.decode(item);
	
	List<Protocol.ItemMsg> lists = new ArrayList<>();
	for(ItemMsg it:items){
		Protocol.ItemMsg itemMsg = Protocol.ItemMsg.newBuilder().setItemId(it.getId()).setItemCount(it.getCount()).build();
		lists.add(itemMsg);
	}
	try {
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		ServerModel serverModel = serverComponent.find(srv);
		
		IClient client = serverModel.createClient();
		
		SendMailWCS.Builder msgb = SendMailWCS.newBuilder();
		msgb.setMsgStr(msg); // 邮件消息
		msgb.setRecvId(roles);
		msgb.addAllItemList(lists); // 物品ID

		// 发送并等待回馈
		SendMailWSC retMsg = client.call(11001, msgb.build(), SendMailWSC.class);
		if (retMsg == null) {
			out.print(srv +"  "+ roles +" "+items +" "+ " 发送成功");
		}
		} catch (Exception e) {
		e.printStackTrace();
		// 按UC需求,输出返回内容
		out.print(srv +"  "+ roles +" "+items +" "+ " 发送失败");
	}
%>