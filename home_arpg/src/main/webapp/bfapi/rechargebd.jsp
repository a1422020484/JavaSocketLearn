<%--
  User: xiezuojie
  Date: 16/7/21 17:24

  应用宝获取所有区服列表
  GET
  描述: 发请get请求,获取游戏所有区服列表,按开服时间顺序排列,接口支持分页.
--%>
<%@page import="java.util.function.DoublePredicate"%>
<%@page import="cn.saturn.web.code.login.domain.AccountManager"%>
<%@page import="cn.saturn.web.code.login.domain.Account"%>
<%@page import="java.util.Date"%>
<%@page import="cn.saturn.web.controllers.order.dao.OrderManager"%>
<%@page import="cn.saturn.web.controllers.order.dao.Order"%>
<%@page import="cn.saturn.web.client.H1802RespHandler"%>
<%@page import="proto.Protocol"%>
<%@page import="cn.saturn.web.controllers.yybapi.dao.OpRecord"%>
<%@page import="cn.saturn.web.client.CHead"%>
<%@page import="proto.ProtocolWeb.SendGMCmdWSC"%>
<%@page import="proto.ProtocolWeb.SendGMCmdWCS"%>
<%@page import="xzj.core.client.GameClient"%>
<%@page import="cn.saturn.web.code.bind.domain.AccountBindManager"%>
<%@page import="cn.saturn.web.code.bind.domain.AccountBind"%>
<%@ include file="define.jsp" %>
<%@ page import="cn.saturn.web.controllers.ucapi.UCRequest" %>
<%@ page import="cn.saturn.web.controllers.ucapi.UCResponse" %>
<%@ page import="cn.saturn.web.controllers.ucapi.UCTool" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.saturn.web.controllers.server.dao.ServerManager" %>
<%@ page import="cn.saturn.web.controllers.server.dao.Server" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Comparator" %>
<%@include file="request.jsp" %>
<%@include file="response.jsp" %>

<%
    out.clear();
    request.setCharacterEncoding("utf-8");
    BfRequestRechargeBd params = new BfRequestRechargeBd();
    try{
    	params.userId = request.getParameter("userId");
    	params.roleId = request.getParameter("roleId");
    	params.gameZoneId = request.getParameter("gameZoneId");
    	params.exInfo = request.getParameter("exInfo");
    	params.transactionId = request.getParameter("transactionId");
    	params.appId = Integer.parseInt(request.getParameter("appId"));
    	params.channelId = Integer.parseInt(request.getParameter("channelId"));
    	params.productId = Integer.parseInt(request.getParameter("productId"));
    	params.charging_channel = Integer.parseInt(request.getParameter("charging_channel"));
    	params.money = Double.parseDouble(request.getParameter("money"));
    	params.gameCoin = Integer.parseInt(request.getParameter("gameCoin"));
    	params.gameCoinCurrency = request.getParameter("gameCoinCurrency");
    	params.sign = request.getParameter("sign");
    	params.appKey = appKeyMap.get(params.appId);
    	params.appSecret = appSecretMap.get(params.appId);
    }catch(Exception e){
    	//e.printStackTrace();
    	BfResponseData responseData = new BfResponseData(ERROR_CODE_PARAMS);
        BfResponse resp = new BfResponse(responseData);
        out.print(resp.toJson());
        return;
    }

    try {
        
        if(!params.checkSign()){
        	BfResponseData responseData = new BfResponseData(ERROR_CODE_SIG);
        	BfResponse resp = new BfResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        
        if(!params.checkDuplication()){
        	BfResponseData responseData = new BfResponseData(ERROR_CODE_DUPLICATION);
        	BfResponse resp = new BfResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }

        if(params.roleId == null){
        	BfResponseData responseData = new BfResponseData(ERROR_CODE_ROLEID);
        	BfResponse resp = new BfResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        Account account = AccountManager.getAccountByThirdUserId(params.userId);
        if(account == null){
        	BfResponseData responseData = new BfResponseData(ERROR_CODE_NOROLE);
        	BfResponse resp = new BfResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        long accountId = account.getId();
        int svrId = Integer.parseInt(params.gameZoneId);
        AccountBind bind = AccountBindManager.getAccountBind(accountId, svrId);
        if(bind == null){
        	BfResponseData responseData = new BfResponseData(ERROR_CODE_NOROLE);
        	BfResponse resp = new BfResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        Server server = ServerManager.getServer(svrId);
        if(server != null){
        	if(server.getState() == Server.SRV_STATE_HIDE || server.getState() == Server.SRV_STATE_CLOSE){
        		BfResponseData responseData = new BfResponseData(ERROR_CODE_SERVERDOWN);
        		BfResponse resp = new BfResponse(responseData);
                out.print(resp.toJson());
                out.flush();
                return;
        	}
        	
        	GameClient client = new GameClient(server.getUrl());
        	client.addHandler(new H1802RespHandler());
        	int playerId = bind.getPlayerId();
        	Protocol.ChargeCS.Builder ccs = Protocol.ChargeCS.newBuilder();
        	ccs.setCpOrderId(0L);
			ccs.setGoodsId(0);
			ccs.setGoodsPrice(0);
			ccs.setAmount(new Double(params.money).floatValue());
			ccs.setAccountId(playerId);
			ccs.setPlatform("system");
        	try{
        		client.send(CHead.H1802, ccs.build());
        	}catch(Exception e){
        		e.printStackTrace();
        		BfResponseData responseData = new BfResponseData(ERROR_CODE_EXCEPTION);
        		BfResponse resp = new BfResponse(responseData);
                out.print(resp.toJson());
                out.flush();
                return;
        	}
        	
        	long timestamp = System.currentTimeMillis();
        	OpRecord opRecord = new OpRecord(timestamp, params.appId+"", params.gameZoneId, params.userId, params.gameZoneId, params.transactionId,
        			params.roleId, params.transactionId, new Double(params.money).floatValue()+"", new Double(10*params.money).intValue());
        	OpRecordManager.insertRecord(opRecord);
        	
        	Date date = new Date();
        	Order order = new Order(OrderManager.createOrderId(), date, date, svrId, bind.getPlayerId(), account.getAccount()+"",
        			bind.getPlayerName(), account.getPlatform(), new Double(params.money).floatValue(), params.transactionId, params.transactionId,
        			"", 0, "");
        	OrderManager.insert(order);
        }
        out.print("ok");
    } catch (Exception e) {
        e.printStackTrace();
        BfResponseData responseData = new BfResponseData(ERROR_CODE_EXCEPTION);
        BfResponse resp = new BfResponse(responseData);
        out.print(resp.toJson());
    }
%>