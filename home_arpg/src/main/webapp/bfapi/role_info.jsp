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
    BfRequestRoleInfo params = new BfRequestRoleInfo();   
    try{
    	params.userId = request.getParameter("userId");
    	params.roleId = request.getParameter("roleId");
    	params.gameZoneId = request.getParameter("gameZoneId");
    	params.exInfo = request.getParameter("exInfo");
    }catch(Exception e){
    	//e.printStackTrace();
    	BfResponseData responseData = new BfResponseData(ERROR_CODE_PARAMS);
    	BfResponse resp = new BfResponse(responseData);
        out.print(resp.toJson());
        out.flush();
        return;
    }

    try { 	   
        Account account = AccountManager.getAccountByThirdUserId(params.userId);
        if(account == null){
        	BfResponseData responseData = new BfResponseData(ERROR_CODE_NOROLE);
        	BfResponse resp = new BfResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }
        
        List<AccountBind> bindList = AccountBindManager.getAccountBindList(account.getId());
        List<RoleInfo> roleList = new ArrayList<>();
        if (bindList != null) {
        	Server server;
            for (AccountBind bind : bindList) {
            	if(params.gameZoneId == null || !params.gameZoneId.trim().equals(bind.getSrvId()+""))
            		continue;
            	server = ServerManager.getServer(Integer.parseInt(params.gameZoneId));
            	RoleInfo roleInfo = new RoleInfo();
                roleInfo.userId = params.userId;
                roleInfo.roleId = bind.getPlayerId()+"";
                roleInfo.roleName = bind.getPlayerName();
                roleInfo.gameZoneId = params.gameZoneId;
                roleInfo.gameZoneName = server == null?"未知":server.getName();
                roleInfo.level = bind.getPlayerLv();
                roleList.add(roleInfo);
            }
        }
        
        if(roleList.size() == 0){
        	BfResponseData responseData = new BfResponseData(ERROR_CODE_NOROLE);
        	BfResponse resp = new BfResponse(responseData);
            out.print(resp.toJson());
            out.flush();
            return;
        }

        BfResponseRoleInfo responseData = new BfResponseRoleInfo();
        responseData.status = ERROR_CODE_SUCCESS;
        responseData.msg = ERROR_STRS.get(ERROR_CODE_SUCCESS);
        responseData.roleInfo = roleList.get(0);

//        System.out.println(JSON.toJSONString(responseData));
        BfResponse resp = new BfResponse(responseData);
        out.print(resp.toJson());
    } catch (Exception e) {
        e.printStackTrace();
        BfResponseData responseData = new BfResponseData(ERROR_CODE_EXCEPTION);
        BfResponse resp = new BfResponse(responseData);
        out.print(resp.toJson());
    }
%>