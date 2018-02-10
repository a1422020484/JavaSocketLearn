package nettyServer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具
 * 
 * @author xiezuojie
 */
public class DateUtil {

	/**
	 * HH:mm:ss
	 */
	public static final String Pattern_HH_mm = "HH:mm";
	/**
	 * HH:mm:ss
	 */
	public static final String Pattern_HH_mm_ss = "HH:mm:ss";
	/**
	 * MM-dd
	 */
	public static final String Pattern_MM_dd = "MM-dd";
	/**
	 * yyyy-MM-dd
	 */
	public static final String Pattern_yyyy_MM_dd = "yyyy-MM-dd";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String Pattern_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	
	//
	public static long msOffset = 0L;
	
	public static long getMsOffset() {
		return msOffset;
	}

	public static void setMsOffset(long msOffset) {
		DateUtil.msOffset = msOffset;
	}
	
	/**
	 * 获取当地时期当前的日期
	 *  @return
	 */
	public static Calendar getCalendar(){
		Calendar calendar = Calendar.getInstance();
		if( 0L != getMsOffset() ){
			calendar.setTimeInMillis(calendar.getTimeInMillis() + getMsOffset());
		}
		return calendar;
	}
	public static long getMillisecond(){
	    return getCalendar().getTimeInMillis() + getMsOffset();
	}
	
	/**
	 * 一天的毫秒数
	 */
	public static final long MillisecondOfDay = 24 * 60 * 60 * 1000;
	/**
	 * 一天的秒数
	 */
	public static final long SecondsOfDay = 24 * 60 * 60;
	
	/**
	 * 一小时的毫秒数
	 */
	public static final long MillisecondOfHour = 60 * 60 * 1000;

	public static final long MillisecondOfMinute = 60 * 1000;
	
	/**
	 * @param dateTimeInMillis 日期毫秒值
	 * @return 指定日期当天的凌晨零点
	 */
	public static long getMidnight(long dateTimeInMillis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(dateTimeInMillis);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}
	
	/**
	 * 获取只精确到分的毫秒值
	 * @param dateTimeInMillis
	 * @return
	 */
	public static long getMinTimeInMillis(long dateTimeInMillis){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(dateTimeInMillis);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}

	/**
	 * @return 今天是星期几
	 */
	public static int todayOfWeek() {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(new Date());
		int today = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (today == 0)
			today = 7;
		return today;
	}

	/**
	 * 判断是否是星期几,参数dayOfWeek是指一周内的某一天,星期一为周内第1天,范围是1~7
	 * 
	 * @param dayOfWeek
	 *            星期几
	 * @return 是否是星期几
	 */
	public static boolean isDayOfWeek(int dayOfWeek) {
		if (dayOfWeek < 1 || dayOfWeek > 7)
			return false;
		return todayOfWeek() == dayOfWeek;
	}

	/**
	 * @param timeInMillis
	 *            时间(毫秒)
	 * @return 获取指定时间的小时
	 */
	public static int hour(long timeInMillis) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(timeInMillis);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * @return 获取当前系统时间的小时
	 */
	public static int cHour() {
		return hour(System.currentTimeMillis());
	}

	/**
	 * @return 获取当前系统时间在一年内的天
	 */
	public static int cDay() {
		return day(System.currentTimeMillis());
	}

	/**
	 * @param timeInMillis
	 *            时间(毫秒)
	 * @return 获取指定时间一年内的某天
	 */
	public static int day(long timeInMillis) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(timeInMillis);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 判断指定时间(毫秒)与当前系统时间是否在一年内的同一天内
	 * 
	 * @param timeInMillis
	 *            时间(毫秒)
	 * @return 指定时间(毫秒)与当前系统时间是否在一年内的同一天内
	 */
	public static boolean isSameDay(long timeInMillis) {
		return cDay() == day(timeInMillis);
	}

	/**
	 * 判断两个指定时间(毫秒)是否在同一天内
	 * 
	 * @param t1
	 * @param t2
	 * @return 两个时间(毫秒)是否在同一天内
	 */
	public static boolean isSameDay(long t1, long t2) {
		if (Math.abs(t1 - t2) >= MillisecondOfDay) {
			return false;
		}

		return day(t1) == day(t2);
	}

