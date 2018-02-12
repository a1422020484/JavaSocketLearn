<%--
  User: xiezuojie
  Date: 16/7/21 17:24

  应用宝获取指定服的玩家信息
  GET
  描述: 发请get请求,获取指定服的所有玩家信息
--%>
<%@page import="cn.saturn.web.code.bind.domain.AccountBindManager"%>
<%@page import="cn.saturn.web.code.bind.domain.AccountBind"%>
<%@page import="cn.saturn.web.code.login.domain.AccountManager"%>
<%@page import="cn.saturn.web.code.login.domain.Account"%>
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
    YYBRequestRoleInfo params = new YYBRequestRoleInfo();   
    try{
    	params.timestamp = Long.parseLong(request.getParameter("timestamp"));
    	params.appid = request.getParameter("appid");
    	params.area = request.getParameter("area");
    	params.sig = request.getParameter("sig");
    	params.openid = request.getParameter("openid");
    	params.partition = request.getParameter("partition");
    	params.pkey = request.getParameter("pkey");
    	params.appKey = appKeys.get(params.appid);
    }catch(Exception e){
    	//e.printStackTrace();
    	YYBResponseData responseData = new YYBResponseData(ERROR_CODE_PARAMS);
    	YYBResponse resp = new YYBResponse(responseData);
        out.print(resp.toJson());
        out.flush();
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
        
        Account account = AccountManager.getAccountByThirdUserId(params.openid);
        if(account == null){
        	YYBResponseData responseData = new YYBResponseData(ERROR_CODE_NOROLE);
        	YYBResponse resp = new YYBResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        List<AccountBind> bindList = AccountBindManager.getAccountBindList(account.getId());
        List<RoleInfo> roleList = new ArrayList<>();
        if (bindList != null) {
            for (AccountBind bind : bindList) {
            	if(params.partition == null || !params.partition.trim().equals(bind.getSrvId()+""))
            		continue;
            	RoleInfo roleInfo = new RoleInfo();
                roleInfo.roleid = bind.getAccountId()+"_"+bind.getSrvId();
                roleInfo.rolename = bind.getPlayerName();
                roleList.add(roleInfo);
            }
        }
        
        if(roleList.size() == 0){
        	YYBResponseData responseData = new YYBResponseData(ERROR_CODE_NOROLE);
        	YYBResponse resp = new YYBResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }

        YYBResponseRoleInfo responseData = new YYBResponseRoleInfo();
        responseData.ret = ERROR_CODE_SUCCESS;
        responseData.msg = ERROR_STRS.get(ERROR_CODE_SUCCESS);
        responseData.list = roleList;

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