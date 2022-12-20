package com.weaver.inte.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weaver.inte.utils.ReadWriteUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: saps.weaver
 * @create: 2022-12-20 09:45
 **/
public class JsonExtUtils {

    private final static String root = "$";

    public static void main(String[] args) throws Exception {
        String text = ReadWriteUtils.read("D://demo.txt");
        Map<String, String> keys = compare(JSONObject.parseObject(text));
        System.out.println(JSON.toJSONString(keys));
    }

    public static Map<String, String> compare(Object res) {
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
