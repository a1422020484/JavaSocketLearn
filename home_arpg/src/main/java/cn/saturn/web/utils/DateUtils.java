package cn.saturn.web.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static Date getDaysAgo(int days) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(new Date());
		calender.add(Calendar.DATE, -days);
		return calender.getTime();
	}
	
	public static String format(Date date) {
		return getDateFormat().format(date);
	}
	
	public static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}
	

}