	/**
	 * 星期一为周第1天
	 * 
	 * @return 获取当前系统时间在一年内的周
	 */
	public static int cWeek() {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(new Date());
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 星期一为周第1天
	 * 
	 * @param timeInMillis
	 *            时间(毫秒)
	 * @return 获取指定时间在一年内的周
	 */
	public static int week(long timeInMillis) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTimeInMillis(timeInMillis);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 星期一为周第1天<br/>
	 * 判断指定时间(毫秒)与当前系统时间是否在一年内的同一周内
	 * 
	 * @param timeInMillis
	 *            时间(毫秒)
	 * @return 指定时间(毫秒)与当前系统时间是否在一年内的同一周内
	 */
	public static boolean isSameWeek(long timeInMillis) {
		return cWeek() == week(timeInMillis);
	}

	/**
	 * @return 获取当前系统时间在一年内的月,1~12
	 */
	public static int cMonth() {
		return month(System.currentTimeMillis());
	}

	/**
	 * @param timeInMillis
	 *            时间(毫秒)
	 * @return 获取指定时间在一年内的月,1~12
	 */
	public static int month(long timeInMillis) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(timeInMillis);
		return calendar.get(Calendar.MONTH) + 1; // 第一个月获得的值是0
	}

	/**
	 * 判断指定时间(毫秒)与当前系统时间是否在一年内的同一月内,如果跨年,返回false
	 * 
	 * @param timeInMillis
	 *            时间(毫秒)
	 * @return 指定时间(毫秒)与当前系统时间是否在一年内的同一月内,如果跨年,返回false
	 */
	public static boolean isSameMonth(long timeInMillis) {
		return isSameYear(timeInMillis)
				&& cMonth() == month(timeInMillis);
	}

	/**
	 * @return 获取当前系统时间的年份
	 */
	public static int cYear() {
		return year(System.currentTimeMillis());
	}

	/**
	 * @param timeInMillis
	 *            时间(毫秒)
	 * @return 获取指定时间的年份
	 */
	public static int year(long timeInMillis) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(timeInMillis);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 判断指定时间(毫秒)与当前系统时间是否在一年内
	 * 
	 * @param timeInMillis
	 *            时间(毫秒)
	 * @return 指定时间(毫秒)与当前系统时间是否在一年内
	 */
	public static boolean isSameYear(long timeInMillis) {
		return cYear() == year(timeInMillis);
	}

	/**
	 * @return 当前时间在小时内的分钟,例:10:04:15,那么返回04
	 */
	public static int cMinute() {
		return minute(System.currentTimeMillis());
	}

