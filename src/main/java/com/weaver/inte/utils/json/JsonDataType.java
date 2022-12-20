package com.weaver.inte.utils.json;

/**
 * @author: saps.weaver
 * @create: 2022-12-20 11:46
 **/

public enum JsonDataType {
    int_type("int"),
    long_type("long"),
    decimal_type("decimal"),
    boolean_type("boolean"),
    string_type("string"),
    object_type("object"),
    array_type("array"),
    null_type("null");

    private String type;

    JsonDataType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static JsonDataType get(String type) {
        for (JsonDataType t : values()) {
            if (type.contains(t.getType())) {
                return t;
            }
        }
        return null;
    }
}
