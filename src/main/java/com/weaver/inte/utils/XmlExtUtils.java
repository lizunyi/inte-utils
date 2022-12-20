package com.weaver.inte.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 *
 * @author saps.weaver
 *
 */
public class XmlExtUtils {

    /***
     * XML->String
     */
    public static String getStringByTag(String xml, String tag) {
        Matcher mac = singleKey(tag).matcher(xml);
        if (mac.find()) {
            return mac.group(1);
        }
        return StringExtUtils.empty;
    }

    public static String getAttributeByTag(String xml, String tag, String attribute) {
        Matcher mac = singleKeyByAttribute(tag, attribute).matcher(xml);
        if (mac.find()) {
            return mac.group(1);
        }
        return StringExtUtils.empty;
    }


    public static String getStringByTags(String xml, String... tag) {
        Matcher mac = moreKey(tag).matcher(xml);
        if (mac.find()) {
            return mac.group(1);
        }
        return StringExtUtils.empty;
    }

    public static List<String> getArrayByTags(String xml, String... tag) {
        List<String> result = getArrayByTags(xml, false, tag);
        return result;
    }

    public static List<String> getArrayByTags(String xml, boolean withTag, String... tag) {
        List<String> result = new ArrayList<>();
        String[] newTag = null;
        if (tag.length == 1) {
            newTag = tag;
        } else {
            newTag = Arrays.copyOfRange(tag, 0, tag.length - 1);
        }
        Matcher mac = moreKey(newTag).matcher(xml);
        String newXMl = StringExtUtils.empty;
        if (mac.find()) {
            newXMl = mac.group(1);
        }
        String listTag = tag[tag.length - 1];
        if (StringExtUtils.isNotNull(newXMl)) {
            mac = singleNoNestingKey(listTag).matcher(newXMl);
            while (mac.find()) {
                if (withTag) {
                    result.add(mac.group(0));
                } else {
                    result.add(mac.group(1));
                }
            }
        }
        return result;
    }

    public static List<String> getArrayByTagsFilterAttribute(String xml, String attribute, String... tag) {
        return getArrayByTagsFilterAttribute(xml, attribute, false, tag);
    }

    public static List<String> getArrayByTagsFilterAttribute(String xml, String attribute, boolean isWithTag, String... tag) {
        String[] newTag = null;
        if (tag.length == 1) {
            newTag = tag;
        } else {
            newTag = Arrays.copyOfRange(tag, 0, tag.length - 1);
        }
        Matcher mac = moreKey(newTag).matcher(xml);
        String newXMl = StringExtUtils.empty;
        if (mac.find()) {
            newXMl = mac.group(1);
        }
        String listTag = tag[tag.length - 1];
        List<String> result = new ArrayList<>();
        if (StringExtUtils.isNotNull(newXMl)) {
            mac = singleNoNestingKey(attribute, listTag).matcher(newXMl);
            while (mac.find()) {
                if (isWithTag) {
                    result.add(mac.group(0));
                } else {
                    result.add(mac.group(1));
                }
            }
        }
        return result;
    }

    /***
     * 从XML中提取字符串核心方法
     * @param tag
     * @param attribute
     * @return
     */
    private static Pattern singleKeyByAttribute(String tag, String attribute) {
        return Pattern.compile(String.format("<%s[\\s\\S]*?%s=\"([\\s\\S]*?)\"[\\s\\S]*?>", tag, attribute));
    }

    private static Pattern singleNoNestingKey(String tag) {
        return Pattern.compile(String.format("<%s[^>]*>([\\s\\S]*?)</%s>", tag, tag, tag));
    }

    private static Pattern singleNoNestingKey(String attribute, String tag) {
        return Pattern.compile(String.format("<%s[^>]*(?=%s)[^>]*>([\\s\\S]*?)</%s>", tag, attribute, tag));
    }

    private static Pattern singleKey(String tag) {
        return Pattern.compile(String.format("<%s[^>]*>([\\s\\S]*)</%s>", tag, tag));
    }

    private static Pattern moreKey(String... tags) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tags.length; i++) {
            if (i == tags.length - 1) {
                sb.append(String.format("<%s[^>]*>", tags[i]));
            } else {
                sb.append(String.format("<%s[^>]*>[\\s\\S]*", tags[i]));
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