	/**
	 * @param timeInMillis
	 *            时间(毫秒)
	 * @return 时间在小时内的分钟,例:10:04:15,那么返回04
	 */
	public static int minute(long timeInMillis) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(timeInMillis);
		return calendar.get(Calendar.MINUTE);
	}
	
	/**
	 * @param d1
	 * @param d2
	 * @return 两个日期的小时差
	 */
	public static int hourDiff(Date d1, Date d2) {
		long l1 = d1.getTime();
		long l2 = d2.getTime();
		long d = Math.abs(l2 - l1);
		int h = (int) (d / 3600000);
		return h;
	}
	
	/**
	 * 
	 * @param l1 时间戳(小)
	 * @param l2 时间戳(大)
	 * @return 两个时间的天数差,如果l2>l1,那么是正值,否则是负值
	 */
	public static int dayDiff(long l1, long l2) {
		long t = l2 - l1;
		int d = (int) (t / MillisecondOfDay);
		return d;
	}
	
	/**
	 * 
	 * @param timeFormat 时间字符串
	 * @param pattern 时间格式
	 * @return 取指定时间的'时,分,秒'在当天的时间,返回当前时间到下一个指定时间之间的时间差(秒),如果当前时间已经过了指定时间,那么计算与第二天的时间差
	 */
	public static int secondsDiffToNextTime(String timeFormat, String pattern) {
		Date date = parse(timeFormat, pattern);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		Calendar cc = Calendar.getInstance();
		cc.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
		cc.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
		cc.set(Calendar.SECOND, c.get(Calendar.SECOND));
		
		long time = cc.getTimeInMillis();
		long now = System.currentTimeMillis();
		
		long diff = time - now;
		if (diff < 0L) {
			diff += MillisecondOfDay;
		}
		
		return (int) (diff / 1000);
	}

	/**
	 * 例:当天是2016-06-06, 参数timeFormat是08:09:10(时,分,秒),那么返回的是2016-06-06 08:09:10的毫秒值
	 * @param timeFormat 时间字符串
	 * @param pattern 时间格式
	 * @return 取指定时间的'时,分,秒'在当天的时间,返回毫秒值
	 */
	public static long todayTimeMillis(String timeFormat, String pattern) {
		Date date = parse(timeFormat, pattern);
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		Calendar cc = Calendar.getInstance();
		cc.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
		cc.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
		cc.set(Calendar.SECOND, c.get(Calendar.SECOND));

		return cc.getTimeInMillis();
	}

	/**
	 *
	 * @param timeFormat 时间字符串
	 * @param pattern 时间格式
	 * @return 取指定时间的'时,分,秒'在当天的时间,返回指定时间的下一次时间点(毫秒),如果当前时间已经过了指定时间,将指定时间延长一天
	 */
	public static long nextTimeMillis(String timeFormat, String pattern) {
		Date date = parse(timeFormat, pattern);
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		Calendar cc = Calendar.getInstance();
		cc.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
		cc.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
		cc.set(Calendar.SECOND, c.get(Calendar.SECOND));

		long time = cc.getTimeInMillis();
		long now = System.currentTimeMillis();
		if (time > now) {
			return time;
		} else {
			return time + MillisecondOfDay;
		}
	}

    /**
     * 将当天内指定时,分,秒的时间转换为时间戳
     * @param hour 时,0~23
     * @param min  分
     * @param sec  秒
     * @return 时间戳(毫秒)
     */
    public static long toTimeMillis(int hour, int min, int sec) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, sec);
        return c.getTimeInMillis();
    }

    /**
	 * 
	 * @param timeInMillis
	 * @param begin
	 *            起始时间格式:'HH:mm:ss'
	 * @param end
	 *            结束时间格式:'HH:mm:ss'
	 * @return 指定时间是否在指定时间范围内
	 */
	public static boolean isInTimeRule(long timeInMillis, String begin, String end) {
		DateFormat format = DateFormatFactory.get(Pattern_HH_mm_ss);
		try {
			Date d1 = format.parse(begin);
			Date d2 = format.parse(end);
			/*
			 * 起始时间 <= 当前时间 <= 结束时间 只格式化HH:mm:ss获得的日期的年份和月份是空的,将使用默认值1970-01
			 * 因此,获得当前日期,将年月日设置成1970-01-01即可
			 */
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(timeInMillis);
			c.set(Calendar.YEAR, 1970);
			c.set(Calendar.MONTH, Calendar.JANUARY);
			c.set(Calendar.DAY_OF_MONTH, 1);
			return d1.getTime() <= c.getTimeInMillis()
					&& c.getTimeInMillis() <= d2.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param d
	 * @param pattern
	 * @return
	 */
	public static String format(Date d, String pattern) {
		try {
			DateFormat format = DateFormatFactory.get(pattern);
			return format.format(d);
		} catch (Exception e) {
			e.printStackTrace();
			return "unknown";
		}
	}
	
	/**
	 * 使用默认格式将字符串格式化为日期<br>
	 * {@link DateUtil#Pattern_yyyy_MM_dd_HH_mm_ss}
	 * 
	 * @param formatString 表示日期格式的字符串
	 * @return 格式化后的日期
	 */
	public static Date parse(String formatString) {
		return parse(formatString, Pattern_yyyy_MM_dd_HH_mm_ss);
	}
	
	/**
	 * 使用指定格式将字符串格式化为日期
	 * 
	 * @param formatString 表示日期格式的字符串
	 * @param pattern 日期格式
	 * @return 格式化后的日期
	 */
	public static Date parse(String formatString, String pattern) {
		try {
			DateFormat format = DateFormatFactory.get(pattern);
			return format.parse(formatString);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(formatString + "不匹配指定格式:" + pattern);
		}
	}
	
	public static long add(int year, int month, int day, int hour, int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, year);
    	c.add(Calendar.MONTH, month);
    	c.add(Calendar.DAY_OF_MONTH, day);
    	c.add(Calendar.HOUR_OF_DAY, hour);
		c.add(Calendar.MINUTE, minute);
		c.add(Calendar.SECOND, second);
		
		return c.getTimeInMillis();
	}
	
	
	public static void main(String[] args){
		String todayStr = DateUtil.format(new Date(), "yyyy-MM-dd");
		System.out.println(todayStr);
		System.out.println(DateUtil.parse(todayStr+ " " + "0:00:00", "yyyy-MM-dd HH:mm:ss"));
	}
}
