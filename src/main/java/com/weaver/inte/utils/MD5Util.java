package com.weaver.inte.utils;

import java.security.MessageDigest;

/**
 * @description 
 * @author lzy
 * @date:2020年4月28日 上午10:31:48
 * @version v1.0
 */
public class MD5Util {

	public static final String md5(String info) throws Exception {
		return md5(info.getBytes("UTF-8"));
	}

	public static final String md5(byte[] bytes) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] md5Array = null;
		synchronized (MD5Util.class) {
			md5.update(bytes);
			md5Array = md5.digest();
		}
		return bytesToHex(md5Array);
	}

	private static String bytesToHex(byte[] b) {
		StringBuffer sb = new StringBuffer("");
		int d;
		for (int i = 0; i < b.length; i++) {
			d = b[i];
			if (d < 0) {
				d = b[i] & 0xff;
			}
			if (d < 16)
				sb.append("0");
			sb.append(Integer.toHexString(d));
		}
		return sb.toString();
	}
}
