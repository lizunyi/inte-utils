package com.weaver.inte;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: saps.weaver
 * @create: 2023-01-15 13:18
 **/
public class Test3 {
    public static void main(String[] args) throws UnknownHostException {
        List<Example> fixedPriceList = new ArrayList<>();
        fixedPriceList.add(new Example("1", BigDecimal.valueOf(100.00)));
        fixedPriceList.add(new Example("1", BigDecimal.valueOf(100)));
        fixedPriceList.add(new Example("2", BigDecimal.valueOf(100)));
        fixedPriceList.add(new Example("2", BigDecimal.valueOf(101)));
        fixedPriceList.add(new Example("3", BigDecimal.valueOf(200)));
        fixedPriceList = new ArrayList<>(fixedPriceList.stream().collect(Collectors.toMap(Example::getName, Function.identity(), (x1, x2) -> x1.getAmount().compareTo(x2.getAmount()) <= 0 ? x1 : x2)).values());

        System.out.println(JSON.toJSONString(fixedPriceList));
    }
}
