package com.weaver.inte.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weaver.inte.utils.ReadWriteUtils;
import com.weaver.inte.utils.StringExtUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author: saps.weaver
 * @create: 2022-12-20 09:45
 **/
public class JsonExtUtils {

    private final static String root = "$";

    public static void main(String[] args) throws Exception {
        String text = ReadWriteUtils.read("D://demo.txt");
        Map<String, String> keys = strcut(JSONObject.parseObject(text));
        System.out.println(JSON.toJSONString(keys));
//        String s = "$.data..dstCity";
//        String s = "$.nihao.al";
//        List<String> vals = getValueByJsonPath(JSONObject.parseObject(text), s);
//        System.out.println(JSON.toJSONString(vals));
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
            vals.add(getStringByKeys(res, keys[0].split("\\.")));
        } else {
            JSONArray arr = getArrayByKeys(res, keys[0]);
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
                    String val = getStringByJson(json, keys[j]);
                    vals.add(val);
                } else {
                    JSONArray arr = getArrayByKeys(json, keys[j]);
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


    /***
     * JSON->String
     */
    public static String getStringByJson(JSONObject json, String key) {
        return StringExtUtils.ifNull(json.get(key));
    }

    public static Integer getIntegerByJson(JSONObject json, String key) {
        return StringExtUtils.ifIntegerNull(json.get(key));
    }

    public static BigDecimal getBigDecimalByJson(JSONObject json, String key) {
        return StringExtUtils.ifBigDecimalNull(json.get(key));
    }

    public static Long getLongByJson(JSONObject json, String key) {
        return StringExtUtils.ifLongNull(json.get(key));
    }

    public static String getStringByKeys(JSONObject json, String... keys) {
        JSONObject commResult = jsonCore(json, keys);
        if (commResult != null) {
            String key = keys[keys.length - 1];
            return StringExtUtils.ifNull(commResult.get(key));
        }
        return StringExtUtils.empty;
    }


    public static Integer getIntegerByKeys(JSONObject json, String... keys) {
        JSONObject commResult = jsonCore(json, keys);
        if (commResult != null) {
            String key = keys[keys.length - 1];
            return StringExtUtils.ifIntegerNull(commResult.get(key));
        }
        return null;
    }

    public static BigDecimal getBigDecimalByKeys(JSONObject json, String... keys) {
        JSONObject commResult = jsonCore(json, keys);
        if (commResult != null) {
            String key = keys[keys.length - 1];
            return StringExtUtils.ifBigDecimalNull(commResult.get(key));
        }
        return null;
    }

    public static Long getLongByKeys(JSONObject json, String... keys) {
        JSONObject commResult = jsonCore(json, keys);
        if (commResult != null) {
            String key = keys[keys.length - 1];
            return StringExtUtils.ifLongNull(commResult.get(key));
        }
        return null;
    }

    public static JSONObject getObjectByKeys(JSONObject json, String... keys) {
        JSONObject commResult = jsonCore(json, keys);
        if (commResult != null) {
            String key = keys[keys.length - 1];
            return commResult.getJSONObject(key);
        }
        return null;
    }

    public static JSONArray getArrayByKeys(JSONObject json, String... keys) {
        JSONObject commResult = jsonCore(json, keys);
        if (commResult != null) {
            String key = keys[keys.length - 1];
            return commResult.getJSONArray(key);
        }
        return null;
    }

    public static String getStringByKeysInArrayFirst(JSONObject json, String... keys) {
        JSONArray array = getArrayByKeys(json, Arrays.copyOf(keys, keys.length - 1));
        if (array != null && array.size() > 0) {
            JSONObject object = array.getJSONObject(0);
            String key = keys[keys.length - 1];
            return getStringByJson(object, key);
        }
        return StringExtUtils.empty;
    }

    public static JSONObject getObjectByKeysInArrayFirst(JSONObject json, String... keys) {
        JSONArray array = getArrayByKeys(json, keys);
        if (array != null && array.size() > 0) {
            JSONObject object = array.getJSONObject(0);
            return object;
        }
        return null;
    }

    /***
     * 从JSONObject 中提取字符串核心方法
     * @param json
     * @param keys
     * @return
     */
    private static JSONObject jsonCore(JSONObject json, String... keys) {
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
}
