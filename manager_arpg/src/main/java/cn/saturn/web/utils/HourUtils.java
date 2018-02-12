package cn.saturn.web.utils;

import java.util.Calendar;
import java.util.Date;

import sun.util.resources.nl.CalendarData_nl;

/**
 * 小时之差工具 <br>
 * "yyyy-MM-dd HH"<br>
 * 
 * @author rodking
 *
 */
public class HourUtils {

	private static final int H = 1000 * 60 * 60;

	/**
	 * 返回今天的 hour long <br>
	 * 
	 * @return
	 */
	public static long getNowHour() {
		return getHour(System.currentTimeMillis());
	}

	/**
	 * 将毫秒转化为小时 <br>
	 * 
	 * @param millis
	 * @return
	 */
	public static long getHour(long millis) {
		return millis / H;
	}

	public static void main(String[] args) {

		Calendar calender = Calendar.getInstance();
		calender.setTime(new Date());
		System.out.println(calender.getTimeInMillis() / (1000 * 60));
		System.out.println(getNowHour());
		System.out.println(calender.get(Calendar.HOUR_OF_DAY));
	}
}
