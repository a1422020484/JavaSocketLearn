<%--
  User: xiezuojie
  Date: 16/7/21 17:24

  应用宝获取所有区服列表
  GET
  描述: 发请get请求,获取游戏所有区服列表,按开服时间顺序排列,接口支持分页.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.Calendar"%>
<%@page import="cn.saturn.web.controllers.gift.dao.GiftRecord"%>
<%@page import="cn.saturn.web.controllers.gift.dao.GiftManager"%>
<%@page import="cn.saturn.web.controllers.gift.dao.GiftCfg"%>
<%@page import="proto.ProtocolWeb"%>
<%@page import="cn.saturn.web.client.H19021RespHandler"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
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
    RequestGift params = new RequestGift();
    try{
    	params.cpid = request.getParameter("cpid")==null?"":request.getParameter("cpid");
    	params.gameid = request.getParameter("gameid")==null?"":request.getParameter("gameid");
    	params.serverid = Integer.parseInt(request.getParameter("serverid"));
    	params.servername = request.getParameter("servername")==null?"":request.getParameter("servername");
    	System.out.println("servername->"+params.servername);
    	params.rolename = request.getParameter("rolename")==null?"":request.getParameter("rolename");
    	System.out.println("rolename->"+params.rolename);
    	params.roleid = request.getParameter("roleid")==null?"":request.getParameter("roleid");
    	params.uid = request.getParameter("uid")==null?"":request.getParameter("uid");
    	params.giftcode = request.getParameter("giftcode")==null?"":request.getParameter("giftcode");
    	params.ts = request.getParameter("ts")==null?"":request.getParameter("ts");
    	params.sign = request.getParameter("sign");
    	params.appKey = appKey;
    }catch(Exception e){
    	//e.printStackTrace();
    	Result result = new Result(ERROR_CODE_PARAMS);
    	ResponseData responseData = new ResponseData(result);
        ResponseBase resp = new ResponseBase(responseData);
        out.print(resp.toJson());
        return;
    }

    try {
        
        if(!params.checkSign()){
        	Result result = new Result(ERROR_CODE_SIG);
        	ResponseData responseData = new ResponseData(result);
        	ResponseBase resp = new ResponseBase(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        String uniqueKey = params.serverid + "-" + params.uid + "-" + params.giftcode + "-" + year + dayOfYear;
        
        if(params.checkDuplication(uniqueKey)){
        	Result result = new Result(ERROR_CODE_DUPLICATION);
        	ResponseData responseData = new ResponseData(result);
        	ResponseBase resp = new ResponseBase(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }

        if(StringUtils.isBlank(params.uid)){
        	Result result = new Result(ERROR_CODE_ROLEID);
        	ResponseData responseData = new ResponseData(result);
        	ResponseBase resp = new ResponseBase(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        Account account = AccountManager.getAccount(params.uid, "zhuodong");
        if(account == null)
        	account = AccountManager.getAccount(params.uid, "Saturn");
        
        if(account == null){
        	Result result = new Result(ERROR_CODE_NOROLE);
        	ResponseData responseData = new ResponseData(result);
        	ResponseBase resp = new ResponseBase(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        long accountId = account.getId();
        int svrId = params.serverid;
        AccountBind bind = AccountBindManager.getAccountBind(accountId, svrId);
        if(bind == null){
        	Result result = new Result(ERROR_CODE_NOROLE);
        	ResponseData responseData = new ResponseData(result);
        	ResponseBase resp = new ResponseBase(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        GiftCfg gift = GiftManager.getGift(params.giftcode);
        if(gift == null){
        	Result result = new Result(ERROR_CODE_NOGIFT);
    		ResponseData responseData = new ResponseData(result);
        	ResponseBase resp = new ResponseBase(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        Server server = ServerManager.getServer(svrId);
        if(server != null){
        	if(server.getState() == Server.SRV_STATE_HIDE || server.getState() == Server.SRV_STATE_CLOSE){
        		Result result = new Result(ERROR_CODE_SERVERDOWN);
        		ResponseData responseData = new ResponseData(result);
            	ResponseBase resp = new ResponseBase(responseData);
                out.print(resp.toJson());
                out.flush();
                return;
        	}
        	
        	int playerId = bind.getPlayerId();
            GameClient client = new GameClient(server.getUrl());
            client.addHandler(new H19021RespHandler());
            ProtocolWeb.UCGiftWCS.Builder wcs = ProtocolWeb.UCGiftWCS.newBuilder();
            wcs.setUcAccountId(bind.getAccountId()+"");
            wcs.setPlayerId(playerId);
            wcs.setGiftId(params.giftcode);
            wcs.setContent(gift.getContent());
            client.send(CHead.H19021, wcs.build());

            try{
            	GiftRecord giftRecord = new GiftRecord();
                giftRecord.setGiftId(params.giftcode);
                giftRecord.setServerId(svrId);
                giftRecord.setPlayerId(playerId);
                giftRecord.setAccountId(bind.getAccountId()+"");
                giftRecord.setReceiveTime(new Date());
                giftRecord.setUniqueKey(uniqueKey);
                GiftManager.insertGiftRecord(giftRecord);
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
        Result result = new Result(ERROR_CODE_SUCCESS);
        ResponseData responseData = new ResponseData(result);
        ResponseBase resp = new ResponseBase(responseData);
        out.print(resp.toJson());
        out.flush();
    } catch (Exception e) {
        e.printStackTrace();
        Result result = new Result(ERROR_CODE_EXCEPTION);
        ResponseData responseData = new ResponseData(result);
        ResponseBase resp = new ResponseBase(responseData);
        out.print(resp.toJson());
        out.flush();
    }
%>