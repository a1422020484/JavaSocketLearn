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
public static final int ERROR_CODE_NOGIFT = 206;//找不到礼包配置
public static final int ERROR_CODE_OPSUCCESS = 1000;//发货成功

public static final Map<Integer, String> ERROR_STRS = new HashMap<>();
static{
	ERROR_STRS.put(ERROR_CODE_SUCCESS, "领取成功");
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
	ERROR_STRS.put(ERROR_CODE_NOGIFT, "找不到礼包配置");
}


public class Result{
	public int code;//结果状态
    public String msg;//结果描述
    public Result(){
    	
    }
    public Result(int code){
    	this.code = code;
    	this.msg = ERROR_STRS.get(code);
    	if(this.code != 0)
    		this.code = 1000;//对方指定要失败传回1000
    }
}

public class ResponseData{
	public Result state;
    public ResponseData(){
    	
    }
    public ResponseData(Result state){
    	this.state = state;
    }
}


public class ResponseGift extends ResponseData{
	public Object data;
	public ResponseGift(){
		
	}
	
    public ResponseGift(Result result){
		this.state = result;
	}
}

public class ResponseBase{
	public ResponseData responseData;
	public ResponseBase(ResponseData responseData){
		this.responseData = responseData;
	}
	
	public String toJson(){
		return JSON.toJSONString(this.responseData);
	}
}
%>