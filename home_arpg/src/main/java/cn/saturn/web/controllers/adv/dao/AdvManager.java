package cn.saturn.web.controllers.adv.dao;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.saturn.web.code.platform.utils.HttpUtils;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.code.login.domain.Account;
import cn.saturn.web.code.login.domain.AccountManager;
import cn.saturn.web.code.platform.PTConfig;
import sun.misc.BASE64Encoder; 
import sun.misc.BASE64Decoder; 
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Component
public class AdvManager {
	
	protected static final Logger logger = LoggerFactory.getLogger("login");
	private static final String uc="uc";
	private static final String gdtand="gdtand";
	private static final String gdtios="gdtios";
	private static final String jrttand="jrttand";
	private static final String jrttios="jrttios";
	
	public  static void toCheck(Account account ){
		
		//System.out.println("account2="+account.toString());
		
		String ucadv=PTConfig.val("UCAdv").trim();
		String gdtadv=PTConfig.val("GdtAdv").trim();
		String jrttadv=PTConfig.val("JrttAdv").trim();
		String os=account.getGames();
		String device=account.getIdfa();
		
		 Date sevenDays =DateUtils.getDaysAgo(7);
		 
		 List<AdvModel> advList=new ArrayList<AdvModel>();
		//UC设备 原值转大写,md5sum,再转大写
		 String ucdevicemd5=DigestUtils.md5Hex(device.toUpperCase()).toUpperCase();
		 AdvModel  ucadvModel= AdvDaoManager.getAdvByDev(ucdevicemd5, sevenDays);
		 if(ucadvModel !=null){
			 ucadvModel.setGames(uc);
			 advList.add(ucadvModel);
		 }
		 
		//广点通原值转小写,md5sum,再转大写
		 String gdtdevicemd5=DigestUtils.md5Hex(device.toLowerCase());
		 AdvModel  gdtadvModel= AdvDaoManager.getAdvByDev(gdtdevicemd5, sevenDays);
		 if(gdtadvModel !=null){
			 gdtadvModel.setGames(gdtand);
			 advList.add(gdtadvModel);
		 }
		 
		//广点通原值转大写,md5sum,再转大写
		 String gdtdeviceMaxmd5=DigestUtils.md5Hex(device.toUpperCase()).toLowerCase();
		 AdvModel  gdtMaxadvModel= AdvDaoManager.getAdvByDev(gdtdeviceMaxmd5, sevenDays);
		 if(gdtMaxadvModel !=null){
			 gdtMaxadvModel.setGames(gdtios);
			 advList.add(gdtMaxadvModel);
		 }
		 
		//jrtt原值
		 AdvModel  jrttyzadvModel= AdvDaoManager.getAdvByDev(device, sevenDays);
		 if(jrttyzadvModel !=null){
			 jrttyzadvModel.setGames(jrttand);
			 advList.add(jrttyzadvModel);
		 }
		 
		//jrtt原值md5
		 String jrttdevicemd5=DigestUtils.md5Hex(device);
		AdvModel  jrttadvModel= AdvDaoManager.getAdvByDev(jrttdevicemd5, sevenDays);
		if(jrttadvModel !=null){
			jrttadvModel.setGames(jrttios);
			 advList.add(jrttadvModel);
		 }
		
		if(advList.size()>1){
		Collections.sort(advList, new Comparator<AdvModel>() {

			@Override
			public int compare(AdvModel o1, AdvModel o2) {
//				return Integer.compare(o1.getTodcrystal(), o2.getTodcrystal());
				if (o1.getCreatetime().getTime() < o2.getCreatetime().getTime()) {
					return 1;
				}else{
				return -1;
				}
			}
		});
		
		}
		 
		if(advList.size()>0){
		for(AdvModel advGames:advList){
		//UC设备 原值转大写,md5sum,再转大写
		 if(advGames.getGames().equals(uc)){
		 
			 //System.out.println("ucadv=" + adv.toString());
			 if(advGames.getAdPlatform().trim().equals(ucadv)){
				 account=UcCheck( account,os,advGames);
			 }
			 if(StringUtils.isNotEmpty(account.getAdPlatform().trim())){
				 return  ;
			 }
		 }
		 
		//广点通原值转小写,md5sum,再转大写
		 if(advGames.getGames().equals(gdtand)){
		
			if (advGames.getAdPlatform().equals(gdtadv)){
				account= GtdCheck( account,os,advGames); 
			 }
			if(StringUtils.isNotEmpty(account.getAdPlatform().trim())){
				 return  ;
			 }
		 }
		 
		//广点通原值转大写,md5sum,再转大写
		 if(advGames.getGames().equals(gdtios)){	 
			if (advGames.getAdPlatform().equals(gdtadv)){
				account= GtdCheck( account,os,advGames);
			 }
			if(StringUtils.isNotEmpty(account.getAdPlatform().trim())){
				 return  ;
			 }
		 }
		 
		//jrtt原值
		 if(advGames.getGames().equals(jrttand)){
			if (advGames.getAdPlatform().equals(jrttadv)){
				 account=JrttCheck( account,os,advGames);
			 }
			if(StringUtils.isNotEmpty(account.getAdPlatform().trim())){
				 return  ;
			 }
		 }
		 
		//jrtt原值md5
		if(advGames.getGames().equals(jrttios)){
			if (advGames.getAdPlatform().equals(jrttadv)){
				 JrttCheck( account,os,advGames);
			 }
			if(StringUtils.isNotEmpty(account.getAdPlatform().trim())){
				 return  ;
			 }
		 }
		
		}
		}
		return ;
	}
	
