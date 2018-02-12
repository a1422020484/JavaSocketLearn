package cn.saturn.web.controllers.aliapi;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.saturn.web.code.login.domain.Account;
import cn.saturn.web.code.login.domain.AccountManager;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.controllers.aliapi.AliRequest.PrivilegeCodes;
import cn.saturn.web.controllers.aliapi.AliResponse.Data;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("")
public class AliController {
	

	private static String appKey = PTConfig.val("UCAppKey");
	private static String gameId = PTConfig.val("UCGameId");
	private static String ucPlatform=PTConfig.val("UCPlatform");
	
	@Post("special")
	public String aliRequest(Invocation inv) throws Throwable {
		
		long id=0;
		String game=null;
		String data=null;
		String sign=null;
		try{
		String content = IOUtils.toString(inv.getRequest().getInputStream(), "utf-8");
		JSONObject json = JSON.parseObject(content);
		id= Long.valueOf(json.getString("id"));
		game=json.getString("game");
		data=json.getString("data");
		sign=json.getString("sign");
		
		AliRequest.AlRequestGame aligames =JSON.parseObject(game,AliRequest.AlRequestGame.class);
		
		AliRequest.AlRequestData alidata =JSON.parseObject(data,AliRequest.AlRequestData.class);
		
		//验证签名
		String aliSign =AliSign.toSign(alidata.getAccountId(),alidata.getCreator(),appKey);
		if(!sign.equals(aliSign)){
			 AliResponse resp = new AliResponse(id, AliResponse.STATE_10, new Data());
			 return  "@"+resp.toJsonString();
		}
		String  privilegeInfo=alidata.getPrivilegeInfo();
		
		Account  account= AccountManager.getAccount(alidata.getAccountId(), ucPlatform);
		if (account == null) {
			AliResponse resp = new AliResponse(id, AliResponse.STATE_10, new Data());
			return  "@"+resp.toJsonString();
		}
		
		StringBuffer specialCode=new StringBuffer("");
		
		if(!StringUtils.isEmpty(privilegeInfo)){
			PrivilegeCodes jsonpriInfo=JSON.parseObject(privilegeInfo,PrivilegeCodes.class);
			List<String> jsonpriInfoList=jsonpriInfo.getPrivilegeCodes();
		if(null!=jsonpriInfo){
			for(int i=0;i<jsonpriInfoList.size();i++){
				specialCode.append(jsonpriInfoList.get(i));
				if(i !=jsonpriInfoList.size()-1){
					specialCode.append(",");
				}
			}
		}
		}
		//加入特权和特权码信息
		account.setSpecial(1);
		String code =StringUtils.isEmpty( specialCode.toString())? "":specialCode.toString();
		account.setSpecialcode(code);
		
		boolean result = AccountManager.update(account);
		if (!result) {
			AliResponse resp = new AliResponse(id, AliResponse.STATE_10, new Data());
			return "@"+resp.toJsonString();
		}
		
		AliResponse.Data aliResponseDada =new AliResponse.Data(alidata.getCreator(), alidata.getAccountId(), "");
		
		
		AliResponse resp = new AliResponse(id, AliResponse.STATE_1,  new Data());
		
		//System.out.println("resp="+resp.toJsonString());
		return "@"+resp.toJsonString();
		} catch (IOException e) {
			e.printStackTrace();
			AliResponse resp = new AliResponse(id, AliResponse.STATE_10, new Data());
			return  "@"+resp.toJsonString();
			
		}
	}

}
