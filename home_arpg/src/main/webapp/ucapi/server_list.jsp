<%--
  User: xiezuojie
  Date: 16/7/21 17:24

  获取所有区服列表
  POST
  描述: 发请post请求,获取游戏所有区服列表,按开服时间顺序排列,接口支持分页.
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

<%!
    public static class UCRequestParams {
        public int gameId; // 九游平台的游戏ID
        public int platform; // 九游平台标识: 2-安卓,3-IOS
        public int page; // 页码,默认1
        public int count; // 每页大小,默认20
    }

    public class UCResponseData {
        public int recordCount;
        public List<UCResponseServer> list; // json
    }

    public class UCResponseServer {
        private int srvId; // 区服ID int
        public String serverId; // 区服ID
        public String serverName; // 区服名称
        transient public long openTime;
    }
    public Comparator<UCResponseServer> comparator = new Comparator<UCResponseServer>() {
        @Override
        public int compare(UCResponseServer o1, UCResponseServer o2) {
            return o2.srvId - o1.srvId;
        }
    };
%>

<%
    out.clear();
    request.setCharacterEncoding("utf-8");
    UCRequest ucRequest = UCTool.parse(request, UCGiftAESKey);
    boolean verifyRs = ucRequest.verifySign(UCGiftCaller, UCGiftApiKey);
    if (!verifyRs) {
        UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_5000011, "");
        out.print(resp.toJsonString());
        return;
    }

    try {
        UCRequestParams params = JSON.parseObject(ucRequest.data.decryptedParams, UCRequestParams.class);
        if (UCGiftGameId != params.gameId) {
            UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_5000030, "");
            out.print(resp.toJsonString());
            return;
        }

        int count = 0;
        List<UCResponseServer> serverList = null;
        Map<Integer, Server> serverMap = ServerManager.getServerMapFromCache();
        if (serverMap != null) {
            count = serverMap.size();
            serverList = new ArrayList<>(serverMap.size());
            for (Server server : serverMap.values()) {
                UCResponseServer responseServer = new UCResponseServer();
                responseServer.srvId = server.getId();
                responseServer.serverId = String.valueOf(server.getId());
                responseServer.serverName = server.getName();
                responseServer.openTime = server.getOpen_time() != null ? server.getOpen_time().getTime() : 0L;
                serverList.add(responseServer);
            }
            serverList.sort(comparator);
        } else {
            serverList = new ArrayList<>();
        }

        int startIdx = (params.page - 1) * params.count;
        startIdx = startIdx >= 0 ? startIdx : 0;
        int pageSize = params.count;
        if (pageSize == 0) {
            pageSize = 20;
        }
        int endIdx = startIdx + pageSize;
        endIdx = endIdx <= count ? endIdx : count;

        UCResponseData responseData = new UCResponseData();
        responseData.recordCount = count;
        responseData.list = new ArrayList<>(serverList.subList(startIdx, endIdx));

//        System.out.println(JSON.toJSONString(responseData));
        UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_2000000, JSON.toJSONString(responseData), UCGiftAESKey);
        out.print(resp.toJsonString());
    } catch (Exception e) {
        e.printStackTrace();
        UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_5000000, "");
        out.print(resp.toJsonString());
    }
%>