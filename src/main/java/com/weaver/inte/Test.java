package com.weaver.inte;

import com.weaver.inte.utils.WeaveThreadCore;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Test {
    public static void main(String[] args) {
        System.out.println(new Date() + " 开始");
        Map<String, Object> dataMap = new HashMap<>();
        WeaveThreadCore a = new WeaveThreadCore()
                .timeout(10)
                .group(new Thread2(), new Thread1())
                .group(new Thread2())
                .addObject(new Example("你好"))
                .addObject("data", dataMap)
                .execute();
        System.out.println(new Date() + " 结束");

        CountDownLatch d = new CountDownLatch(1);
        try {
            d.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
