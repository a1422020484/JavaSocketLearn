package cn.saturn.web.center;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.saturn.operation.OperationExt;
import cn.saturn.operation.ReportSqlTemp;
import cn.saturn.web.center.dao.GamesDAO;
import cn.saturn.web.center.dao.GamesModel;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.utils.Config;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.HttpTookit;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.MD5Util;
import cn.saturn.web.utils.Utils;

@Component
public class SendCenter {
	
	//向土星后台中心服务器发送数据
	private static boolean sends = Utils.config.get("sendcenter", false);
	//中心服务器地址
	private static String ADDR = Config.val("centerIp");
	//游戏后台唯一标识符
	private static String games = Config.val("gamever");
	
	protected static final Logger daydatelogger = LoggerFactory.getLogger(LogType.daydate);
	
	protected static final Logger daylinelogger = LoggerFactory.getLogger(LogType.dayline);
	
	@Resource
	GamesDAO gamesDAO;
	
	
	@Resource
	javax.sql.DataSource dataSource;
	
	/**
	 * 向土星中心服务器发送日报汇总表
	 * @throws ParseException 
	 */
	public  void SendDaydate(int type) throws ParseException{
		if(!sends){
			return;
		}
		String addr="/center/recedaydate/daydate";
		Date date;
		Date daten;
		GamesModel	gameModel= gamesDAO.getGames();
		date =DateUtils.getZeroPoint(gameModel.getTimes());
		daten=DateUtils.getZeroPoint(new Date());
		
		for(date.getTime();daten.getTime()-date.getTime()>0;DateUtils.getAddDay(date, 1)){
			
			//  1  LIKE "2017-05-17%"
			//时间  0  (SELECT "2017-05-17" as t_time )
		
			String timeStr=DateUtils.getDayStr(date);
			//String likeStr=" LIKE  \\\""+ timeStr+"%\\\"";
			//String selectStr= " SELECT \\\""+timeStr +"\\\" as t_time  ";
			String likeStr=" LIKE  \""+ timeStr+"%\" ";
			String selectStr= " SELECT \""+timeStr +"\" as t_time  ";
			
			double  rate=gameModel.getRate();
			
			JdbcTemplate template = new JdbcTemplate(dataSource);
			ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
			
			String sql = "";

			sql = MessageFormat.format(temp.getSql(),selectStr ,likeStr,rate);
			//System.out.println(sql);
			List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
			
			String value=SendCenter.keyToValue(out, temp.getTitle_en());
			
			//加密key,后台唯一标识符+设备新增+新增注册人数，md5加密;
			Random rd=new Random();
			int cid=rd.nextInt(100);
			String cidStr=String.valueOf(cid);
			String keys=games+cidStr;
			
			String gameidt=MD5Util.MD5(keys);
			 
			String sendtime=DateUtils.getDayStr(new Date());
			
			String  sendStr="sendtime="+sendtime+"&gamever="+games+"&gameidt="+gameidt+"&cid="+cid+value;
			//+"&gameidt"+gameidt
			//+"&gameidt="+gameidt
			daydatelogger.info(sendStr);
			String sendHead=ADDR+addr;
			//发送http请求
			HttpTookit.doGet(sendHead, sendStr, "UTF-8", true);
			//System.out.println("111:"+sendStr);
			date=DateUtils.getAddDay(date, 1);
			gameModel.setTimes(date);
			gamesDAO.insertOrUpdate(gameModel);
		}	
	}
	
	
	public  void SendDayline(int type) throws ParseException{
		if(!sends){
			return;
		}
		String addr="/center/recedayline/dayline";
		GamesModel	gameModel= gamesDAO.getGames();
		double  rate=gameModel.getRate();
		
		DateParam param = new DateParam();
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String nowDayStr= DateUtils.getNowDay();
		String resultTime = param.getParam(nowDayStr, nowDayStr);
		String resultQuery = param.getQuery(nowDayStr, nowDayStr);
		
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);

		String sql = MessageFormat.format(temp.getSql(), resultQuery,resultTime,rate);
		//System.out.println(sql);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		String value=SendCenter.keyToValue(out, temp.getTitle_en());
		
		//加密key,后台唯一标识符+设备新增+新增注册人数，md5加密;
		Random rd=new Random();
		int cid=rd.nextInt(100);
		String cidStr=String.valueOf(cid);
		String keys=games+cidStr;
		
		String gameidt=MD5Util.MD5(keys);
		 
		String sendtime=DateUtils.getDayStr(new Date());
		
		String  sendStr="sendtime="+sendtime+"&gamever="+games+"&gameidt="+gameidt+"&cid="+cid+value;
		
		daylinelogger.info(sendStr);
		//System.out.println("111:"+sendStr);
		String sendHead=ADDR+addr;
		HttpTookit.doGet(sendHead, sendStr, "UTF-8", true);
		//System.out.println("send ok");
	}
	
	
	/**
	 * 将数组转 "key1=value1&key2=value2"
	 * @param titleEN
	 * @param queryOut
	 * @param 
	 * @return
	 */
	
	public static String keyToValue(List<String[]> queryOut,String[] titleEN) {
		
		StringBuffer sBuffer = new StringBuffer();
		if(queryOut.size()!=1){
			
			return null;
		}
		
		for (String[]  query:queryOut) {
			for(int j = 0; j < titleEN.length; j++){
			String	 value=query[j];
			if (value == null || value.equals(""))
				value = "";
			String	en=titleEN[j];
			sBuffer.append("&"+en+"="+value);
			
			}
			
			
		}
		
		return sBuffer.toString();
	}
	
	 public static void main(String[] args) {
	      String timeStr="2017-05-17"; 
	      String likeStr=" LIKE  \""+ timeStr+"%\"";
	        System.out.println("111:"+likeStr);
	        String selectStr= " SELECT \""+timeStr +"\" as t_time  ";
	        System.out.println("111:"+selectStr);
	        
	        System.out.println(new Date());
	    }
}
