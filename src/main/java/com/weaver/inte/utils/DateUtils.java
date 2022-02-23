package com.weaver.inte.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private static final String getTime(String pattern, Date d) {
		long timers = d.getTime() / 1000;
		long ss = timers % 60;
		long mm = timers / 60 % 60;
		long hh = timers % 86400 / 3600 + 8;
		return String.format(pattern, zero(hh), zero(mm), zero(ss));
	}

	public static final String getTime(Date d) {
		return getTime("%s:%s:%s", d);
	}

	public static final long getTimeByNumber(Date d) {
		return Long.parseLong(getTime("%s%s%s", d));
	}

	public static final Date getNowDate() {
		return new Date(System.currentTimeMillis() / 86400000L * 86400000L - 28800000L);
	}


	public static final String formatNowDate() {
		Date d = getNowDate();
		return format(d, "yyyy-MM-dd");
	}

	public static final String formatNow() {
		return format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	public static final String format(Date d, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}

	public static final Date parse(String d, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(d);
		} catch (ParseException e) {

		}
		return null;
	}

	private static final String zero(long n) {
		return n < 10 ? "0" + n : String.valueOf(n);
	}
}
