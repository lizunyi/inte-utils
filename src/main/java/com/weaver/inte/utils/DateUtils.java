package com.weaver.inte.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static final ThreadLocal<SimpleDateFormat> local = new ThreadLocal<>();

	public static final String getTime(Date d) {
		long timers = d.getTime() / 1000L;
		long ss = timers % 60L;
		long mm = timers / 60L % 60L;
		long hh = timers / 3600L % 24L + 8;
		return String.format("%s:%s:%s", zero(hh), zero(mm), zero(ss));
	}

	public static final long getTimeByNumber(Date d) {
		long timers = d.getTime() / 1000L;
		long ss = timers % 60L;
		long mm = timers / 60L % 60L;
		long hh = timers / 3600L % 24L + 8;
		return Long.parseLong(String.format("%s%s%s", hh, zero(mm), zero(ss)));
	}

	public static final String getTime2(Date d) {
		long timers = d.getTime() / 1000L % 86400L;
		long ss = timers % 60L;
		long mm = timers / 60L % 60L;
		long hh = timers / 3600L + 8;
		return String.format("%s:%s:%s", zero(hh), zero(mm), zero(ss));
	}

	public static final Date getNowDate() {
		return new Date(System.currentTimeMillis() / 86400000L * 86400000L - 28800000L);
	}

	public static final String formatNowDate() {
		Date d = getNowDate();
		return format(d, "yyyy-MM-dd");
	}

	public static final String format(Date d, String pattern) {
		local.set(new SimpleDateFormat(pattern));
		return ((SimpleDateFormat) local.get()).format(d);
	}

	public static final Date parse(String d, String pattern) {
		try {
			local.set(new SimpleDateFormat(pattern));
			return ((SimpleDateFormat) local.get()).parse(d);
		} catch (ParseException parseException) {
			return null;
		}
	}

	private static final String zero(long n) {
		return n < 10 ? "0" + n : String.valueOf(n);
	}

	public static final Date addHour(int hour) {
		Date d = new Date();
		return new Date(d.getTime() + hour * 3600000);
	}

	public static final Date addMin(int min) {
		Date d = new Date();
		return new Date(d.getTime() + min * 60000);
	}

	/**
	 * Methods Descrip:按指定格式转换日期字符串为日期对象,如果解析失败,返回null
	 *
	 * @param date:日期字符串
	 * @param pattern:指定的日期格式
	 * @return:Date 日期
	 */
	public static Date parseDate(String date, String pattern) {
		if (date == null)
			return null;

		try {
			DateFormat parser = new SimpleDateFormat(pattern);
			return parser.parse(date);
		} catch (ParseException ex) {
		}

		return null;
	}
}
