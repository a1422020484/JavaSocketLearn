package cn.saturn.web.controllers.adv;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.controllers.adv.GdtController.Resp;
import cn.saturn.web.controllers.adv.dao.AdvDAO;
import cn.saturn.web.controllers.adv.dao.AdvModel;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * 今日头条
 * @author Administrator
 *
 */

@Path("jrtt")
public class JrttController {
	
	/**
	参数  格式
	AID 广告计划 id 原值
	CID 广告创意 id 原值
	CTYPE 创意样式 2=小图模式 3=大图式 4=组图模式 5=视频
	MAC用户终端的eth0 接口的MAC 地址去除分隔符 ":",(保持大写)取md5sum 摘要
	IDFA iOS IDFA 适用iOS6 及以上   原值
	ANDROIDID1 用户终端AndroidID 原值
	IMEI 用户终端的IMEI,15 位数字 取 md5sum 摘要,
 	AAID Android Advertising 原值
    OPENUDID Open UDID 原值
	ANDROIDID用户终端的AndroidID,md5加密 取 md5 摘要
    DUID Windows Phone 用户终端的 DUID,md5 加密取 md5 
	UA 数据上报终端设的 User Agent
	UDID iOS UDID,md5加密
	客户端操作系统的类型  0–Android 1–iOS2– WP3
	IP 媒体投放系统获取的用户终端的公共 IP 地址
	ts 客户端触发监测 的时间
	CALLBACK_URL 激活回调地址
	CALLBACK_PARAM 激活回调参数;
	*/
	
	@Resource
	AdvDAO  advDAO;
	
	public static final int jrttadvback_success = 0; //返回正常
	public static final int jrttadvback_err=-1 ; //请求参数非法
	
	
	@Get("toRead")
	@Post("toRead")
	public String gameLoad(@Param("games") String games,@Param("adPlatForm") String adPlatform,@Param("subPlatform") String subPlatform,
			@Param("version") String version, @Param("cid") String CID,@Param("aid") String AID,@Param("mac") String MAC,
			@Param("idfa") String IDFA,@Param("androidid1") String ANDROIDID1,@Param("os") int OS,
			@Param("imei") String IMEI,@Param("mac1") String mac1,@Param("aaid") String AAID,
			@Param("timestamp") long timestamp,@Param("ip") String IP,@Param("ua") String UA,@Param("lbs") String LBS,
			@Param("callback_url") String CALLBACK_URL,@Param("callback_param") String CALLBACK_PARAM) {
		
		
		String jrtt=PTConfig.val("JrttAdv");
		String adversion=PTConfig.val("Adversion");
		
		Resp resp=null;
		String os=null;
		String device =null;
		//os 0 表示 Ios ,1 表示 Android,2 其他
		if(OS==0){
			os="android";
			//原值转大写
			device=IMEI.toUpperCase();
		}else if(OS==1){
			os="ios";
			//传过来是原值,去掉- ,取MD5SUM
			device=IDFA;
		}else if(OS==2){
			os="wp3";	
		}else{
			resp=new Resp(jrttadvback_err,"");
			return "@"+JSON.toJSONString(resp);
		}
		
		if(StringUtils.isEmpty(CALLBACK_URL.trim())){
			resp=new Resp(jrttadvback_err,"");
			return "@"+JSON.toJSONString(resp);
		}
		
		if(StringUtils.isEmpty(IDFA.trim()) && StringUtils.isEmpty(IMEI.trim())){
			resp=new Resp(jrttadvback_err,"");
			return "@"+JSON.toJSONString("faile");
		}
		
		Date tsdateTime=null;
		try{
		//tsdateTime=new java.util.Date(ts* 1000L);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	      
	    String d = format.format(timestamp);  
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
		
		if(device !=null){
			adv.setDevice(device);
		}
		
		if(tsdateTime !=null){
			adv.setTs(tsdateTime);
		}
		
		if(CALLBACK_URL !=null){
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
		
		if(UA !=null){
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
			resp=new Resp(jrttadvback_err,"");
			return "@"+JSON.toJSONString(resp);
		}
		
		return "@"+JSON.toJSONString("success");
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

