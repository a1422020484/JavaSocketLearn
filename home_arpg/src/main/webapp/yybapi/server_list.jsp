<%--
  User: xiezuojie
  Date: 16/7/21 17:24

  应用宝获取所有区服列表
  GET
  描述: 发请get请求,获取游戏所有区服列表,按开服时间顺序排列,接口支持分页.
--%>
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
<%!
    public Comparator<YYBResponseServer> comparator = new Comparator<YYBResponseServer>() {
        @Override
        public int compare(YYBResponseServer o1, YYBResponseServer o2) {
            return Integer.parseInt(o2.id) - Integer.parseInt(o1.id);
        }
    };
%>

<%
    out.clear();
    request.setCharacterEncoding("utf-8");
    YYBRequestSvrList params = new YYBRequestSvrList();
    try{
    	params.timestamp = Long.parseLong(request.getParameter("timestamp"));
    	params.appid = request.getParameter("appid");
    	params.area = request.getParameter("area");
    	params.sig = request.getParameter("sig");
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

        List<YYBResponseServer> serverList = null;
        Map<Integer, Server> serverMap = ServerManager.getServerMapFromCache();
        if (serverMap != null) {
            serverList = new ArrayList<>(serverMap.size());
            for (Server server : serverMap.values()) {
            	YYBResponseServer responseServer = new YYBResponseServer();
                responseServer.id = String.valueOf(server.getId());
                //responseServer.payid = String.valueOf(server.getId());
                responseServer.payid = "1105494480";
                responseServer.name = server.getName();
                responseServer.type = 3;
                serverList.add(responseServer);
            }
            serverList.sort(comparator);
        } else {
            serverList = new ArrayList<>();
        }

        YYBResponseSvrList responseData = new YYBResponseSvrList();
        responseData.ret = ERROR_CODE_SUCCESS;
        responseData.msg = ERROR_STRS.get(ERROR_CODE_SUCCESS);
        responseData.list = serverList;

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