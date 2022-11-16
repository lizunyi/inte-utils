package com.weaver.inte;

import com.alibaba.fastjson.JSONObject;
import com.weaver.inte.utils.StringUtils;

/**
 * @author: saps.weaver
 * @create: 2022-11-16 09:16
 **/
public class JsonTest {
    public static void main(String[] args) throws Exception {
        /***
         * StringUtils 涵盖
         * 1. 字符串判断是否为空，字符串与Int、Integer、Long、long、Double、BigDecimal等类型转转，替代if else 或三元运算符都操作
         * 2. 从json对象中取值
         * 3. 从xml对象中取值（正则）
         */

        //字符串操作 示例
        String str = null;
        System.out.println(StringUtils.isNull(str));//判断是否为空，入参Object类型
        System.out.println(StringUtils.ifNull(str));//判断如果为空，则返回空字符串
        System.out.println(StringUtils.ifNull(str, "Hello"));//判断如果为空，则返回默认的字符串
        str = "";
        System.out.println(StringUtils.isNull(str));
        str = "a";
        System.out.println(StringUtils.isNull(str));
        System.out.println(StringUtils.ifNull(str, "Hello"));
        str = "11";
        System.out.println(StringUtils.ifBigDecimalNull(str));//如果字符串不为空，则转为BigDecimal对象
        System.out.println(StringUtils.ifLongNull(str));//如果字符串不为空，则转为Long对象
        System.out.println(StringUtils.ifDoubleNull(str));//如果字符串不为空，则转为Double对象

        //操作Json示例
        String jsonStr = "{\n" +
                "  \"code\": \"200\",\n" +
                "  \"msg\": null,\n" +
                "  \"data\": {\n" +
                "    \"flight\": {\n" +
                "      \"airName\": \"厦门航空\",\n" +
                "      \"orgAirportTerminal\": \"T2\",\n" +
                "      \"arrDate\": \"2022-12-01\",\n" +
                "      \"airportTax\": \"50\",\n" +
                "      \"depDate\": \"2022-12-01\",\n" +
                "      \"meal\": \"有餐食\",\n" +
                "      \"fuelTax\": \"120\",\n" +
                "      \"flightNo\": \"MF3023\",\n" +
                "      \"planeStyle\": \"大型 33H\",\n" +
                "      \"dstAirportTerminal\": \"T2\",\n" +
                "      \"airCode\": \"MF\",\n" +
                "      \"orgAirportName\": \"首都国际机场\",\n" +
                "      \"dstAirportName\": \"虹桥国际机场\",\n" +
                "      \"cabin\": {\n" +
                "        \"supplierName\": \"畅帆商旅\",\n" +
                "        \"discountRate\": \"0.44\",\n" +
                "        \"cost\": \"880.00\",\n" +
                "        \"supplierState\": \"cfsl\",\n" +
                "        \"lastSeat\": \"A\",\n" +
                "        \"basicCabinType\": \"Y\",\n" +
                "        \"policyCode\": \"T\",\n" +
                "        \"cabinName\": \"经济舱\",\n" +
                "        \"uniqueInfo\": null\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        JSONObject json = JSONObject.parseObject(jsonStr);
        //如果仅仅从demo.json中取cabins.discountRate
        System.out.println(StringUtils.getBigDecimalByKeys(json, "data", "flight", "cabin", "discountRate"));
        /***
         * 在使用上如果对某个对象多次取值，建议缩小取值的深度，比如 要获取cabin下的
         * supplierName、discountRate、cost、supplierState、lastSeat、basicCabinType 构建新的对象，可以取出 cabin 对象，然后从cabin对象中逐步取出各个值以减少不必要的循环开销
         */
        JSONObject cabinJson = StringUtils.getObjectByKeys(json, "data", "flight", "cabin");
        System.out.println(StringUtils.getStringByJson(cabinJson, "supplierName"));
        System.out.println(StringUtils.getBigDecimalByJson(cabinJson, "discountRate"));
        System.out.println(StringUtils.getBigDecimalByJson(cabinJson, "cost"));
        System.out.println(StringUtils.getStringByJson(cabinJson, "supplierState"));
        System.out.println(StringUtils.getStringByJson(cabinJson, "lastSeat"));
        System.out.println(StringUtils.getStringByJson(cabinJson, "basicCabinType"));

    }
}
