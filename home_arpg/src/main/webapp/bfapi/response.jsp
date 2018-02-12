<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
public static final int ERROR_CODE_SUCCESS = 1;//成功
public static final int ERROR_CODE_TIMESTAMP_EXPIRE = 0;//请求参数timestamp已过期
public static final int ERROR_CODE_SIG = 2;//sig参数错误
public static final int ERROR_CODE_PKEY = 3;//sig参数错误
public static final int ERROR_CODE_NOROLE = 101;//该openid没有注册游戏/该openid在该区没有角色
public static final int ERROR_CODE_EXCEPTION = 201;//程序异常
public static final int ERROR_CODE_PARAMS = 202;//程序异常
public static final int ERROR_CODE_ROLEID = 203;//角色ID异常
public static final int ERROR_CODE_SERVERDOWN = 204;//服务器维护中
public static final int ERROR_CODE_DUPLICATION = 205;//重复领取
public static final int ERROR_CODE_OPSUCCESS = 1000;//发货成功

public static final Map<Integer, String> ERROR_STRS = new HashMap<>();
static{
	ERROR_STRS.put(ERROR_CODE_SUCCESS, "拉取成功");
	ERROR_STRS.put(ERROR_CODE_TIMESTAMP_EXPIRE, "请求参数timestamp已过期");
	ERROR_STRS.put(ERROR_CODE_SIG, "sig参数错误");
	ERROR_STRS.put(ERROR_CODE_PKEY, "pkey参数错误");
	ERROR_STRS.put(ERROR_CODE_NOROLE, "该userId没有注册游戏/该userId在该区没有角色");
	ERROR_STRS.put(ERROR_CODE_EXCEPTION, "服务器异常");
	ERROR_STRS.put(ERROR_CODE_PARAMS, "参数错误");
	ERROR_STRS.put(ERROR_CODE_ROLEID, "角色ID异常");
	ERROR_STRS.put(ERROR_CODE_SERVERDOWN, "服务器维护中");
	ERROR_STRS.put(ERROR_CODE_DUPLICATION, "重复领取");
	ERROR_STRS.put(ERROR_CODE_OPSUCCESS, "已成功发货");
}


public class RoleInfo{
	public String userId;
	public String roleId;
	public String gameZoneId;
	public String gameZoneName;
	public String roleName;
    public int level;
}

public class BfResponseData{
	public int status;//结果状态
    public String msg;//结果描述
    public BfResponseData(){
    	
    }
    public BfResponseData(int status){
    	this.status = status;
    	this.msg = ERROR_STRS.get(status);
    }
}

public class BfResponseRoleInfo extends BfResponseData{
	public RoleInfo roleInfo;
	public BfResponseRoleInfo(){
		
	}
	public BfResponseRoleInfo(int status, RoleInfo roleInfo){
		this.status = status;
		this.msg = ERROR_STRS.get(status);
		this.roleInfo = roleInfo;
	}
}

public class BfResponseRecharge extends BfResponseData{
	public String result;
	public BfResponseRecharge(){
		
	}
	
    public BfResponseRecharge(String result){
		this.result = result;
	}
}

public class BfResponseRechargeBd extends BfResponseData{
	public String result;
	public BfResponseRechargeBd(){
		
	}
	
    public BfResponseRechargeBd(String result){
		this.result = result;
	}
}

public class BfResponse{
	public BfResponseData responseData;
	public BfResponse(BfResponseData responseData){
		this.responseData = responseData;
	}
	
	public String toJson(){
		return JSON.toJSONString(this.responseData);
	}
}
%>