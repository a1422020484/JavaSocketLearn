<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
public static final int ERROR_CODE_SUCCESS = 0;//成功
public static final int ERROR_CODE_TIMESTAMP_EXPIRE = 1;//请求参数timestamp已过期
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
	ERROR_STRS.put(ERROR_CODE_NOROLE, "该openid没有注册游戏/该openid在该区没有角色");
	ERROR_STRS.put(ERROR_CODE_EXCEPTION, "服务器异常");
	ERROR_STRS.put(ERROR_CODE_PARAMS, "参数错误");
	ERROR_STRS.put(ERROR_CODE_ROLEID, "角色ID异常");
	ERROR_STRS.put(ERROR_CODE_SERVERDOWN, "服务器维护中");
	ERROR_STRS.put(ERROR_CODE_DUPLICATION, "重复领取");
	ERROR_STRS.put(ERROR_CODE_OPSUCCESS, "已成功发货");
}

public class YYBResponseServer {
    public String id; // 区服id
    public String payid;//支付区服id
    public String name; // 区服名称
    public int type;//区的类型，1-qq，2-微信，3-qq和微信通用
}

public class RoleInfo{
	public String roleid;
	public String rolename;
}

public class YYBResponseData{
	public int ret;//结果状态
    public String msg;//结果描述
    public YYBResponseData(){
    	
    }
    public YYBResponseData(int ret){
    	this.ret = ret;
    	this.msg = ERROR_STRS.get(ret);
    }
}

public class YYBResponseSvrList extends YYBResponseData{ 
    public List<YYBResponseServer> list; // json
    
    public YYBResponseSvrList(){
    	super();
    }
    public YYBResponseSvrList(int ret, List<YYBResponseServer> list){
    	this.ret = ret;
    	this.msg = ERROR_STRS.get(ret);
    	this.list = list;
    }
}

public class YYBResponseRoleInfo extends YYBResponseData{
	public List<RoleInfo> list;
	public YYBResponseRoleInfo(){
		
	}
	public YYBResponseRoleInfo(int ret, List<RoleInfo> list){
		this.ret = ret;
		this.msg = ERROR_STRS.get(ret);
		this.list = list;
	}
}

public class YYBResponseGameOp extends YYBResponseData{
	
}

public class YYBResponse{
	public YYBResponseData responseData;
	public YYBResponse(YYBResponseData responseData){
		this.responseData = responseData;
	}
	
	public String toJson(){
		return JSON.toJSONString(this.responseData);
	}
}
%>