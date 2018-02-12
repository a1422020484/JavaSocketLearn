package cn.saturn.web.controllers.adv;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.controllers.adv.dao.AdvDAO;
import cn.saturn.web.controllers.adv.dao.AdvModel;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * UC汇川广告平台
 * @author Administrator
 *
 */

@Path("uchc")
public class UchcController {
	
	/**
	参数 描述 格式 可选性
	IDFA_SUM IOS 设备唯一标识
	原值转大写，计算 32 位
	md5sum，再转大写 IOS 必选
	IMEI_SUM Android 设备唯一标识
	Android 必填，原值转大写，计算
	32 位 md5sum，再转大写 安卓必选
	TS 客户端触发监测的时间 UTC 时间戳 必选
	CALLBACK_URL 激活回调地址 经过 url encode 后的 url 方案一必选
	CALLBACK_PARAM 激活回调参数 方案二必选
	AID 广告单元 ID 原值 可选
	CID 广告创意 ID 原值 可选
	MAC_SUM 用户设备的 mac 地址的 md5
	保留 “:”，保持大写，取
	md5sum，再转大写 可选
	MAC_SUM1 用户设备的 mac 地址的 md5
	去除“:”，保持大写，取
	md5sum，再转大写 可选
	ANDROIDID_SUM
	用户终端的 AndroidID，md5 加
	密 保持大写，取 md5sum，再转大写 可选
	UA 数据上报终端设备的 User Agent 经过 url encode 的字符串 可选
	OS 操作系统 0 表示 Ios ,1 表示 Android,2 其他 可选
	LBS 经纬度
	精确到两位小数，精度在前维度在
	后，用逗号分隔 可选
	IP
	媒体投放系统获取的用户终端的
	IP 地址 A.B.C.D 可选
	*/
	
	@Resource
	AdvDAO  advDAO;
	
	public static final int ucadvback_success = 200; //返回正常
	public static final int ucadvback_err=0 ; //返回错位
	
	
	@Get("toRead")
	@Post("toRead")
	public String gameLoad(@Param("games") String games,@Param("adPlatForm") String adPlatform,@Param("subPlatform") String subPlatform,
			@Param("version") String version, @Param("idfa_sum") String IDFA_SUM,@Param("imei_sum") String IMEI_SUM,
			@Param("ts") long ts,@Param("callback_url") String CALLBACK_URL,@Param("callback_param") String CALLBACK_PARAM,@Param("aid") String AID,
			@Param("cid") String CID, @Param("mac_sum") String MAC_SUM, @Param("mac_sum1") String MAC_SUM1, @Param("androidid_sum") String ANDROIDID_SUM,  
			@Param("ua") String UA,@Param("os") int OS,@Param("lbs") String LBS,@Param("ip") String IP) {
		
		
		String uc=PTConfig.val("UCAdv");
		String adversion=PTConfig.val("Adversion");
		
		String os=null;
		String device =null;
		//os 0 表示 Ios ,1 表示 Android,2 其他
		if(OS==0){
			os="ios";
			device=IDFA_SUM;
		}else if(OS==1){
			os="android";
			device=IMEI_SUM;
		}else if(OS==2){
			os="other";
			
		}
		
		Date tsdateTime=null;
		try{
		//tsdateTime=new java.util.Date(ts* 1000L);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	      
	    String d = format.format(ts);  
	    tsdateTime=format.parse(d);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		AdvModel  adv=new AdvModel();
		if(games !=null){
			adv.setGames(games);
		}
		
		if(adPlatform !=null){
			adv.setAdPlatform(adPlatform);
		}
		if(subPlatform !=null){
			adv.setSubPlatform(subPlatform);
		}

		if(adversion !=null){
			adv.setVersion(adversion);
		}
		
		if( device!=null){
			adv.setDevice(device);
		}
		
		if( tsdateTime!=null){
			adv.setTs(tsdateTime);
		}
		
		if( CALLBACK_URL !=null){
			adv.setCallback_url(CALLBACK_URL);
		}
		
		if(CALLBACK_PARAM !=null){
			adv.setCallback_param(CALLBACK_PARAM);
		}
		
		if(AID !=null){
			adv.setAid(AID);
		}
		
		if(CID !=null){
			adv.setCid(CID);
		}
		
		if(MAC_SUM !=null){
			adv.setMac_sum(MAC_SUM);
		}
		
		if( MAC_SUM1 !=null){
			adv.setMac_sum1(MAC_SUM1);
		}
			
		if(ANDROIDID_SUM !=null){
			adv.setAndroidid_sum(ANDROIDID_SUM);
		}
		
		if(UA !=null ){
			adv.setUa(UA);
		}
		
		if(os !=null){
			adv.setOs(os);
		}
		
		if(LBS !=null){
			adv.setLbs(LBS);
		}
		
		if(IP !=null){
			adv.setIp(IP);
		}
		adv.setCreatetime(new Date());
		
		int id=advDAO.insertOrUpdate(adv);
		
		if(id<=0){
			return "@"+ucadvback_err;
		}
		
		return "@"+ucadvback_success;
	}
	
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
