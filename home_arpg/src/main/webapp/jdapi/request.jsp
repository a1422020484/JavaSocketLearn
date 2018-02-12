<%@page import="cn.saturn.web.controllers.gift.dao.GiftManager"%>
<%@page import="cn.saturn.web.controllers.yybapi.dao.OpRecordManager"%>
<%@page import="cn.saturn.web.utils.MD5Util"%>
<%@page import="java.util.Base64"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.reflect.Field"%>
<%@page import="javax.crypto.Mac"%>
<%@page import="javax.crypto.spec.SecretKeySpec"%>
<%@page import="javax.crypto.SecretKey"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
public static class RequestBase{
	public String appKey;
}

public static class RequestGift extends RequestBase{  
	public String cpid;//
    public String gameid;//应用ID
    public int serverid;//服务器ID
    public String servername;//服务器名称
    public String rolename;//角色名称
    public String roleid;//角色名称
    public String uid;//第三方Id
    public String giftcode;//礼物ID
    public String ts;//时间戳
    public String sign;//签名
    
    public boolean checkSign(){
    	String str = appKey+cpid+gameid+serverid+servername+rolename+roleid+uid+giftcode+ts;
    	System.out.println("str->"+str);
    	String mySign = MD5Util.MD5(str).toLowerCase();
    	System.out.println("jdsign->"+sign+",mySign->"+mySign);
    	if(!mySign.equals(sign))
    		return false;
    	return true;
    }
    
    public boolean checkDuplication(String uniqueKey){
    	return GiftManager.isExists(uniqueKey);
    }
}


%>