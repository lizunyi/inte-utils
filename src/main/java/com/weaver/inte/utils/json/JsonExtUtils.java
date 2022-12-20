package com.weaver.inte.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weaver.inte.utils.ReadWriteUtils;
import com.weaver.inte.utils.StringUtils;

import java.util.*;

/**
 * @author: saps.weaver
 * @create: 2022-12-20 09:45
 **/
public class JsonExtUtils {

    private final static String root = "$";

    public static void main(String[] args) throws Exception {
        String text = ReadWriteUtils.read("D://demo.txt");
//        Map<String, String> keys = strcut(JSONObject.parseObject(text));
//        System.out.println(JSON.toJSONString(keys));
        String s = "$.data..dstCity";
//        String s = "$.nihao.al";
        List<String> vals = getValueByJsonPath(JSONObject.parseObject(text), s);
        System.out.println(JSON.toJSONString(vals));
    }

    /***
     * 根据JSON路径获取字符串
     * @param res
     * @param path $.nihao.al
     */
    public static List<String> getValueByJsonPath(JSONObject res, String path) {
        List<String> vals = new ArrayList<>();
        String[] keys = path.replaceAll("^\\$\\.", "").split("\\.\\.");
        if (keys.length == 1) {
            vals.add(StringUtils.getStringByKeys(res, keys[0].split("\\.")));
        } else {
            JSONArray arr = StringUtils.getArrayByKeys(res, keys[0]);
            if (arr != null) {
                cycleGetValueByJsonPath(arr, Arrays.copyOfRange(keys, 1, keys.length), vals);
            }
        }
        return vals;
    }

    private static void cycleGetValueByJsonPath(JSONArray res, String[] keys, List<String> vals) {
        for (int i = 0; i < res.size(); i++) {
            JSONObject json = res.getJSONObject(i);
            for (int j = 0; j < keys.length; j++) {
                if (j == keys.length - 1) {
                    String val = StringUtils.getStringByJson(json, keys[j]);
                    vals.add(val);
                } else {
                    JSONArray arr = StringUtils.getArrayByKeys(json, keys[j]);
                    if (arr != null) {
                        cycleGetValueByJsonPath(arr, Arrays.copyOfRange(keys, j + 1, keys.length), vals);
                    }
                }
            }
        }
    }

    /***
     * 获取JSON的数据结构
     * @param res
     * @return
     */
    public static Map<String, String> strcut(Object res) {
        Map<String, String> structMap = new HashMap<>();
        JsonDataType jsonDataType = getJsonDataType(res);
        if (jsonDataType == JsonDataType.object_type) {
            compareObject(root, structMap, (JSONObject) res);
        } else if (jsonDataType == JsonDataType.array_type) {
            compareArray(root.concat("."), structMap, (JSONArray) res);
        }
        return structMap;
    }

    private static void compareObject(String keyPrefix, Map<String, String> structMap, JSONObject res) {
        for (String key : res.keySet()) {
            Object obj = res.get(key);
            JsonDataType jsonDataType = getJsonDataType(obj);
            if (jsonDataType == JsonDataType.object_type) {
                compareObject(keyPrefix.concat(".").concat(key), structMap, (JSONObject) obj);
            } else if (jsonDataType == JsonDataType.array_type) {
                compareArray(keyPrefix.concat(".").concat(key).concat("."), structMap, (JSONArray) obj);
            } else {
                String fullKey = keyPrefix.concat(".").concat(key);
                if (!structMap.containsKey(fullKey)) {
                    structMap.put(fullKey, jsonDataType.getType());
                }
            }
        }
    }

    private static void compareArray(String keyPrefix, Map<String, String> structMap, JSONArray res) {
        for (int i = 0; i < 1; i++) {
            Object inObj = res.get(i);
            JsonDataType inJsonDataType = getJsonDataType(inObj);
            if (inJsonDataType == JsonDataType.object_type) {
                compareObject(keyPrefix, structMap, (JSONObject) inObj);
            } else if (inJsonDataType == JsonDataType.array_type) {
                compareArray(keyPrefix.concat("."), structMap, (JSONArray) inObj);
            }
        }
    }

    private static JsonDataType getJsonDataType(Object obj) {
        String typeName = "null";
        if (obj != null) {
            typeName = obj.getClass().getSimpleName().toLowerCase();
        }
        return JsonDataType.get(typeName);
    }
}
