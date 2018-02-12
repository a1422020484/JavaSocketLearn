package cn.saturn.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//import javax.annotation.concurrent.ThreadSafe;

/**
 * 天数日期工具 <br>
 * "yyyy-MM-dd"<br>
 * 
 * @author rodking
 *
 */
//@ThreadSafe
public class DateUtils {

	/**
	 * 返回昨天 String <br>
	 * "yyyy-MM-dd"
	 * 
	 * @return
	 */
	public static String getYesterdayStr() {
		Calendar calender = Calendar.getInstance();
		calender.setTime(new Date());
		calender.add(Calendar.DATE, -1);
		return format(calender.getTime());
	}
	
	/**
	 * 返回传入时间的 String <br>
	 * "yyyy-MM-dd"
	 * 
	 * @return
	 */
	public static String getDayStr(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		return format(calender.getTime());
	}
	
	/**
	 * 返回一个月以前 String <br>
	 * "yyyy-MM-dd"
	 * 
	 * @return
	 */
	
	public static String getMonthAgoStr() {
		Calendar calender = Calendar.getInstance();
		calender.setTime(new Date());
		calender.add(Calendar.DATE, -30);
		return format(calender.getTime());
	}
	

	/**
	 * 返回明天 String <br>
	 * "yyyy-MM-dd"
	 * 
	 * @return
	 */
	public static String getTomorrowStr() {
		Calendar calender = Calendar.getInstance();
		calender.setTime(new Date());
		calender.add(Calendar.DATE, 1);
		return format(calender.getTime());
	}

	/**
	 * 返回增加后的天数 <br>
	 * addDay 正数 +addDay<br>
	 * addDay 负数 -addDay<br>
	 * 
	 * @param day
	 * @param addDay
	 * @return
	 * @throws ParseException
	 */
	public static String getAddDayStr(String day, int addDay) throws ParseException {
		Calendar calender = Calendar.getInstance();
		Date data = getDateFormat().parse(day);
		calender.setTime(data);
		calender.add(Calendar.DATE, addDay);
		return format(calender.getTime());
	}
	
	/**
	 * 返回增加后的天数 <br>
	 * addDay 正数 +addDay<br>
	 * addDay 负数 -addDay<br>
	 * 
	 * @param day
	 * @param addDay
	 * @return
	 * @throws ParseException
	 */
	public static Date getAddDay(Date date, int addDay) throws ParseException {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.DATE, addDay);
		return calender.getTime();
	}
	
	/**
	 * 返回增加后天数的00：00：00 <br>
	 * addDay 正数 +addDay<br>
	 * addDay 负数 -addDay<br>
	 * 
	 * @param day
	 * @param addDay
	 * @return
	 * @throws ParseException
	 */
	public static Date getAddDayZeroPoint(Date date, int addDay) throws ParseException {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.DATE, addDay);
		calender.set(Calendar.HOUR_OF_DAY, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		return calender.getTime();
	}
	
	
	/**
	 * 返回增加后天数的23:59:59 <br>
	 * addDay 正数 +addDay<br>
	 * addDay 负数 -addDay<br>
	 * 
	 * @param day
	 * @param addDay
	 * @return
	 * @throws ParseException
	 */
	public static Date getAddDayLastTime(Date date, int addDay) throws ParseException {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.DATE, addDay);
		calender.set(Calendar.HOUR_OF_DAY, 23);
		calender.set(Calendar.MINUTE, 59);
		calender.set(Calendar.SECOND, 59);
		return calender.getTime();
	}
	
	
	/**
	 * 返回传入Date的00:00:00 <br>
	 * addDay 正数 +addDay<br>
	 * addDay 负数 -addDay<br>
	 * 
	 * @param day
	 * @param addDay
	 * @return
	 * @throws ParseException
	 */
	public static Date getZeroPoint(Date date) throws ParseException {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.set(Calendar.HOUR_OF_DAY, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		calender.set(Calendar.MILLISECOND, 0);
		return calender.getTime();
	}
	
	

	/**
	 * 返回2天之差 <br>
	 * 
	 * @param endDay
	 * @param startDay
	 * @return
	 * @throws ParseException
	 */
	public static long daysBetween(String endDay, String startDay) throws ParseException {
		Date sData = getDateFormat().parse(startDay);
		Date eData = getDateFormat().parse(endDay);
		long daysBetween = (eData.getTime() - sData.getTime() + 1000000) / (3600 * 24 * 1000);
		return daysBetween;
	}
	
	/**
	 * 返回2天之差 <br>
	 * 
	 * @param endDay
	 * @param startDay
	 * @return
	 * @throws ParseException
	 */
	public static long daysBetween(Date endDay, Date startDay) throws ParseException {
		long daysBetween = (endDay.getTime() - startDay.getTime() + 1000000) / (3600 * 24 * 1000);
		return daysBetween;
	}

	/**
	 * 返回2天之差 <br>
	 * 
	 * @param endDay
	 * @param startDay
	 * @return
	 * @throws ParseException
	 */
	public static long daysBetween(int endDay, int startDay) {
		long daysBetween = (endDay - startDay) / (3600 * 24);
		return daysBetween;
	}

	/**
	 * 返回今天的 String <br>
	 * 
	 * @return
	 */
	public static String getNowDay() {
		Calendar calender = Calendar.getInstance();
		calender.setTime(new Date());
		return getDateFormat().format(calender.getTime());
	}

	public static Date parse(String strDate) throws ParseException {
		return getDateFormat().parse(strDate);
	}

	public static String format(Date date) {
		return getDateFormat().format(date);
	}
	
	public static String formatHms(Date date) {
		return getDateTimeFormatH().format(date);
	}
	
	public static String formatDateTime(Date date) {
		return getDateTimeFormat().format(date);
	}

	public static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}
	
	public static SimpleDateFormat getDateTimeFormat() {
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	}
	
	public static SimpleDateFormat getDateTimeFormatH() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取当前时间的减少1秒的时间
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date cutSecond(Date date, int second) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, second);
		return c.getTime();
	}
	
	/**
	 * 获取当前时间的前一个小时时间
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, hour);
		return c.getTime();
	}
	
	
	
	/**
	 * 获取传入时间的整点+1分时间
	 * @param date
	 * @return
	 */
	public static Date getPointHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 判断是否大于当天 <br>
	 * "yyyy-MM-dd"
	 * 
	 * @return
	 */
	public static boolean isAfterToday(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.set(Calendar.HOUR_OF_DAY, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		calender.set(Calendar.MILLISECOND, 0);
		return new Date(date.getTime()+1000).after(calender.getTime());
	}
	
	/**
	 * 判断是否小于当天
	 * @param date
	 * @return
	 */
	public static boolean isBeforeToday(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.set(Calendar.HOUR_OF_DAY, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		calender.set(Calendar.MILLISECOND, 0);
		return new Date(date.getTime()).before(calender.getTime());
	}
}
