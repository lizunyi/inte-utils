package com.weaver.inte;

import com.weaver.inte.utils.ParallThread;

public class Thread2 extends ParallThread {
    @Override
    protected void work() throws Exception {
        Thread.sleep(1000);
        Example a = getObject(Example.class);
        System.out.println("Tread2：" + a.getName());
        System.out.println("Thread2结束");
    }
}
