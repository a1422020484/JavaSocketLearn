<%--
  User: xiezuojie
  Date: 16/7/21 17:24

  应用宝获取所有区服列表
  GET
  描述: 发请get请求,获取游戏所有区服列表,按开服时间顺序排列,接口支持分页.
--%>
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
    YYBRequestGameOp params = new YYBRequestGameOp();
    try{
    	params.timestamp = Long.parseLong(request.getParameter("timestamp"));
    	params.appid = request.getParameter("appid");
    	params.area = request.getParameter("area");
    	params.sig = request.getParameter("sig");
    	params.openid = request.getParameter("openid");
    	params.partition = request.getParameter("partition");
    	params.pkey = request.getParameter("pkey");
    	params.billno = request.getParameter("billno");
    	params.roleid = request.getParameter("roleid");
    	params.midas_billno = request.getParameter("midas_billno");
    	params.money = request.getParameter("money");
    	params.gold = request.getParameter("gold");
    	params.appKey = appKeys.get(params.appid);
    }catch(Exception e){
    	//e.printStackTrace();
    	YYBResponseData responseData = new YYBResponseData(ERROR_CODE_PARAMS);
        YYBResponse resp = new YYBResponse(responseData);
        out.print(resp.toJson());
        return;
    }

    try {
        if(!params.checkTimestamp()){
        	YYBResponseData responseData = new YYBResponseData(ERROR_CODE_TIMESTAMP_EXPIRE);
        	YYBResponse resp = new YYBResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        if(!params.checkSig(request)){
        	YYBResponseData responseData = new YYBResponseData(ERROR_CODE_SIG);
        	YYBResponse resp = new YYBResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        if(!params.checkPkey()){
        	YYBResponseData responseData = new YYBResponseData(ERROR_CODE_PKEY);
        	YYBResponse resp = new YYBResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        if(!params.checkDuplication()){
        	YYBResponseData responseData = new YYBResponseData(ERROR_CODE_DUPLICATION);
        	YYBResponse resp = new YYBResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }

        if(params.roleid == null){
        	YYBResponseData responseData = new YYBResponseData(ERROR_CODE_ROLEID);
        	YYBResponse resp = new YYBResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        String[] strs = params.roleid.split("_");
        if(strs == null || strs.length<2){
        	YYBResponseData responseData = new YYBResponseData(ERROR_CODE_ROLEID);
        	YYBResponse resp = new YYBResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        long accountId = Long.parseLong(strs[0]);
        int svrId = Integer.parseInt(strs[1]);
        AccountBind bind = AccountBindManager.getAccountBind(accountId, svrId);
        if(bind == null){
        	YYBResponseData responseData = new YYBResponseData(ERROR_CODE_NOROLE);
        	YYBResponse resp = new YYBResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        Account account = AccountManager.getAccountByThirdUserId(params.openid);
        if(account == null){
        	YYBResponseData responseData = new YYBResponseData(ERROR_CODE_NOROLE);
        	YYBResponse resp = new YYBResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        Server server = ServerManager.getServer(svrId);
        if(server != null){
        	if(server.getState() == Server.SRV_STATE_HIDE || server.getState() == Server.SRV_STATE_CLOSE){
        		YYBResponseData responseData = new YYBResponseData(ERROR_CODE_SERVERDOWN);
            	YYBResponse resp = new YYBResponse(responseData);
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
			ccs.setAmount(Integer.parseInt(params.money));
			ccs.setAccountId(playerId);
			ccs.setPlatform("system");
        	try{
        		client.send(CHead.H1802, ccs.build());
        	}catch(Exception e){
        		e.printStackTrace();
        		YYBResponseData responseData = new YYBResponseData(ERROR_CODE_EXCEPTION);
            	YYBResponse resp = new YYBResponse(responseData);
                out.print(resp.toJson());
                out.flush();
                return;
        	}
        	
        	OpRecord opRecord = new OpRecord(params.timestamp, params.appid, params.area, params.openid, params.partition, params.billno,
        			params.roleid, params.midas_billno, params.money, Integer.parseInt(params.gold));
        	OpRecordManager.insertRecord(opRecord);
        	
        	Date date = new Date();
        	Order order = new Order(OrderManager.createOrderId(), date, date, svrId, bind.getPlayerId(), account.getAccount()+"",
        			bind.getPlayerName(), account.getPlatform(), Float.parseFloat(params.money), params.billno, params.billno,
        			"", 0, "");
        	OrderManager.insert(order);
        }

        YYBResponseGameOp responseData = new YYBResponseGameOp();
        responseData.ret = ERROR_CODE_SUCCESS;
        responseData.msg = ERROR_STRS.get(ERROR_CODE_OPSUCCESS);

//        System.out.println(JSON.toJSONString(responseData));
        YYBResponse resp = new YYBResponse(responseData);
        out.print(resp.toJson());
    } catch (Exception e) {
        e.printStackTrace();
        YYBResponseData responseData = new YYBResponseData(ERROR_CODE_EXCEPTION);
        YYBResponse resp = new YYBResponse(responseData);
        out.print(resp.toJson());
    }
%>