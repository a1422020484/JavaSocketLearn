package cn.saturn.operation;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.saturn.web.utils.DateUtils;

public class TimeUtils {

	public static final int oneDayTime = 60 * 60 * 24;
	public static final long oneDayTimeL = oneDayTime * 1000L;
	public static final double secondRate = 0.001;

	/** 获取时间截(int, 精确到s) **/
	public static int currentTimes() {
		long nowTimeL = System.currentTimeMillis();
		// int nowTime = (int) ((long) (nowTimeL * 0.001f));
		int nowTime = (int) (nowTimeL * 0.001);
		return nowTime;
	}

	/** 获取时间截(int, 精确到s) **/
	public static int time(long timeL) {
		return (int) (timeL * 0.001);
	}

	/** 把时间转到当日的某一点 **/
	public static long todayTimes(long timeL, int hour) {
		// 判断这个时间是否过了时间点
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(timeL)); // 设置当前时间
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, 0); // 0分
		cal.set(Calendar.SECOND, 0); // 0秒

		return cal.getTimeInMillis();
	}

	/** 把时间控制到当日的0点 **/
	public static long todayTimes(long timeL) {
		return todayTimes(timeL, 0);
	}

	/** 输出时间文本 **/
	public static String toString(long time, String format) {
		DateFormat format0 = new SimpleDateFormat(format);
		Date date = new Date(time);
		return format0.format(date);
	}

	/** 输出时间文本 **/
	public static String toString(int time, String format) {
		return toString((long) (time * 1000L), format);
	}

	public static final String defaultFormat = "yyyy-MM-dd HH:mm:ss";
	public static final String dayFormat = "yyyy-MM-dd";

	/** 输出时间文本(yyyy-MM-dd HH:mm:ss) **/
	public static String toString(int time) {
		return toString((long) (time * 1000L), defaultFormat);
	}

	/** 输出时间文本(yyyy-MM-dd HH:mm:ss) **/
	public static String toString(long time) {
		return toString(time, defaultFormat);
	}

	/** 从文本中转化成时间段 **/
	public static long toTimes(String dateStr, String format) {
		DateFormat format0 = new SimpleDateFormat(format);
		try {
			Date date = format0.parse(dateStr);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/** 从文本中转化成时间段 **/
	public static long toTimes(String dateStr) {
		return toTimes(dateStr, defaultFormat);
	}

	/**
	 * 获取当天时间字符串
	 * 
	 * @return
	 */
	public static String getTodayStr() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	/**
	 * 获取昨天时间字符串
	 * 
	 * @return
	 */
	public static String getYesterdayStr() {
		Date date = new Date(System.currentTimeMillis() - 86400000);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	/**
	 * 获取时间字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeStr(Date date) {
		if (date == null) {
			return "null";
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		return format.format(date);
	}

	/**
	 * 检测等待时间
	 * 
	 * @param prevTime
	 *            开始时间
	 * @param waiteTime
	 *            等待时间
	 * @return 是否已经达到时间了
	 */
	public static boolean checkWaiteTime(long prevTime, long waiteTime) {
		long nowTime = System.currentTimeMillis();
		long dt = nowTime - prevTime;
		return dt >= waiteTime;
	}

	/**
	 * 获取时间差
	 * 
	 * @param prevTime
	 * @return
	 */
	public static long getIntervalTime(long prevTime) {
		long nowTime = System.currentTimeMillis();
		return nowTime - prevTime;
	}
	
	/***
	 * 
	 * 传入时间字符串 "2016-05-06" 
	 * 返回 当日零时零分零秒 字符串 "2016-05-06 00:00:00"
	 * @param sDateStr
	 * @return
	 * @throws ParseException
	 */
	
	public static String getParamHmsst(String sDateStr) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = df.parse(sDateStr);
		Calendar calender = Calendar.getInstance();
		
		calender.setTime(sDate);
		calender.set(Calendar.HOUR, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
	    return	 DateUtils.formatHms(calender.getTime());
	
	}
	
	/***
	 * 
	 * 传入时间字符串 "2016-05-06" 
	 * 返回 当日零时零分零秒 Date 2016-05-06 00:00:00
	 * @param sDateStr
	 * @return
	 * @throws ParseException
	 */
	
	public static Date getParamHmsstD(String sDateStr) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = df.parse(sDateStr);
		Calendar calender = Calendar.getInstance();
		
		calender.setTime(sDate);
		calender.set(Calendar.HOUR, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
	    return	 sDate;
	
	}
	
	
	
	/***
	 * 
	 * 传入时间字符串 "2016-05-06" 
	 * 返回 当日零时零分零秒 字符串 "2016-05-06 23:59:59"
	 * @param endDateStr
	 * @return
	 * @throws ParseException
	 */
	
	public static String getParamHmsend(String endDateStr) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(endDateStr);
		Calendar calender = Calendar.getInstance();
		
		calender.setTime(endDate);
		calender.set(Calendar.HOUR, 23);
		calender.set(Calendar.MINUTE, 59);
		calender.set(Calendar.SECOND, 59);
	    return	 DateUtils.formatHms(calender.getTime());
	
	}
	
	/***
	 * 
	 * 传入时间String字符串 "2016-05-06" 增加的时间int
	 * 返回 增加后的String的时间字符串
	 * @param endDateStr
	 * @return
	 * @throws ParseException
	 */
	public static String getParam(String endDateStr,int addtime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(endDateStr);
		Calendar calender = Calendar.getInstance();
		
		calender.setTime(endDate);
		calender.set(Calendar.DATE, addtime);
	    return	 DateUtils.formatHms(calender.getTime());
	
	}
	

	/***
	 * 
	 * 传入时间字符串 "2016-05-06" 
	 * 返回 当日零时零分零秒 字符串  2016-05-06 23:59:59 ;
	 * @param endDateStr
	 * @return
	 * @throws ParseException
	 */
	
	public static Date getParamHmsendD(String endDateStr) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(endDateStr);
		Calendar calender = Calendar.getInstance();
		
		calender.setTime(endDate);
		calender.set(Calendar.HOUR, 23);
		calender.set(Calendar.MINUTE, 59);
		calender.set(Calendar.SECOND, 59);
	    return	 endDate;
	
	}
	
	/***
	 * 
	 * 传入时间 
	 * 返回 传入时间23:59:59  2016-05-06 23:59:59 ;
	 * @param endDateStr
	 * @return
	 * @throws ParseException
	 */
	
	public static Date getParamHmsendD(Date date) throws ParseException {
		
		Calendar calender = Calendar.getInstance();
		
		calender.setTime(date);
		calender.set(Calendar.HOUR_OF_DAY, 23);
		calender.set(Calendar.MINUTE, 59);
		calender.set(Calendar.SECOND, 59);
	    return	 calender.getTime();
	
	}
	
	
	/***
	 * 
	 * 传入时间 
	 * 返回 传入时间的00:00:00  2016-05-06 00:00:00 ;
	 * @param endDateStr
	 * @return
	 * @throws ParseException
	 */
	
	public static Date getParamHmsStartD(Date date) throws ParseException {
		
		Calendar calender = Calendar.getInstance();
		
		calender.setTime(date);
		calender.set(Calendar.HOUR_OF_DAY, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
	    return	 calender.getTime();
	
	}
	
	/***
	 * 
	 * 传入时间字符串 2017-02-05 15:03
	 * 返回 传入的时间 ;
	 * @param endDateStr
	 * @return
	 * @throws ParseException
	 */
	
	public static Date getTDate(String dateStr) throws ParseException {
		
		//2017-02-0515:03
		//yyyy-MM-dd HH:mm:ss
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dateTime = df.parse(dateStr);
		
	    return	 dateTime;
	
	}

}