	public static  Account UcCheck(Account account,String os,AdvModel adv){
		
		//System.out.println("ucCheck- -----=" + account.toString()+"----"+adv.toString() );
		
		
		String ucAdvUrl=PTConfig.val("UCAdvUrl");
		String callbackvalue =adv.getCallback_param();
		//String callback="&callback_param="+callbackvalue;
		/*account.setAdPlatform(adv.getAdPlatform());
 		AccountManager.update(account);*/
		try{
			
		if(adv.getAdPlatform() !=null && StringUtils.isEmpty( account.getAdPlatform().trim())){
	 			account.setAdPlatform(adv.getAdPlatform());
	 	}
	 		
	 	if(adv.getSubPlatform() !=null && StringUtils.isEmpty( account.getAdSubPlatform().trim())){
	 		account.setAdSubPlatform(adv.getSubPlatform());
	 	}
	 	AccountManager.update(account);
			
		int type = HttpUtils.create(ucAdvUrl).addParam("&callback_param", callbackvalue).getBackCode();
	 	//System.out.println("ucCheck- resp=" + type);
		
		logger.info("ucCheck- -----="+"type="+type+"===" + account.toString()+"----"+adv.toString() );
		
	 	if(type==200){
	 		//设置广告信息已经返回
	 		/*adv.setAdvType(1);
	 		AdvDaoManager.insertOrUpdate(adv);*/
	 			
	 		adv.setAdvType(1);
	 		AdvDaoManager.Update(adv);
	 		
	 	}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  account;
	}
	
	
	public static  Account GtdCheck(Account account,String os,AdvModel adv){
		
		//System.out.println("gtdCheck- -----=" + account.toString()+"----"+adv.toString() );
		
		//logger.info("gtdCheck- -----=" + account.toString()+"----"+adv.toString());
		
		String GdtBackUrl=PTConfig.val("GdtBackUrl");
		String advertiser_id=PTConfig.val("advertiser_id");
		//String appid=PTConfig.val("appid");
		String conv_type=PTConfig.val("conv_type");
		String encrypt_key=PTConfig.val("encrypt_key");
		String sign_key=PTConfig.val("sign_key");
				
		
		String appType=adv.getOs();
		
		String backOS=null;
		if(appType.equals("android")){
			backOS="ANDROID";
		}else{
			backOS="IOS";
		}
		
		
		/*app_type=ANDROID&click_id=007210548a030059ccdfd1d4&client_ip=10.11.12.13&conv_time=1422263664&muid=0
				f074dc8e1f0547310e729032ac0730b&sign_key=08ebe39d34c421b8*/
		String md5adv="app_type="+backOS+"&click_id="+adv.getUa()+"&client_ip="+"&conv_time="+adv.getTs().getTime()/1000+"&muid="+adv.getDevice()+"&sign_key="+sign_key;
		
		//System.out.println("md5adv="+md5adv);
		
		String  md5=DigestUtils.md5Hex(md5adv).toLowerCase();
		
		try{
			
			if(adv.getAdPlatform() !=null && StringUtils.isEmpty(account.getAdPlatform().trim())){
	 			account.setAdPlatform(adv.getAdPlatform());
	 		}
	 		
	 		if(adv.getSubPlatform() !=null && StringUtils.isEmpty(account.getAdSubPlatform().trim())){
	 		account.setAdSubPlatform(adv.getSubPlatform());
	 		}
	 		AccountManager.update(account);
			
		 String resp = HttpUtils.create(GdtBackUrl).
				addParam("click_id", adv.getUa()).
				addParam("muid", adv.getDevice()).
				addParam("appid", adv.getCid()).
				addParam("conv_time", String.valueOf(adv.getTs().getTime()/1000)).
				addParam("client_ip", "").
				addParam("encstr", md5).
				addParam("encver", "1.0").
				addParam("advertiser_id", advertiser_id).
				addParam("app_type", backOS).
				addParam("conv_type", conv_type).
				addParam("sign_key", sign_key).
				post();
		/*
		System.out.println("GdtBackUrl="+GdtBackUrl);
		System.out.println("click_id="+adv.getUa());
		System.out.println("muid="+adv.getDevice());
		System.out.println("appid="+adv.getCid());
		System.out.println("conv_time="+String.valueOf(adv.getTs().getTime()/1000));
		System.out.println("client_ip="+"");
		System.out.println("encstr="+md5);
		System.out.println("encver="+"1.0");
		System.out.println("advertiser_id="+advertiser_id);
		System.out.println("app_type="+backOS);
		System.out.println("conv_type="+conv_type);
		System.out.println("sign_key="+sign_key);*/
		
		
		/*System.out.println("gtdCheck- resp=" + resp);*/
		
		JSONObject respData = JSON.parseObject(resp);
		int ret = Integer.valueOf(respData.getString("ret"));
	 	
		logger.info("gtdCheck- -----="+"ret="+ret+"===" + account.toString()+"----"+adv.toString());
		
	 	if(ret==0){
	 		adv.setAdvType(1);
	 		AdvDaoManager.Update(adv);
	 		
	 	}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  account;
	}
	
	
	
	public static  Account JrttCheck(Account account,String os,AdvModel adv){
		
		//System.out.println("jrttCheck- -----=" + account.toString()+"----"+adv.toString() );
		
		//logger.info("jrttCheck- -----=" + account.toString()+"----"+adv.toString() );
		
		String ucAdvUrl=PTConfig.val("UCAdvUrl");
		String callbackvalue =adv.getCallback_url();
		//String callback="&callback_param="+callbackvalue;
		String  backurl=adv.getCallback_url();
		try{
			
			if(adv.getAdPlatform() !=null && StringUtils.isEmpty(account.getAdPlatform().trim())){
	 			account.setAdPlatform(adv.getAdPlatform());
	 		}
	 		
	 		if(adv.getSubPlatform() !=null && StringUtils.isEmpty( account.getAdSubPlatform().trim())){
	 		account.setAdSubPlatform(adv.getSubPlatform());
	 		}
	 		AccountManager.update(account);
			
		String resp = HttpUtils.create(backurl).get();
		
		//System.out.println("jrttCheck- resp=" + resp);
		
		JSONObject respData = JSON.parseObject(resp);
		int ret = Integer.valueOf(respData.getString("ret"));
		
		logger.info("jrttCheck- -----="+"ret="+ret+"===" + account.toString()+"----"+adv.toString() );
		
	 	if(ret==0){
	 		
	 		adv.setAdvType(1);
	 		AdvDaoManager.Update(adv);
	 		
	 	}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  account;
	}
	
	
	//亦或加密
	public  static String  Encryption(String info,String key){
		StringBuffer sb=new StringBuffer();
		
		int  j=0;
		for(int i=0;i<info.length();i++){
			sb.append( info.charAt(i) ^ key.charAt(j) );
			j=j+1;
		}
		
		return sb.toString();
	}
	
	 public static String setEncrypt(String str,String sn){
	        //String sn="ziyu"; //密钥
	        int[] snNum=new int[str.length()];
	        String result="";
	        String temp="";

	        for(int i=0,j=0;i<str.length();i++,j++){
	            if(j==sn.length())
	                j=0;
	            snNum[i]=str.charAt(i)^sn.charAt(j);
	        }

	        for(int k=0;k<str.length();k++){

	            if(snNum[k]<10){
	                temp="00"+snNum[k];
	            }else{
	                if(snNum[k]<100){
	                    temp="0"+snNum[k];
	                }
	            }
	            result+=temp;
	        }
	        return result;
	    }
	

	 
	// 将 s 进行 BASE64 编码 
	@SuppressWarnings("restriction")
	public static String getBASE64(String s) {
		if (s == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}
	 
	// 将 BASE64 编码的字符串 s 进行解码 
	@SuppressWarnings("restriction")
	public static String getFromBASE64(String s) {
		if (s == null)
			return null;
		@SuppressWarnings("restriction")
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static void main(String[] args) {
		//System.out.println( Encryption("asasa45","454qwqw"));
		//System.out.println(getBASE64("8570852226966") );
		
		
		System.out.println( DigestUtils.md5Hex("510FF2E8-551D-4190-90D2-4E24126AC5F4".toUpperCase()).toLowerCase());
		
		//int  code= HttpUtils.create("http://huichuan.sm.cn/td").addParam("tp_type", "roi").addParam("callback_param", "r_8186902653680245070_11601_1").getBackCode();
	 	
		
	}
	
}
