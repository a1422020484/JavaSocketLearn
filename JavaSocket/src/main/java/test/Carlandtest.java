package test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Carlandtest {
	
	static {
		System.out.println("Carlandtest init");
	}
	
	public static final String HelloWorld = "HelloWorld";
	
	public static void main(String[] args) {
		GregorianCalendar gc = new GregorianCalendar();
		final char[] kor_week = { '1', '2', '3', '4', '5', '6', '7' };
		for (int i = 2007; i <= 2020; i++) {
			gc.set(i, Calendar.JULY + 1, 20);
//			gc.set(i, 7, 20);
			char week = kor_week[gc.get(Calendar.DAY_OF_WEEK) - 1];
			System.out.println(i + "年的生日是星期" + week);
		}
		
		java.util.Date date = new java.util.Date();
		java.sql.Date date2 = new java.sql.Date(1565665484300L);
		
		System.out.println(date.getDay());
		System.out.println(date2.getDay());
	}

}
