<%--
  User: xiezuojie
  Date: 16/7/25 16:24

  礼包发放接口
  POST
  用户在九游平台提交领号信息后,由发号服务器调用CP游戏服务提供的礼包发放接口,对用户发放礼包.
  注意: 接口必须做好业务约束,serverId+roleId+kaId之间做好约束,防止调用方在超时或连接失败时重试,造成礼包重复发放.
--%>
<%@ include file="define.jsp" %>
<%@ page import="cn.saturn.web.code.login.domain.Account" %>
<%@ page import="cn.saturn.web.code.login.domain.AccountManager" %>
<%@ page import="cn.saturn.web.controllers.server.dao.Server" %>
<%@ page import="cn.saturn.web.controllers.server.dao.ServerManager" %>
<%@ page import="cn.saturn.web.controllers.ucapi.UCRequest" %>
<%@ page import="cn.saturn.web.controllers.ucapi.UCResponse" %>
<%@ page import="cn.saturn.web.controllers.ucapi.UCTool" %>
<%@ page import="cn.saturn.web.controllers.ucapi.gift.UCGiftManager" %>
<%@ page import="cn.saturn.web.controllers.ucapi.gift.UCGiftRecord" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="proto.ProtocolWeb" %>
<%@ page import="xzj.core.client.GameClient" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="cn.saturn.web.controllers.ucapi.gift.UCGift" %>
<%@ page import="cn.saturn.web.client.CHead" %>
<%@ page import="cn.saturn.web.client.H19021RespHandler" %>

<%!
    public static class UCRequestParams {
        public String accountId; // 九游SDK用户的标识,游戏客户端登录后从SDK取得
        public int gameId; // 九游定义的游戏id
        public String kaId; // 由CP提供的礼包id
        public String serverId; // 游戏区服id
        public String roleId; // 游戏角色id
    }

    public class UCResponseData {
        public String result; // 操作结果,true/false
    }
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

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        String uniqueKey = params.serverId + "-" + params.roleId + "-" + params.kaId + "-" + year + dayOfYear;
        if (UCGiftManager.isExists(uniqueKey)) {
            // 已领取过
            UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_5000036, "");
            out.print(resp.toJsonString());
            return;
        }

        // 可领取
        Account account = AccountManager.getAccountByThirdUserId(params.accountId);
        if (account == null) {
            UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_5000031, "");
            out.print(resp.toJsonString());
            return;
        }

        UCGift gift = UCGiftManager.getGift(params.kaId);
        if (gift == null) {
            UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_5000032, "");
            out.print(resp.toJsonString());
            return;
        }

        int serverId = Integer.valueOf(params.serverId);
        Server server = ServerManager.getServer(serverId);
        if (server != null) {
            if (server.getState() == Server.SRV_STATE_HIDE || server.getState() == Server.SRV_STATE_CLOSE) {
                // 服务器维护中
                UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_5000000, "");
                out.print(resp.toJsonString());
                return;
            }

            int playerId = Integer.valueOf(params.roleId);
            GameClient client = new GameClient(server.getUrl());
            client.addHandler(new H19021RespHandler());
            ProtocolWeb.UCGiftWCS.Builder wcs = ProtocolWeb.UCGiftWCS.newBuilder();
            wcs.setUcAccountId(params.accountId);
            wcs.setPlayerId(playerId);
            wcs.setGiftId(params.kaId);
            wcs.setContent(gift.getContent());
            client.send(CHead.H19021, wcs.build());

            UCGiftRecord giftRecord = new UCGiftRecord();
            giftRecord.setGiftId(params.kaId);
            giftRecord.setServerId(serverId);
            giftRecord.setPlayerId(playerId);
            giftRecord.setUcAccountId(params.accountId);
            giftRecord.setReceiveTime(new Date());
            giftRecord.setUniqueKey(uniqueKey);
            UCGiftManager.insertGiftRecord(giftRecord);
        }

        // 不管有没有领取过,都返回成功
        UCResponseData responseData = new UCResponseData();
        responseData.result = "true";
        UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_2000000, JSON.toJSONString(responseData), UCGiftAESKey);
        out.print(resp.toJsonString());

    } catch (Exception e) {
        e.printStackTrace();
        UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_5000000, "");
        out.print(resp.toJsonString());
    }
%>
