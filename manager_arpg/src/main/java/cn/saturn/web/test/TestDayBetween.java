package cn.saturn.web.test;

import java.text.ParseException;

import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.WebUtils;

public class TestDayBetween {
	public static void main(String[] args) throws ParseException {
		System.out.println(DateUtils.daysBetween("2016-06-15", "2016-06-14"));

		System.out.println(
				DateUtils.daysBetween(WebUtils.Html.getDate0("2016-06-15"), WebUtils.Html.getDate0("2016-06-14")));

	}
}
