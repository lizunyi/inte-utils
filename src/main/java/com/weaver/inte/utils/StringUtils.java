package com.weaver.inte.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/***
 * 
 * @author saps.weaver
 *
 */
public class StringUtils {
	public final static String empty = "";

	public static String ifNull(Object obj, String defaultValue) {
		if (isNull(obj)) {
			return defaultValue;
		}
		return obj.toString();
	}

	public static String ifNull(Object obj) {
		return ifNull(obj, empty);
	}

	public static long ifLongNull(Object obj, long defaultValue) {
		if (isNull(obj)) {
			return defaultValue;
		}
		return Long.parseLong(obj.toString());
	}

	public static long ifLongNull(Object obj) {
		return ifLongNull(obj, 0);
	}

	public static double ifDoubleNull(Object obj, double defaultValue) {
		if (isNull(obj)) {
			return defaultValue;
		}
		return Double.parseDouble(obj.toString());
	}

	public static double ifDoubleNull(Object obj) {
		return ifDoubleNull(obj, 0);
	}

	public static Integer ifIntegerNull(Object arg) {
		if(isNull(arg)) {
			return null;
		}
		return Integer.parseInt(arg.toString());
	}

    public static BigDecimal ifBigDecimalNull(Object arg) {
    	if(isNull(arg)) {
    		return BigDecimal.ZERO;
    	}
    	if(arg instanceof BigDecimal){
    		return (BigDecimal) arg;
    	}
    	return new BigDecimal(arg.toString());
    }
    
	public static int ifIntNull(Object obj, int defaultValue) {
		if (isNull(obj)) {
			return defaultValue;
		}
		return Integer.parseInt(obj.toString());
	}

	public static int ifIntNull(Object obj) {
		return ifIntNull(obj, 0);
	}

	public static boolean isNull(Object obj) {
		if (obj == null || empty.equals(obj.toString())) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	public static boolean isempty(Object str) {
		return (str == null || empty.equals(str));
	}

	/***
	 * JSON->String
	 */
	public static String getStringByJson(JSONObject json, String key) {
		return ifNull(json.get(key));
	}
	public static Integer getIntegerByJson(JSONObject json,String key) {
		return ifIntegerNull(json.get(key));
	}
	public static BigDecimal getBigDecimalByJson(JSONObject json, String key) {
		return ifBigDecimalNull(json.get(key));
	}

	public static Long getLongByJson(JSONObject json,String key) {
		return ifLongNull(json.get(key));
	}

	public static JSONObject comm(JSONObject json, String... keys) {
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			if (json != null && json.containsKey(key) && json.get(key) != null) {
				if (i == keys.length - 1) {
					return json;
				} else {
					json = json.getJSONObject(key);
				}
			} else {
				break;
			}
		}
		return null;
	}

	public static String getStringByKeys(JSONObject json, String... keys) {
		JSONObject commResult = comm(json, keys);
		if (commResult != null) {
			String key = keys[keys.length - 1];
			return ifNull(commResult.get(key));
		}
		return empty;
	}

	public static String getStringByKeysInArrayFirst(JSONObject json, String... keys) {
		JSONArray array = getArrayByKeys(json, Arrays.copyOf(keys,  keys.length - 1));
		if (array != null && array.size() > 0) {
			JSONObject object = array.getJSONObject(0);
			String key = keys[keys.length - 1];
			return getStringByJson(object, key);
		}
		return empty;
	}

	public static JSONObject getObjectByKeysInArrayFirst(JSONObject json, String... keys) {
		JSONArray array = getArrayByKeys(json, keys);
		if (array != null && array.size() > 0) {
			JSONObject object = array.getJSONObject(0);
			return object;
		}
		return null;
	}

	public static Integer getIntegerByKeys(JSONObject json, String... keys) {
		JSONObject commResult = comm(json, keys);
		if (commResult != null) {
			String key = keys[keys.length - 1];
			return ifIntegerNull(commResult.get(key));
		}
		return null;
	}

	public static JSONObject getObjectByKeys(JSONObject json, String... keys) {
		JSONObject commResult = comm(json, keys);
		if (commResult != null) {
			String key = keys[keys.length - 1];
			return commResult.getJSONObject(key);
		}
		return null;
	}

