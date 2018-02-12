package cn.saturn.web.controllers.adv;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.controllers.adv.dao.AdvDAO;
import cn.saturn.web.controllers.adv.dao.AdvModel;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * 广点通
 * @author Administrator
 *
 */

@Path("gdt33")
public class GdtController {
	
	@Resource
	AdvDAO  advDAO;
	
	public static final int gdtadvback_success = 0; //返回正常
	public static final int gtdadvback_err=-1 ; //请求参数非法
	public static final int gtdadvback2_err=-2 ; //参数解析失败
	
	/**
	 muid：设备 id，由 IMEI（Android 应用）MD5 生成，戒是由 IDFA（iOS 应用）MD5 生成；
	具体加密方案在第 4 部分（muid 加密方案）详细说明；
	click_time：点击发生的时间，由广点通系统生成，取值为标准时间戳，秒级别；
	clickid：广点通后台生成的点击 id，广点通系统中标识用户每次点击生成的唯一标识；
	appid：android 应用为开放平台移劢应用的 id，戒者 ios 应用在 Apple App Store 的 id；
	根据广告主在广点通（e.qq.com）创建转化时提交的基本信息关联；
	advertiser_id：广告主在广点通（e.qq.com）的账户 id；根据广告主在广点通（e.qq.com）
	创建转化时提交的基本信息关联；
	app_type：app 类型；取值为 android 戒 ios；注意是小写；根据广告主在广点通
	（e.qq.com）创建转化时提交的基本信息关联；
	*/
	@Get("toRead")
	@Post("toRead")
	public String gameLoad(@Param("games") String games,@Param("adPlatForm") String adPlatform,@Param("subPlatform") String subPlatform,
			@Param("version") String version, @Param("muid") String muid,@Param("click_time") long click_time,
			@Param("clickid") String clickid,@Param("appid") String appid,
			@Param("advertiser_id") String advertiser_id, @Param("app_type") String app_type) {
		
		Resp resp=null;
		
		String gtd=PTConfig.val("GdtAdv");
		String adversion=PTConfig.val("Adversion");
		
		String os=null;

		//
		if(app_type.toLowerCase().equals("ios")){
			os="ios";
		}else if(app_type.toLowerCase().equals("android")){
			os="android";

		}else{
			resp=new Resp(gtdadvback_err,"");
			return "@"+JSON.toJSONString(resp);
		}
		
		Date tsdateTime=null;
		try{
		//tsdateTime=new java.util.Date(ts* 1000L);
		/*
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	      
	    String d = format.format(click_time);  
	    tsdateTime=format.parse(d);*/
			
		String format="yyyy-MM-dd HH:mm:ss";
		
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    tsdateTime =new Date(click_time * 1000L);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		AdvModel  adv=new AdvModel();
		
		if(games != null){
		adv.setGames(games);
		}
		
		if(adPlatform != null){
		adv.setAdPlatform(adPlatform);
		}
		
		if(subPlatform !=null){
		adv.setSubPlatform(subPlatform);
		}
		
		if(adversion !=null){
			adv.setVersion(adversion);	
		}
		
		if(muid !=null){
		adv.setDevice(muid);
		}
		
		if(tsdateTime !=null){
		adv.setTs(tsdateTime);
		}
		
		if(os !=null){
		adv.setOs(os);
		}
		
		if(muid !=null){
			adv.setLbs(muid);
		}
		
		if(clickid !=null){
		adv.setUa(clickid);
		}
		if(appid !=null ){
		adv.setCid(appid);
		}
		
		if(advertiser_id !=null ){
			adv.setAid(advertiser_id);	
		}
		
		adv.setCreatetime(new Date());
		
		int id=advDAO.insertOrUpdate(adv);
		
		if(id<=0){
			resp=new Resp(gtdadvback_err,"");
			return "@"+JSON.toJSONString(resp);
		}
		
		resp=new Resp(gdtadvback_success,"");
		return "@"+JSON.toJSONString(resp);
		
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
	
	static class Resp {

		int ret;
		String msg;
		
		public int getRet() {
			return ret;
		}

		public void setRet(int ret) {
			this.ret = ret;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
		

		public Resp(int ret, String msg) {
			super();
			this.ret = ret;
			this.msg = msg;
		}
	}
}
