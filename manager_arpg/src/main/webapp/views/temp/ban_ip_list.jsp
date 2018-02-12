<%@ page import="cn.saturn.web.controllers.power.BanIp" %>
<%@ page import="cn.saturn.web.controllers.power.dao.BanIpDAO" %>
<%@ page import="cn.saturn.web.controllers.power.dao.BanIpManager" %>
<%@ page import="cn.saturn.web.redis.RedisKeys" %>
<%@ page import="cn.saturn.web.redis.RedisUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.util.List" %>
<%--
  User: xiezuojie
  Date: 16/8/17 20:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<%
    BanIpDAO banIpDAO = BanIpManager.banIpDAO; // db

    String ip = request.getParameter("ip");
    if (StringUtils.isNotEmpty(ip)) {
        String opt = request.getParameter("opt");
        // 添加
        if ("add".equals(opt)) {
            String note = request.getParameter("note");
            banIpDAO.insertOrUpdate(ip, note);
        } else if ("del".equals(opt)) {
            // 删除
            banIpDAO.delete(ip);
            // key: k_home_ban_ip >> RedisKeys.K_BAN_IP
            RedisUtils.hdel("k_home_ban_ip", ip);
        }
    }

    // list
    //List<BanIp> banIpList = banIpDAO.getList();
    //pageContext.setAttribute("banIpList", banIpList);
%>

<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="ban_ip_list.jsp">
    <input hidden name="opt" value="add">
    <table>
        <tr>
            <td>添加 IP:</td>
            <td><input name="ip" size="20"/></td>
            <td>NOTE:</td>
            <td><input name="note" size="20"/></td>
            <td><input type="submit"/></td>
        </tr>
    </table>
</form>
<br/>
<table border="1">
    <tr>
        <td>IP</td>
        <td>NOTE</td>
        <td></td>
    </tr>
    <c:forEach items="${banIpList}" var="banIp">
        <tr>
            <td>${banIp.ip}</td>
            <td>${banIp.note}</td>
            <td><a href="ban_ip_list.jsp?opt=del&ip=${banIp.ip}">删除</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