	public static JSONArray getArrayByKeys(JSONObject json, String... keys) {
		JSONObject commResult = comm(json, keys);
		if (commResult != null) {
			String key = keys[keys.length - 1];
			return commResult.getJSONArray(key);
		}
		return null;
	}
	/***
	 * XML->String
	 */
	public static void main(String[] args) throws Exception {
		String xml = ReadWriteUtil.read("D://hotel-info.xml");
//		Pattern pat = Pattern.compile("<PhoneNo[^>]*(?=phoneType=\"InternalMobileTel\")[^>]*>([\\s\\S]*?)</PhoneNo>");
//		Matcher mac = pat.matcher(xml);
//		while (mac.find()) {
//			System.out.println(mac.group(0));
//		}
		//取对象
		System.out.println(getStringByTag(xml,"ReqHotel"));
		//取值
		System.out.println(getStringByTags(xml,"ReqHotel","HotelBaseInfo","HotelCode"));
		System.out.println(getStringByTags(xml,"ReqHotel","HotelBaseInfo","HotelName"));
		System.out.println(getStringByTags(xml,"ReqHotel","HotelBaseInfo","HotelAddress"));
		//多个标签取数组
		getArrayByTags(xml, new CharacterHandler() {
			@Override
			public void exec(String str) {
				System.out.println(str);
				System.out.println(getStringByTag(str,"AutoBus"));
			}
		},"ReqHotel", "HotelTrafic", "TfcItem");
		//多个标签取 + 属性过滤 取数组
		getArrayByTagsFilterAttribute(xml, new CharacterHandler() {
			@Override
			public void exec(String str) {
				System.out.println(str);
			}
		},"phoneType=\"InternalMobileTel\"","HotelBaseInfo", "ContactPhone", "PhoneNo");
	}

	public static String getStringByTags(String xml,String ... tag) {
		Matcher mac = moreKey(tag).matcher(xml);
		if (mac.find()) {
			return mac.group(1);
		}
		return empty;
	}

	public static void getArrayByTags(String xml,CharacterHandler handler,String ... tag) {
		String[] newTag = null;
		if (tag.length == 1) {
			newTag = tag;
		} else {
			newTag = Arrays.copyOfRange(tag, 0, tag.length - 1);
		}
		Matcher mac = moreKey(newTag).matcher(xml);
		String newXMl = empty;
		if (mac.find()) {
			newXMl = mac.group(1);
		}
		String listTag = tag[tag.length - 1];
		if (StringUtils.isNotNull(newXMl)) {
			mac = singleNoNestingKey(listTag).matcher(newXMl);
			while (mac.find()) {
				handler.exec(mac.group(1));
			}
		}
	}

	public static void getArrayByTagsFilterAttribute(String xml,CharacterHandler handler,String attribute,String ... tag) {
		String[] newTag = null;
		if (tag.length == 1) {
			newTag = tag;
		} else {
			newTag = Arrays.copyOfRange(tag, 0, tag.length - 1);
		}
		Matcher mac = moreKey(newTag).matcher(xml);
		String newXMl = empty;
		if (mac.find()) {
			newXMl = mac.group(1);
		}
		String listTag = tag[tag.length - 1];
		if (StringUtils.isNotNull(newXMl)) {
			mac = singleNoNestingKey(attribute, listTag).matcher(newXMl);
			while (mac.find()) {
				handler.exec(mac.group(1));
			}
		}
	}

	public static String getStringByTag(String xml,String tag){
		Matcher mac = singleKey(tag).matcher(xml);
		if(mac.find()){
			return mac.group(1);
		}
		return empty;
	}

	private static Pattern singleNoNestingKey(String tag) {
		return Pattern.compile(String.format("<%s>([\\s\\S]*?)</%s>", tag, tag, tag));
	}

	private static Pattern singleNoNestingKey(String attribute,String tag) {
		return Pattern.compile(String.format("<%s[^>]*(?=%s)[^>]*>([\\s\\S]*?)</%s>", tag, attribute, tag));
	}

	private static Pattern singleKey(String tag) {
		return Pattern.compile(String.format("<%s>([\\s\\S]*)</%s>", tag, tag));
	}

	private static Pattern moreKey(String ... tags) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tags.length; i++) {
			if (i == tags.length - 1) {
				sb.append(String.format("<%s>", tags[i]));
			} else {
				sb.append(String.format("<%s>[\\s\\S]*", tags[i]));
			}
		}
		for (int i = tags.length - 1; i > -1; i--) {
			if (i == tags.length - 1) {
				sb.append(String.format("([\\s\\S]*)</%s>", tags[i]));
			} else {
				sb.append(String.format("[\\s\\S]*</%s>", tags[i]));
			}
		}
		return Pattern.compile(sb.toString());
	}
}