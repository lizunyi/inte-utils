package com.weaver.inte.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public final static String fmtDateTime = "yyyy-MM-dd HH:mm:ss";
    public final static String fmtDateMinutes = "yyyy-MM-dd HH:mm";
    public final static String fmtDate = "yyyy-MM-dd";


    public static final String getTime(Date d) {
        return getTime("%s:%s:%s", d);
    }

    private static final String getTime(String pattern, Date d) {
        long timers = d.getTime() / 1000;
        long ss = timers % 60;
        long mm = timers / 60 % 60;
        long hh = (timers / 3600 + 8) % 24;
        return String.format(pattern, zero(hh), zero(mm), zero(ss));
    }

    public static final long getTimeByNumber(Date d) {
        return Long.parseLong(getTime("%s%s%s", d));
    }

    public static final Date parseDate() {
        return new Date(System.currentTimeMillis() / 86400000L * 86400000L - 28800000L);
    }

    public static final Date parseDate(String dateStr) {
        return parse(dateStr, fmtDate);
    }

    public static final Date parseDateTime(String dateStr) {
        return parse(dateStr, fmtDateTime);
    }

    public static final Date parseDate(Date dateTime) {
        return new Date(dateTime.getTime() / 86400000L * 86400000L - 28800000L);
    }

    public static final String format(Date d, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(d);
    }

    public static final String formatDate() {
        Date d = parseDate();
        return format(d, fmtDate);
    }

    public static final String formatDate(Date d) {
        return format(d, fmtDate);
    }

    public static final String formatDateTime() {
        return format(new Date(), fmtDateTime);
    }

    public static final String formatDateTime(Date d) {
        return format(d, fmtDateTime);
    }

    public static final String fmtDateMinutes() {
        return format(new Date(), fmtDateTime);
    }

    public static final String fmtDateMinutes(Date d) {
        return format(d, fmtDateTime);
    }

    public static final Date parse(String d, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(d);
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

    public static final Date addHour(Date d, int hour) {
        return new Date(d.getTime() + hour * 3600000);
    }

    public static final Date addMin(int min) {
        Date d = new Date();
        return new Date(d.getTime() + min * 60000);
    }

    public static final Date addMin(Date d, int min) {
        return new Date(d.getTime() + min * 60000);
    }
}

