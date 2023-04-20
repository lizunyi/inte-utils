package com.weaver.inte.utils;


import com.alibaba.fastjson.JSON;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapExtUtils {

    private final Map<String, Object> data;

    private MapExtUtils() {
        this.data = new LinkedHashMap<>();
    }

    public static MapExtUtils newInstance() {
        return new MapExtUtils();
    }

    public static Map<String, Object> parser(String json) {
        return JSON.parseObject(json, Map.class);
    }

    public MapExtUtils put(String name, Object Object) {
        this.data.put(name, Object);
        return this;
    }

    public String json() {
        return JSON.toJSONString(data);
    }

    public Map<String, Object> map() {
        return data;
    }

}
