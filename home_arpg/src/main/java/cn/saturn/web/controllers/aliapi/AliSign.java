package cn.saturn.web.controllers.aliapi;

import xzj.core.util.MD5;

public class AliSign {
	
	public static String toSign(String accountId,String creator,String apiKey){
		
		String sign="accountId="+accountId+"creator="+creator;
		return  MD5.encode(sign+apiKey).toLowerCase();
	}

}
