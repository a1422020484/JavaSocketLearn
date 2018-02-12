package cn.home.test;

import java.text.ParseException;

import cn.saturn.web.utils.DateUtils;

public class DateUtilesTest {
	public static void main(String[] args) throws ParseException {
		System.out.println(DateUtils.getAddDayStr("2016-8-17", 0));
		System.out.println(DateUtils.getAddDayStr("2016-8-17", 1));
		
		System.out.println("tes,te".contains("t"));
	}
}
