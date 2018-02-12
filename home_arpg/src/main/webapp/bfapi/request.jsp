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
public static class BfRequestBase{
	public String userId;
	public String roleId;
	public String gameZoneId;
	public String exInfo;
	public String appKey;
	public String appSecret;
}


public static class BfRequestRoleInfo extends BfRequestBase{  
}

public static class BfRequestRecharge extends BfRequestBase{  
	public String transactionId;//交易流水（豹风订单号）
    public int appId;//应用ID
    public int channelId;//渠道ID
    public int productId;//商品Id（配置在豹风的首充商品ID）
    public double money;//商品价格
    public int charging_channel;//6003(首充)
    public String sign;//签名
    
    public boolean checkSign(){
    	String mySign = MD5Util.MD5(productId+userId+roleId+gameZoneId+transactionId+appKey+appSecret).toLowerCase();
    	mySign = mySign.substring(8, 24);
    	System.out.println("sign->"+sign+",mySign->"+mySign);
    	if(!mySign.equals(sign))
    		return false;
    	return true;
    }
    
    public boolean checkDuplication(){
    	return !OpRecordManager.isExists(transactionId);
    }
}

public static class BfRequestRechargeBd extends BfRequestBase{  
	public String transactionId;//交易流水（豹风订单号）
    public int appId;//应用ID
    public int channelId;//渠道ID
    public int productId;//商品Id（配置在豹风的首充商品ID）
    public double money;//商品价格
    public int charging_channel;//6003(首充)
    public int gameCoin;//游戏币数量
    public String gameCoinCurrency;//游戏币单位（水晶）
    public String sign;//签名
    
    public boolean checkSign(){
    	String mySign = MD5Util.MD5(gameCoin+money+userId+roleId+gameZoneId+transactionId+appKey+appSecret).toLowerCase();
    	mySign = mySign.substring(8, 24);
    	System.out.println("sign->"+sign+",mySign->"+mySign);
    	if(!mySign.equals(sign))
    		return false;
    	return true;
    }
    
    public boolean checkDuplication(){
    	return !OpRecordManager.isExists(transactionId);
    }
}

public static class BfUtil{
}
%>