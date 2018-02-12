<%--
  User: xiezuojie
  Date: 16/7/21 11:57

  获取用户创建的角色及区服信息
  POST
  描述: 发请post请求,通过九游SDK的accountId获取游戏的区服和角色信息.游戏区服角色信息必须是实时信息,并且按照角色的登录时间倒序排序.
--%>
<%@ include file="define.jsp" %>
<%@ page import="cn.saturn.web.code.bind.domain.AccountBind" %>
<%@ page import="cn.saturn.web.code.bind.domain.AccountBindManager" %>
<%@ page import="cn.saturn.web.code.login.domain.Account" %>
<%@ page import="cn.saturn.web.code.login.domain.AccountManager" %>
<%@ page import="cn.saturn.web.controllers.server.dao.Server" %>
<%@ page import="cn.saturn.web.controllers.server.dao.ServerManager" %>
<%@ page import="cn.saturn.web.controllers.ucapi.UCRequest" %>
<%@ page import="cn.saturn.web.controllers.ucapi.UCResponse" %>
<%@ page import="cn.saturn.web.controllers.ucapi.UCTool" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Comparator" %>

<%!
    public static class UCRequestParams {
        public String accountId; // 游戏客户端登录后从SDK取得
        public int gameId; // 九游平台的游戏ID
        public int platform; // 九游平台标识: 2-安卓,3-IOS
    }

    public class UCResponseData {
        public String accountId;
        public List<UCResponseRoleInfo> roleInfos; // json
    }

    public class UCResponseRoleInfo {
        public String serverId; // 区服ID
        public String serverName; // 区服名称
        public String roleId; // 角色ID
        public String roleName; // 角色名称
        public String roleLevel; // 角色等级
        transient public long createTime; // 创建时间
    }
    public Comparator<UCResponseRoleInfo> comparator = new Comparator<UCResponseRoleInfo>() {
        @Override
        public int compare(UCResponseRoleInfo o1, UCResponseRoleInfo o2) {
            return (int) (o2.createTime - o1.createTime);
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

        //Account account = AccountManager.getAccountByThirdUserId(params.accountId);
        Account account = AccountManager.getAccount(params.accountId,LoginPlatform);
        if (account == null) {
            // 没有此帐号
            UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_5000031, "");
            out.print(resp.toJsonString());
            return;
        }

        Map<Integer, Server> serverMap = ServerManager.getServerMapFromCache();
        if (serverMap == null) {
            // 读不取服务器列表,返回错误
            UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_5000000, "");
            out.print(resp.toJsonString());
            return;
        }

        UCResponseData responseData = new UCResponseData();
        responseData.accountId = params.accountId;
        responseData.roleInfos = new ArrayList<>();
        List<AccountBind> bindList = AccountBindManager.getAccountBindList(account.getId());
        if (bindList != null) {
            for (AccountBind bind : bindList) {
                Server server = serverMap.get(bind.getSrvId());
                if (server == null) {
                    continue; // 没有此服务器??
                }

                UCResponseRoleInfo roleInfo = new UCResponseRoleInfo();
                roleInfo.serverId = String.valueOf(bind.getSrvId());
                roleInfo.serverName = server.getName();
                roleInfo.roleId = String.valueOf(bind.getPlayerId());
                roleInfo.roleName = bind.getPlayerName();
                roleInfo.roleLevel = String.valueOf(bind.getPlayerLv());
                roleInfo.createTime = bind.getCreateTime() != null ? bind.getCreateTime().getTime() : 0L;
                responseData.roleInfos.add(roleInfo);
            }
            responseData.roleInfos.sort(comparator);
        }

        System.out.println(JSON.toJSONString(responseData));
        // success
        UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_2000000, JSON.toJSONString(responseData), UCGiftAESKey);
        out.print(resp.toJsonString());

    } catch (Exception e) {
        e.printStackTrace();
        UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_5000000, "");
        out.print(resp.toJsonString());
        return;
    }
%>