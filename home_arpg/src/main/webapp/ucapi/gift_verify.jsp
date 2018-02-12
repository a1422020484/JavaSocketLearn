<%--
  User: xiezuojie
  Date: 16/7/25 16:32

  礼包内容校验
  POST
  为了避免CP提供的礼包id被误操作而引起礼包错发,误发的风险,需要CP提供礼包内容校验接口,即由CP提供类似礼包id白名单功能,
  只有在白名单里面的CP的礼包id才能在九游平台上架给用户使用.
--%>
<%@ include file="define.jsp" %>
<%@ page import="cn.saturn.web.controllers.ucapi.UCRequest" %>
<%@ page import="cn.saturn.web.controllers.ucapi.UCResponse" %>
<%@ page import="cn.saturn.web.controllers.ucapi.UCTool" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="cn.saturn.web.controllers.ucapi.gift.UCGift" %>
<%@ page import="cn.saturn.web.controllers.ucapi.gift.UCGiftManager" %>

<%!
    public static class UCRequestParams {
        public int gameId; // 九游定义的游戏id
        public String kaId; // 由CP提供的礼包id
        //public int count; // CP提供的礼包中包含兑换码的数量
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

        UCGift gift = UCGiftManager.getGift(params.kaId);
        if (gift == null) {
            UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_5000032, "");
            out.print(resp.toJsonString());
            return;
        }

        UCResponseData responseData = new UCResponseData();
        responseData.result = "true";
//        System.out.println(JSON.toJSONString(responseData));
        UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_2000000, JSON.toJSONString(responseData), UCGiftAESKey);
        out.print(resp.toJsonString());

    } catch (Exception e) {
        e.printStackTrace();
        UCResponse resp = new UCResponse(ucRequest.id, UCResponse.STATE_5000000, "");
        out.print(resp.toJsonString());
    }
%>
