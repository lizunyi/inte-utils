package com.weaver.inte;

import com.weaver.inte.utils.CountDownGroup;

public class Test {
    public static void main(String[] args) {
        System.out.println("开始");
        CountDownGroup a = new CountDownGroup()
                .threadGroup(new Thread2(),new Thread1())
                .addThread(new Thread2())
//                .addThread(new Thread2())
                .addObject(new Example("你好"))
                .execute();
        System.out.println("结束");
        a.execute();
    }
}
