package com.weaver.inte.utils;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class ListExtUtils<T> {

    private final List<T> data;

    private ListExtUtils() {
        this.data = new ArrayList<>();
    }

    public static ListExtUtils newInstance() {
        return new ListExtUtils();
    }

    public ListExtUtils add(T Object) {
        this.data.add(Object);
        return this;
    }

    public String json() {
        return JSON.toJSONString(data);
    }

    public List<T> list() {
        return data;
    }

}
