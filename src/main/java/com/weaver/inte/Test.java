package com.weaver.inte;

import com.weaver.inte.utils.WeaveThreadCore;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        System.out.println(null == null);
        System.out.println("开始");
        WeaveThreadCore a = new WeaveThreadCore()
                .group(new Thread2(), new Thread1())
                .group(new Thread2())
                .addObject(new Example("你好"))
                .execute();
        System.out.println("结束");
        a.execute();

        List<Example> amo = new ArrayList<>();
        amo.add(new Example("123"));
        amo.stream().forEach(x -> {
            x.setName("123123");
        });
        System.out.println(amo.get(0).getName());
    }
}
