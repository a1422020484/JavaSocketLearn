package cn.saturn.web.controllers.statistics.dao;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.saturn.web.utils.DateUtils;

public class DateParam {
	/**
	 * 获取时间范围查询 <br>
	 * eg: <br>
	 * getParam("2016-05-25", "2016-05-25"); <br>
	 * return LIKE "2016-05-25%"<br>
	 * getParam("2016-05-26", "2016-05-24"); <br>
	 * return @时间错误<br>
	 * getParam("2016-05-25", "2016-05-26"); <br>
	 * return BETWEEN "2016-05-25" AND "2016-05-27" <br>
	 * 
	 * @param sDateStr
	 * @param eDateStr
	 * @return
	 * @throws ParseException
	 */
	public String getParam(String sDateStr, String eDateStr) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = df.parse(sDateStr);
		Date eDate = df.parse(eDateStr);
		Calendar calender = Calendar.getInstance();
		long dTime = eDate.getTime() - sDate.getTime();

		if (dTime < 0) {
			return "@时间错误";
		} else if (dTime == 0) {
			calender.setTime(eDate);
			calender.set(Calendar.HOUR, 24);
			return MessageFormat.format(" BETWEEN \"{0}\" AND \"{1}\"", DateUtils.format(sDate),
					DateUtils.format(calender.getTime()));
 
//			return MessageFormat.format(" LIKE \"{0}%\"", DateUtils.format(sDate),
//					DateUtils.format(calender.getTime()));
		} else if (dTime > 0) {
			calender.setTime(eDate);
			calender.set(Calendar.HOUR, 24);
			return MessageFormat.format(" BETWEEN \"{0}\" AND \"{1}\"", DateUtils.format(sDate),
					DateUtils.format(calender.getTime()));
		}

		return "";
	}
	
	
	/**
	 * 获取时间范围查询 <br>
	 * eg: <br>
	 * getParam("2016-05-25 ", "2016-05-25 "); <br>
	 * return BETWEEN "2016-05-25 00:00:00" AND "2016-05-25 23:59:59" <br>
	 * getParam("2016-05-26", "2016-05-24"); <br>
	 * return @时间错误<br>
	 * getParam("2016-05-25", "2016-05-26"); <br>
	 * return BETWEEN "2016-05-25 00:00:00" AND "2016-05-26 23:59:59 " <br>
	 * 
	 * @param sDateStr
	 * @param eDateStr
	 * @return
	 * @throws ParseException
	 */
	public String getParamHms(String sDateStr, String eDateStr) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = df.parse(sDateStr);
		Date eDate = df.parse(eDateStr);
		Calendar calender = Calendar.getInstance();
		long dTime = eDate.getTime() - sDate.getTime();

		if (dTime < 0) {
			return "@时间错误";
		} else if (dTime == 0) {
			calender.setTime(eDate);
			calender.set(Calendar.HOUR, 23);
			calender.set(Calendar.MINUTE, 59);
			calender.set(Calendar.SECOND, 59);
			return MessageFormat.format(" BETWEEN \"{0}\" AND \"{1}\"", DateUtils.formatHms(sDate),
					DateUtils.formatHms(calender.getTime()));
 
//			return MessageFormat.format(" LIKE \"{0}%\"", DateUtils.format(sDate),
//					DateUtils.format(calender.getTime()));
		} else if (dTime > 0) {
			calender.setTime(eDate);
			calender.set(Calendar.HOUR, 23);
			calender.set(Calendar.MINUTE, 59);
			calender.set(Calendar.SECOND, 59);
			return MessageFormat.format(" BETWEEN \"{0}\" AND \"{1}\"", DateUtils.formatHms(sDate),
					DateUtils.formatHms(calender.getTime()));
		}

		return "";
	}
	

	/**
	 * 获取时间范围查询 <br>
	 * eg: <br>
	 * getParamHms(2016-05-25 00:00:00 , 2016-05-27 20:00:00 ); <br>
	 * return BETWEEN "2016-05-25 00:00:00" AND "2016-05-27 19:59:59" <br>
	 * getParam(2016-05-25 00:00:00 , 2016-05-24 20:00:00 ); <br>
	 * return @时间错误<br>
	 * 
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public String getParamHmsD(Date startTime, Date endTime) throws ParseException {
		
		long dTime = endTime.getTime() - startTime.getTime();
		Date endTime1= DateUtils.cutSecond(endTime, -1);
		if (dTime < 0) {
			return "@时间错误";
		} else if (dTime > 0) {
			return MessageFormat.format(" BETWEEN \"{0}\" AND \"{1}\"", DateUtils.formatHms(startTime),DateUtils.formatHms(endTime1));
		}
		return "";
	}
	
	

	/**
	 * 获取查询时间段<br>
	 * eg<br>
	 * getParam("2016-05-25", "2016-05-25"); <br>
	 * "(SELECT "2016-05-25" as t_time )"<br>
	 * getParam("2016-05-26", "2016-05-24"); <br>
	 * return @时间错误<br>
	 * getParam("2016-05-25", "2016-05-26"); <br>
	 * "(SELECT "2016-05-25" as t_time ) UNION (SELECT "2016-05-26" as t_time )"
	 * <br>
	 * 
	 * @param sDateStr
	 * @param eDateStr
	 * @return
	 * @throws ParseException
	 */
	public String getQuery(String sDateStr, String eDateStr) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calender = Calendar.getInstance();
		StringBuilder strB = new StringBuilder();
		Date sDate = df.parse(sDateStr);
		Date eDate = df.parse(eDateStr);
		calender.setTime(sDate);

		long dx = (int) ((eDate.getTime() - sDate.getTime()) / (1000 * 60 * 60 * 24));

		if (dx < 0) {
			return "@日期选择错误";
		}
		dx += 1;
		for (int i = 0; i < dx; i++) {
			strB.append("(SELECT \"" + df.format(calender.getTime()) + "\" as t_time )");
			strB.append(i < dx - 1 ? " UNION " : "");
			calender.add(Calendar.DAY_OF_YEAR, 1);
		}

		return strB.toString();
	}

	/**
	 * 如果包含 @ return true<br>
	 * 如果不包含 @ return false<br>
	 * 
	 * @param result
	 * @return
	 */
	public boolean isError(String result) {
		return result.contains("@");
	}
	
}
