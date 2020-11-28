package com.vma.push.business.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public final static String PATTERN_24TIME = "yyyy-MM-dd HH:mm:ss";
	public final static String PATTERN_DATE = "yyyy-MM-dd";
	public final static String PATTERN_TIMESTAMP = "yyyyMMddHHmmss";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMdd");

	/**
	 * 根据传入的模式参数返回当天的日期
	 * 
	 * @param pattern
	 *            传入的模式
	 * @return 按传入的模式返回一个字符串
	 */
	public static String getToday(String pattern) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static String getCurrentMonday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
//		DateFormat df = DateFormat.getDateInstance();
		String preMonday = dateFormat.format(monday);
		return preMonday;
	}

	// 获得当前周- 周日 的日期
	public static String getPreviousSunday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
//		DateFormat df = DateFormat.getDateInstance();
		String preMonday = dateFormat.format(monday);
		return preMonday;
	}

	// 获得本周一与当前日期相差的天数
	public static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}

	// 获得当前月--开始日期
	public static String getMinMonthDate(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(date));
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			return dateFormat.format(calendar.getTime());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获得当前月--结束日期
	public static String getMaxMonthDate(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(date));
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			return dateFormat.format(calendar.getTime());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.err.println(getCurrentMonday());
		System.err.println(getPreviousSunday());
	}

}
