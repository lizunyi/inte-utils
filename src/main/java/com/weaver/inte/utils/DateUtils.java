package com.weaver.inte.utils;

import java.util.Date;

/**
 * @description 
 * @author lzy
 * @date:2020年4月28日 上午10:31:48
 * @version v1.0
 */
public class DateUtils {

	public static final String getTime(Date d) {
  		long timers = d.getTime() / 1000;
  		long ss = timers % 60;//秒
  		long mm = timers / 60 % 60;//分
  		long hh = timers / 3600 % 24;//时
  		
  		return String.format("%s:%s:%s", hh,mm,ss);
	}

	public static final String getTime2(Date d) {
  		long timers = d.getTime() / 1000 % 86400;
		long ss = timers % 60;//秒
		long mm = timers / 60 % 60;//分
		long hh = timers / 3600;//时
  		
  		return String.format("%s:%s:%s", hh,mm,ss);
	}

}
